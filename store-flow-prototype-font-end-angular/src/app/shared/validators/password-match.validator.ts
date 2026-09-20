import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export const passwordMatchValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
    const password = control.get('password');
    const confirmPassword = control.get('confirmPassword');

    if (!password || !confirmPassword) return null;

    // ถ้ารหัสไม่ตรงกัน ให้พ่น Error ไปแปะไว้ที่ช่อง confirmPassword
    if (password.value !== confirmPassword.value) {
        confirmPassword.setErrors({ passwordMismatch: true });
        return { passwordMismatch: true };
    } else {
        // ถ้ารหัสตรงกันแล้ว และไม่มีเออเรอร์อื่นค้างอยู่ ให้ล้างเออเรอร์ทิ้ง
        if (confirmPassword.hasError('passwordMismatch')) {
            confirmPassword.setErrors(null);
        }
        return null;
    }

}