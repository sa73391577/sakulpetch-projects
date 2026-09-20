import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UserFormModalComponent } from './user-form-modal.component';
import { ReactiveFormsModule, FormGroup, FormControl, Validators, FormsModule } from '@angular/forms';
import { UsersService } from '../../../core/services/users.service';
import { RolesService } from '../../../core/services/roles.service';
import { GendersService } from '../../../core/services/genders.service';
import { DateUtilsService } from '../../../core/utils/date-utils.service';
import { AuthService } from '../../../core/services/auth';
import { SwalAlert2Service } from '../../../core/services/swal-alert-2.service';
import { ConvertDataUtilsService } from '../../../core/utils/convert-data-utils.service';
import { provideTranslateService } from '@ngx-translate/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { of, throwError } from 'rxjs';
import { ComponentRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { describe, it, expect, beforeEach, vi, type Mock } from 'vitest';
import Swal from 'sweetalert2';
import { delay } from 'rxjs/operators';

describe('UserFormModalComponent', () => {

  let component: UserFormModalComponent;
  let fixture: ComponentFixture<UserFormModalComponent>;
  let componentRef: ComponentRef<UserFormModalComponent>;

  let mockUsersService: { getByUsername: Mock; add: Mock; update: Mock };
  let mockRolesService: { list: Mock };
  let mockGendersService: { list: Mock };
  let mockDateUtilsService: { convertFormat: Mock };
  let mockAuthService: { getCurrentUserLogin: Mock };
  let mockSwalService: { success: Mock; error: Mock };
  let mockConvertDataService: { convertStrArrayToArray: Mock };

  beforeEach(async () => {
    Object.defineProperty(window, 'matchMedia', {
      writable: true,
      value: vi.fn().mockImplementation(query => ({
        matches: false,
        media: query,
        onchange: null,
        addListener: vi.fn(),
        removeListener: vi.fn(),
        removeEventListener: vi.fn(),
        addEventListener: vi.fn(),
        dispatchEvent: vi.fn(),
      })),
    });

    mockUsersService = {
      getByUsername: vi.fn().mockReturnValue(of({ data: { nameTH: 'สมชาย', username: 'test_edit' } })),
      add: vi.fn().mockReturnValue(of({ success: true })),
      update: vi.fn().mockReturnValue(of({ success: true }))
    };

    mockRolesService = {
      list: vi.fn().mockReturnValue(of({ data: [{ code: 'ADMIN', nameTH: 'ผู้ดูแลระบบ', nameEN: 'Admin' }] }))
    };

    mockGendersService = {
      list: vi.fn().mockReturnValue(of({ data: [{ code: 'MALE', nameTH: 'ชาย', nameEN: 'Male' }] }))
    };

    mockDateUtilsService = {
      convertFormat: vi.fn().mockReturnValue('2000-01-01')
    };

    mockAuthService = {
      getCurrentUserLogin: vi.fn().mockReturnValue('admin_test')
    };

    mockSwalService = {
      success: vi.fn().mockResolvedValue({ isConfirmed: true, isDismissed: false }),
      error: vi.fn().mockResolvedValue({ isConfirmed: true })
    };

    mockConvertDataService = {
      convertStrArrayToArray: vi.fn().mockReturnValue([['ADMIN']])
    };

    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, UserFormModalComponent],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        provideTranslateService(),
        { provide: UsersService, useValue: mockUsersService },
        { provide: RolesService, useValue: mockRolesService },
        { provide: GendersService, useValue: mockGendersService },
        { provide: DateUtilsService, useValue: mockDateUtilsService },
        { provide: AuthService, useValue: mockAuthService },
        { provide: SwalAlert2Service, useValue: mockSwalService },
        { provide: ConvertDataUtilsService, useValue: mockConvertDataService },
        {
          provide: JwtHelperService,
          useValue: { decodeToken: vi.fn(), isTokenExpired: vi.fn(), getTokenExpirationDate: vi.fn() }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(UserFormModalComponent);
    component = fixture.componentInstance;
    componentRef = fixture.componentRef;

    // ดักสกัดการสั่งงานออโต้ใน OnInit / OnChanges ตั้งแต่แรกเริ่มระบบ
    vi.spyOn(component, 'loadRoleList').mockImplementation(() => { });
    vi.spyOn(component, 'loadGenderList').mockImplementation(() => { });

  });

  // ─────────────── หมวดเริ่มต้นระบบ ───────────────

  it('TC00 : should create', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  it('TC01 : ควรสร้างฟอร์มเริ่มต้นและผูก Validators มาครบถ้วน', () => {
    fixture.detectChanges();
    expect(component.signupForm).toBeDefined();
  });

  it('TC02 : ฟอร์มต้องระบุสถานะ Invalid หากปล่อยให้ค่าทั้งหมดเป็นค่าว่าง', () => {
    fixture.detectChanges();
    expect(component.signupForm.invalid).toBe(true);
  });

  // ─────────────── หมวด OnChanges & Data Binding ───────────────

  it('TC03 : เมื่อค่า isOpen ถูกตั้งเป็น true ควรเรียกใช้ฟังก์ชันโหลดข้อมูลกลุ่ม Role', () => {
    const roleSpy = vi.spyOn(component, 'loadRoleList');
    const genderSpy = vi.spyOn(component, 'loadGenderList');

    // จำลองส่งข้อมูล Input สั่งเปิดหน้าต่าง
    componentRef.setInput('isOpen', true);
    componentRef.setInput('userNameInput', null);

    // กระตุ้นระบบ OnChanges ของ Angular ให้ทำงาน
    component.ngOnChanges({
      isOpen: { currentValue: true, previousValue: false, firstChange: true, isFirstChange: () => true }
    });

    //ตรวจสอบผลลัพธ์
    expect(roleSpy).toHaveBeenCalled();
  });


  it('TC04 : เมื่อเป็นเคสแก้ไขข้อมูล ฟังก์ชัน loadUserInfo ควรกรอกฟอร์มให้อัตโนมัติ', async () => {

    mockUsersService.getByUsername.mockReturnValue(of({
      data: {
        nameTH: 'สมชาย',
        surnameTH: 'ใจดี',
        nameEN: 'Somchai',
        surnameEN: 'Jaidee',
        idCard: '1106341043306',
        mobilePhone: '0812345678',
        telephone: '021234567',
        brithDate: '2000-01-01',
        username: 'test_edit',
        roleCode: '["ADMIN"]',
        genderCode: 'MALE',
        email: 'test@email.com'
      }
    }));

    mockConvertDataService.convertStrArrayToArray.mockReturnValue(['ADMIN']);

    component.loadUserInfo('test_edit');

    expect(mockUsersService.getByUsername).toHaveBeenCalledWith('test_edit');
    expect(component.signupForm.value.nameTH).toBe('สมชาย');
  });

  // ─────────────── หมวดทดสอบการส่งฟอร์ม ───────────────

  it('TC05 : เมื่อกดบันทึกข้อมูลในขณะที่ฟอร์มยัง Invalid ไม่ควรเรียก API บันทึกใดๆ', () => {
    fixture.detectChanges();
    component.submitForm();
    expect(mockUsersService.add).not.toHaveBeenCalled();
  });

  it('TC06 : เมื่อกรอกข้อมูลสมาชิกใหม่ถูกต้องครบถ้วน ควรเรียกใช้ API add', async () => {

    componentRef.setInput('isOpen', true);
    componentRef.setInput('userNameInput', null);

    fixture.detectChanges();
    await fixture.whenStable();

    // ป้อนข้อมูลจำลองปกติ
    component.signupForm.patchValue({
      nameTH: 'สมชาย',
      surnameTH: 'ใจดี',
      nameEN: 'Somchai',
      surnameEN: 'Jaidee',
      idCard: '1106341043306',
      mobilePhone: '0812345678',
      telephone: '021234567',
      brithDate: '2000-01-01',
      username: '',
      password: 'Password123!',
      confirmPassword: 'Password123!',
      roleCode: ['001'],
      genderCode: '001',
      email: 'test@email.com'
    });

    // บังคับเขียนทับสถานะฟอร์มโดยตรงในระดับ Property ให้เป็น VALID
    Object.defineProperty(component.signupForm, 'invalid', { get: () => false }); // invalid เป็น false แปลว่าฟอร์มถูกต้องร้อยเปอร์เซ็นต์
    Object.defineProperty(component.signupForm, 'valid', { get: () => true });

    // สั่งกดบันทึกข้อมูลฟอร์ม
    component.submitForm();

    expect(mockUsersService.add).toHaveBeenCalled();
  });

  it('TC07 : เมื่อกรอกข้อมูลสมาชิกถูกต้อง (ขาแก้ไขข้อมูล) ควรเรียกใช้ API update', async () => {

    component.loadGenderList = vi.fn();
    component.loadRoleList = vi.fn();

    // ป้อนค่าสถานะจำลอง
    componentRef.setInput('isOpen', true);
    componentRef.setInput('userNameInput', 'somchai01');

    // กระตุ้นระบบประมวลผลและการสร้างอินสแตนซ์ FormGroup ขาแก้ไขของ Angular
    fixture.detectChanges();
    await fixture.whenStable();

    //Set Form.
    component.signupForm.patchValue({
      nameTH: 'สมชาย',
      surnameTH: 'ใจดี',
      nameEN: 'Somchai',
      surnameEN: 'Jaidee',
      idCard: '1106341043306',
      mobilePhone: '0812345678',
      telephone: '021234567',
      brithDate: '2000-01-01',
      username: 'somchai01',
      roleCode: ['001'],
      genderCode: '001',
      email: 'test@email.com'
    });

    // บังคับเปลี่ยนสถานะตัวตรวจสอบฟอร์มภาพรวมชั้นนอกสุดให้เป็น VALID (invalid = false)
    Object.defineProperty(component.signupForm, 'invalid', { get: () => false });

    // สั่งกดยืนยันส่งข้อมูลแบบฟอร์ม
    component.submitForm();

    expect(mockUsersService.update).toHaveBeenCalled();
  });


});
