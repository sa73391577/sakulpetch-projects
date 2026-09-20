import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export const idCardValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;

    // 1. ถ้ายังไม่ได้กรอกข้อมูล ให้ข้ามการตรวจเช็กไปก่อน (ให้เป็นหน้าที่ของ Validators.required)
    if (!value) return null;

    // 2. ตรวจสอบว่าต้องเป็นตัวเลขล้วน 13 หลักเท่านั้น
    if (!/^\d{13}$/.test(value)) {
        return { invalidIdCard: true };
    }

    // 3. สูตรคำนวณตรวจสอบความถูกต้องของเลขบัตรประชาชนตัวจริง (Checksum)
    let sum = 0;
    for (let i = 0; i < 12; i++) {
        sum += parseInt(value.charAt(i), 10) * (13 - i);
    }

    const checkDigit = (11 - (sum % 11)) % 10;

    // 4. ถ้ารหัสหลักสุดท้ายไม่ตรงกับผลลัพธ์สูตรคำนวณ ➡️ พ่น Error ทันที 🔴
    if (checkDigit !== parseInt(value.charAt(12), 10)) {
        return { invalidIdCard: true };
    }

    // 5. 🟢 ถ้าเลขถูกต้องตามสูตรจริง คืนค่าเป็น null (ผ่าน)
    return null;
};