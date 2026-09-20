import { Directive, HostListener, ElementRef } from '@angular/core';
import { NgControl } from '@angular/forms';


@Directive({
  selector: '[appThaiOnly]',
  standalone: true
})
export class ThaiOnlyDirective {
  constructor(private el: ElementRef, private control: NgControl) { }

  @HostListener('input', ['$event'])
  onInputChange(event: any) {
    const input = this.el.nativeElement;
    const rawValue = input.value;

    // ล้างสิ่งที่ไม่ใช่ตัวอักษรภาษาไทย และไม่ใช่ช่องว่าง (Space) ออกไปให้หมด
    const cleanValue = rawValue.replace(/[^ก-๙\s]/g, '');

    // อัปเดตค่ากลับไปโชว์ที่หน้าจอ และส่งค่าเข้าระบบฟอร์มหลัก
    input.value = cleanValue;
    if (this.control && this.control.control) {
      this.control.control.setValue(cleanValue, { emitEvent: false });
    }
  }
}
