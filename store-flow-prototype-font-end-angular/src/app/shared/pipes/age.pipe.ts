import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'age',
  standalone: true
})
export class AgePipe implements PipeTransform {
  transform(birthDateValue: any): number | string {
    if (!birthDateValue) return '-';

    const birthDate = new Date(birthDateValue);

    // ตรวจสอบความถูกต้องของข้อมูลวันที่ ป้องกันแอปพัง
    if (isNaN(birthDate.getTime())) return '-';

    const today = new Date();

    // คำนวณอายุจากปีเบื้องต้น
    let age = today.getFullYear() - birthDate.getFullYear();

    // เช็กเดือนเพื่อความแม่นยำ
    const monthDiff = today.getMonth() - birthDate.getMonth();

    // ถ้ายังไม่ถึงเดือนเกิด หรือเป็นเดือนเกิดแต่ยังไม่ถึงวันเกิด ให้ลบอายุลง 1 ปี
    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
      age--;
    }

    // if age < 0 save error
    return age < 0 ? 0 : age;
  }
}
