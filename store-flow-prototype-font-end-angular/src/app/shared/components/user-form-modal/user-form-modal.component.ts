import { Component, input, output, inject, signal, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserInfo } from '../../../core/models/user.model';
import { RoleInfo } from '../../../core/models/role.model';
import { GenderInfo } from '../../../core/models/gender.model';
import { UsersService } from '../../../core/services/users.service';
import { RolesService } from '../../../core/services/roles.service';
import { TranslateService, TranslatePipe } from '@ngx-translate/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators, FormsModule } from '@angular/forms';
import { SignUpForm } from '../../../core/models/signup.model';
import { idCardValidator } from '../../../shared/validators/id-card.validator';
import { mobileNumberValidator } from '../../../shared/validators/mobile-number.validator';
import { phoneNumberValidator } from '../../../shared/validators/phone-number.validator';
import { IdCardMaskDirective } from '../../../shared/directives/id-card-mask.directive';
import { passwordMatchValidator } from '../../../shared/validators/password-match.validator';
import { DatepickerComponent } from '../../../shared/components/datepicker/datepicker.component';

import { ThaiOnlyDirective } from '../../../shared/directives/thai-only.directive';
import { EnglishOnlyDirective } from '../../../shared/directives/english-only.directive';
import { DateUtilsService } from '../../../core/utils/date-utils.service';

import { AuthService } from '../../../core/services/auth';
import { GendersService } from '../../../core/services/genders.service';

import Swal from 'sweetalert2'
import { SwalAlert2Service } from '../../../core/services/swal-alert-2.service';
import { ConvertDataUtilsService } from '../../../core/utils/convert-data-utils.service';

@Component({
  selector: 'app-user-form-modal',
  imports: [CommonModule, ReactiveFormsModule, TranslatePipe, IdCardMaskDirective, DatepickerComponent
    , ThaiOnlyDirective, EnglishOnlyDirective],
  standalone: true,
  templateUrl: './user-form-modal.component.html',
  styleUrl: './user-form-modal.component.css',
})
export class UserFormModalComponent implements OnChanges {

  userService = inject(UsersService);
  translateService = inject(TranslateService);
  roleService = inject(RolesService);
  dateUtilsService = inject(DateUtilsService);
  authService = inject(AuthService);
  gendersService = inject(GendersService);
  swalService = inject(SwalAlert2Service);
  convertDataService = inject(ConvertDataUtilsService);

  userId = input<string>();
  userNameInput = input<string>();
  isOpen = input<boolean>(false);
  signupForm !: FormGroup;

  closeEmitModal = output<void>();

  userInfo!: UserInfo;
  currentLang = signal<string>('th');
  rolesSelected = signal<RoleInfo[]>([]);
  genderRadio = signal<GenderInfo[]>([]);

  ngOnInit() {
    this.signupForm = new FormGroup<SignUpForm>({
      nameTH: new FormControl('', [Validators.required]),
      surnameTH: new FormControl('', [Validators.required]),
      nameEN: new FormControl('', [Validators.required]),
      surnameEN: new FormControl('', [Validators.required]),
      idCard: new FormControl('', [Validators.required, idCardValidator]),
      mobilePhone: new FormControl('', [Validators.required, mobileNumberValidator]),
      telephone: new FormControl('', [Validators.required, phoneNumberValidator]),
      brithDate: new FormControl('', [Validators.required]),
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      confirmPassword: new FormControl('', [Validators.required]),
      roleCode: new FormControl([], [Validators.required]),
      genderCode: new FormControl('', [Validators.required]),
      createdBy: new FormControl(''),
      email: new FormControl('', [Validators.required, Validators.email])
    }, {
      validators: passwordMatchValidator
    });

    this.translateService.onLangChange.subscribe((event) => {
      this.currentLang.set(event.lang);
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['isOpen'] && this.isOpen() === true) {
      // 1. เคลียร์ล้างข้อมูลเก่าที่ค้างอยู่ในฟอร์มออกไปก่อนเป็นอันดับแรกสุด 
      if (this.signupForm) {
        this.signupForm.reset();
      }

      // 2. ค่อยสั่งโหลดข้อมูลชุดใหม่เข้ามาเติมในฟอร์ม (ค่าจะไม่โดนลบแล้ว)
      this.loadRoleList();
      this.loadGenderList();
      this.loadUserInfo(this.userNameInput());
    }
  }


  loadUserInfo(username: string | undefined | null) {

    if (username) {
      this.signupForm = new FormGroup<SignUpForm>({
        nameTH: new FormControl('', [Validators.required]),
        surnameTH: new FormControl('', [Validators.required]),
        nameEN: new FormControl('', [Validators.required]),
        surnameEN: new FormControl('', [Validators.required]),
        idCard: new FormControl('', [Validators.required, idCardValidator]),
        mobilePhone: new FormControl('', [Validators.required, mobileNumberValidator]),
        telephone: new FormControl('', [Validators.required, phoneNumberValidator]),
        brithDate: new FormControl('', [Validators.required]),
        username: new FormControl(''),
        password: new FormControl(''),
        confirmPassword: new FormControl(''),
        roleCode: new FormControl([], [Validators.required]),
        genderCode: new FormControl('', [Validators.required]),
        createdBy: new FormControl(''),
        email: new FormControl('', [Validators.required, Validators.email])
      });
    } else {
      this.signupForm = new FormGroup<SignUpForm>({
        nameTH: new FormControl('', [Validators.required]),
        surnameTH: new FormControl('', [Validators.required]),
        nameEN: new FormControl('', [Validators.required]),
        surnameEN: new FormControl('', [Validators.required]),
        idCard: new FormControl('', [Validators.required, idCardValidator]),
        mobilePhone: new FormControl('', [Validators.required, mobileNumberValidator]),
        telephone: new FormControl('', [Validators.required, phoneNumberValidator]),
        brithDate: new FormControl('', [Validators.required]),
        username: new FormControl('', [Validators.required]),
        password: new FormControl('', [Validators.required]),
        confirmPassword: new FormControl('', [Validators.required]),
        roleCode: new FormControl([], [Validators.required]),
        genderCode: new FormControl('', [Validators.required]),
        createdBy: new FormControl(''),
        email: new FormControl('', [Validators.required, Validators.email])
      }, {
        validators: passwordMatchValidator
      });
    }



    if (username) {
      this.userService.getByUsername(username).subscribe({
        next: (res) => {
          if (!res || !res.data) {
            console.log("user info not found.");
            this.swalService.error(this.translateService.instant('modal.title.user.not.found'), this.translateService.instant('modal.message.not.found.user.info.specified'))
              .then((res) => {
                this.closeEmitModal.emit();
              });
            return;
          }
          let info = res?.data;

          this.signupForm.patchValue(
            {
              nameTH: info?.nameTH,
              surnameTH: info?.surnameTH,
              nameEN: info?.nameEN,
              surnameEN: info?.surnameEN,
              idCard: info?.idCard,
              mobilePhone: info?.mobilePhone,
              telephone: info?.telephone,
              brithDate: info?.brithDate,
              username: info?.username,
              roleCode: this.convertDataService.convertStrArrayToArray(info?.roleCode)[0],
              genderCode: info?.genderCode,
              email: info?.email,
            }
          );

          console.log("this.signupForm : ", this.signupForm.value);

        },
        error: (err) => {
          console.log(err);
          this.swalService.error("", this.translateService.instant('error.text.server.error.call.admin')).then(
            (res) => {
              this.closeEmitModal.emit();
            }
          );
        }
      });
    }
  }

  submitForm() {

    if (this.signupForm.invalid) {
      console.log("signupForm.invalid !!!");
      this.signupForm.markAllAsTouched();
      return;
    }

    const submitData = { ...this.signupForm.value };
    submitData.roleCode = submitData.roleCode ? [submitData.roleCode] : [];
    submitData.brithDate = this.dateUtilsService.convertFormat(submitData.brithDate, 'yyyy-MM-dd');
    submitData.createdBy = this.authService.getCurrentUserLogin();

    if (submitData.username) {
      this.updateUser(submitData);
    } else {
      this.addUser(submitData);
    }

  }

  addUser(submitData: any) {
    //call api for save user info.
    this.userService.add(submitData).subscribe(
      {
        next: (res) => {
          if (res) {
            this.swalService.success(this.translateService.instant('modal.title.save.success'), this.translateService.instant('modal.message.save.user.success')).then((res) => {
              if (res.isConfirmed) {
                this.closeEmitModal.emit();
              }
              // กดปุ่มยกเลิกอื่น ๆ
              if (res.isDismissed) {
                if (res.dismiss === Swal.DismissReason.cancel) {
                  //กดปุ่มยกเลิก
                }
                else if (res.dismiss === Swal.DismissReason.backdrop) {
                  // กดนอก pop-up
                }
                else if (res.dismiss === Swal.DismissReason.close) {
                  //กดปุ่ม [X]
                }
                else if (res.dismiss === Swal.DismissReason.esc) {
                  //กดปุ่ม ESC
                }
              }
            });
          }
        },
        error: (err) => {
          console.log("err : ", err);
          this.swalService.error("", this.translateService.instant('error.text.server.error.call.admin'));
        }
      }
    );
  }

  updateUser(submitData: any) {
    //call api for save user info.
    this.userService.update(submitData).subscribe(
      {
        next: (res) => {
          if (res) {
            this.swalService.success(this.translateService.instant('modal.title.save.success'), this.translateService.instant('modal.message.save.user.success')).then((res) => {
              /*if (res.isConfirmed) {

              }
              // กดปุ่มยกเลิกอื่น ๆ
              if (res.isDismissed) {
                if (res.dismiss === Swal.DismissReason.cancel) {
                  //กดปุ่มยกเลิก
                }
                else if (res.dismiss === Swal.DismissReason.backdrop) {
                  // กดนอก pop-up
                }
                else if (res.dismiss === Swal.DismissReason.close) {
                  //กดปุ่ม [X]
                }
                else if (res.dismiss === Swal.DismissReason.esc) {
                  //กดปุ่ม ESC
                }
              }*/

              this.closeEmitModal.emit();
            });
          }
        },
        error: (err) => {
          console.log("err : ", err);
          this.swalService.error("", this.translateService.instant('error.text.server.error.call.admin'));
        }
      }
    );
  }

  closeModal() {
    console.log("close modal !!");
    this.closeEmitModal.emit();
  }

  loadRoleList() {
    this.roleService.list().subscribe({
      next: (res) => {
        console.log("res of roles : ", res);
        if (res) {
          let dataList = res?.data;
          let roleMapperList = (dataList || []).map((data: any) => {
            return {
              code: data?.code,
              nameTh: data?.nameTH,
              nameEn: data?.nameEN
            };
          });
          this.rolesSelected.set(roleMapperList);

        }
      },
      error: (err) => {
        console.log("error of roles : ", err);
        this.swalService.error("", this.translateService.instant('error.text.server.error.call.admin'));
      }
    });
  }

  loadGenderList() {
    this.gendersService.list().subscribe({
      next: (res) => {
        console.log("res of roles : ", res);
        if (res) {
          let dataList = res?.data;
          let genderMapperList = (dataList || []).map((data: any) => {
            if (this.signupForm?.get('genderCode')?.value == '' || this.signupForm?.get('genderCode')?.value == null) {

              this.signupForm.get('genderCode')?.setValue(data?.code);
            }
            console.log("genderCode in form :", this.signupForm.get('genderCode')?.value);
            return {
              code: data?.code,
              nameTh: data?.nameTH,
              nameEn: data?.nameEN
            };
          });
          this.genderRadio.set(genderMapperList);

        }
      },
      error: (error) => {
        console.log("error of roles : ", error);
        this.swalService.error("", this.translateService.instant('error.text.server.error.call.admin'));
      }
    });
  }
}