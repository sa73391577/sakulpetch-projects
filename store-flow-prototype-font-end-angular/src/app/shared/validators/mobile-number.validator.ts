import { AbstractControl, ValidationErrors } from '@angular/forms';

export function mobileNumberValidator(control: AbstractControl): ValidationErrors | null {
  const value = control.value;

  if (!value) return null;

  const mobileRegex = /^0[6-9]\d{8}$/;
  const isValid = mobileRegex.test(value);

  return isValid ? null : { invalidMobile: true };
}