import { AbstractControl, ValidationErrors } from '@angular/forms';

export function phoneNumberValidator(control: AbstractControl): ValidationErrors | null {
  const value = control.value;

  // ถ้าไม่มีข้อมูล ให้ผ่านไปก่อน (ปล่อยให้เป็นหน้าที่ของ Validators.required ถ้ามี)
  if (!value) return null;

  // RegEx เช็กเบอร์บ้านไทย 9 หลัก ขึ้นต้นด้วย 0 และตามด้วยเลข 2-5 หรือ 7
  const telephoneRegex = /^0[2-57]\d{7}$/;
  const isValid = telephoneRegex.test(value);

  // ถ้าถูกต้องคืนค่า null, ถ้าผิดรูปแบบคืนค่า object error
  return isValid ? null : { invalidTelephone: true };
}