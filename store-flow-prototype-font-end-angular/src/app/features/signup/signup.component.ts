import { Component, inject } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators, FormsModule } from '@angular/forms';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';
import { SignUpForm } from '../../core/models/signup.model'
import { CommonModule } from '@angular/common';
import { SwitchLanguageComponent } from '../../shared/components/switch-language/switch-language.component';
import { Router } from '@angular/router';
import { DatepickerComponent } from '../../shared/components/datepicker/datepicker.component';
import { passwordMatchValidator } from '../../shared/validators/password-match.validator';
import { idCardValidator } from '../../shared/validators/id-card.validator';
import { IdCardMaskDirective } from '../../shared/directives/id-card-mask.directive';

import { ThaiOnlyDirective } from '../../shared/directives/thai-only.directive';
import { EnglishOnlyDirective } from '../../shared/directives/english-only.directive';

import { UsersService } from '../../core/services/users.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [ReactiveFormsModule, TranslatePipe, CommonModule
    , SwitchLanguageComponent, DatepickerComponent, FormsModule, IdCardMaskDirective, ThaiOnlyDirective, EnglishOnlyDirective],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css',
})
export class SignupComponent {

  private translate = inject(TranslateService);
  private router = inject(Router);
  private userService = inject(UsersService);

  signupForm !: FormGroup;
  selectedDate: string = "";

  ngOnInit() {
    this.signupForm = new FormGroup<SignUpForm>({
      nameTH: new FormControl('', [Validators.required]),
      surnameTH: new FormControl('', [Validators.required]),
      nameEN: new FormControl('', [Validators.required]),
      surnameEN: new FormControl('', [Validators.required]),
      idCard: new FormControl('', [Validators.required, idCardValidator]),
      mobilePhone: new FormControl('', [Validators.required]),
      telephone: new FormControl('', [Validators.required]),
      brithDate: new FormControl('', [Validators.required]),
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      confirmPassword: new FormControl('', [Validators.required]),
      roleCode: new FormControl([]),
      genderCode: new FormControl('', [Validators.required]),
      createdBy: new FormControl('SYSTEM', [Validators.required]),
      email: new FormControl('')
    }, {
      validators: passwordMatchValidator
    });

    this.userService.list();


  }

  submitSignUpForm() {
    console.log("this.signupForm.value : ", this.signupForm.value);
    if (this.signupForm.invalid) {
      console.log("form invaild.");
      this.signupForm.markAllAsTouched();
      return;
    }

    let resForm = this.signupForm?.value;
    if (null != resForm && 'undefined' !== resForm) {
      if (resForm?.password != resForm?.confirmPassword) {
        return;
      }
    }
  }

  backToLoginPage() {
    this.router.navigateByUrl('/login');
  }

}
