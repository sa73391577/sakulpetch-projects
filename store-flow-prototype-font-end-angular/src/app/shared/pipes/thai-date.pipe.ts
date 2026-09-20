import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'thaiDate',
  standalone: true
})
export class ThaiDatePipe implements PipeTransform {
  transform(value: any, formatType: 'dash' | 'short' | 'full' = 'dash'): string {
    if (!value) return '';

    // แปลงค่าให้เป็น Object Date ของ JavaScript
    const date = new Date(value);

    // ตรวจสอบว่า Date ถูกต้องไหม ป้องกันแอปพัง
    if (isNaN(date.getTime())) return value;

    // เติมเลข 0 ข้างหน้าหากเป็นเลขหลักเดียว เช่น 4 -> 04
    const day = date.getDate().toString().padStart(2, '0');
    const monthNumber = (date.getMonth() + 1).toString().padStart(2, '0');

    // คำนวณปี พ.ศ. (ค.ศ. + 543)
    const buddhistYear = date.getFullYear() + 543;

    // 1. แบบตัวเลขมีขีดคั่น DD-MM-YYYY (เช่น 24-08-2569)
    if (formatType === 'dash') {
      return `${day}-${monthNumber}-${buddhistYear}`;
    }

    // ส่วนแสดงผลรูปแบบอื่น ๆ (เก็บไว้เผื่อเลือกใช้ในอนาคต)
    const monthIndex = date.getMonth();
    if (formatType === 'full') {
      const fullMonths = ['มกราคม', 'กุมภาพันธ์', 'มีนาคม', 'เมษายน', 'พฤษภาคม', 'มิถุนายน', 'กรกฎาคม', 'สิงหาคม', 'กันยายน', 'ตุลาคม', 'พฤศจิกายน', 'ธันวาคม'];
      return `${date.getDate()} ${fullMonths[monthIndex]} ${buddhistYear}`;
    } else {
      const shortMonths = ['ม.ค.', 'ก.พ.', 'มี.ค.', 'เม.ย.', 'พ.ค.', 'มิ.ย.', 'ก.ค.', 'ส.ค.', 'ก.ย.', 'ต.ค.', 'พ.ย.', 'ธ.ค.'];
      return `${day} ${shortMonths[monthIndex]} ${buddhistYear}`;
    }
  }
}
