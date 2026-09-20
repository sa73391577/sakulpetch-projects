import { Directive, HostListener, ElementRef } from '@angular/core';
import { NgControl } from '@angular/forms';

@Directive({
  selector: '[appIdCardMask]',
  standalone: true
})
export class IdCardMaskDirective {
  constructor(private el: ElementRef, private control: NgControl) {

  }

  @HostListener('input', ['$event'])
  onInputChange(event: any) {
    const input = this.el.nativeElement;
    let rawValue = input.value;

    // 1. เก็บเฉพาะตัวเลขดิบ 13 หลักเอาไว้คุยกับระบบฟอร์มเบื้องหลัง
    const cleanValue = rawValue.replace(/\D/g, '').substring(0, 13);

    // 2. หั่นข้อความและใส่เครื่องหมายขีดกลาง (-) สำหรับโชว์บนหน้าจอ
    const numbers = [];
    if (cleanValue.length > 0) numbers.push(cleanValue.substring(0, 1));
    if (cleanValue.length > 1) numbers.push(cleanValue.substring(1, 5));
    if (cleanValue.length > 5) numbers.push(cleanValue.substring(5, 10));
    if (cleanValue.length > 10) numbers.push(cleanValue.substring(10, 12));
    if (cleanValue.length > 12) numbers.push(cleanValue.substring(12, 13));

    const formattedValue = numbers.join('-');

    // 3. 🌟 จุดสำคัญ: สั่งแก้ค่าบนช่องกรอกหน้าจอโดยตรง
    input.value = formattedValue;

    // 4. 🌟 จุดสำคัญที่สุด: บังคับยิงค่าขีดกลางส่งกลับไปอัปเดตในสถาปัตยกรรมฟอร์มของ Angular 
    // เพื่อให้ตัวควบคุม NgControl ล็อกหน้าจอเป็นค่าฟอร์แมตขีดกลาง และทำการแจ้งเปลี่ยนค่าพร้อมกัน
    if (this.control && this.control.control) {
      // อัปเดตค่าตัวเลขดิบ (cleanValue) เข้าฐานฟอร์มหลักเพื่อให้ Validator ตรวจสอบสูตรได้ถูกต้อง
      this.control.control.setValue(cleanValue, { emitEvent: false });

      // บังคับสลักคำบนหน้าจอผ่าน View Value Accessor ป้องกัน Angular ดึงค่าเก่ามาถมทับ
      if (this.control.valueAccessor) {
        this.control.valueAccessor.writeValue(formattedValue);
      }
    }
  }


}
