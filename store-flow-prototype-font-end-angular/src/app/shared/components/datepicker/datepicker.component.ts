import { Component, Input, forwardRef, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NG_VALUE_ACCESSOR, ControlValueAccessor } from '@angular/forms';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE, ErrorStateMatcher } from '@angular/material/core';
import { BuddhistDateAdapter, BUDDHIST_DATE_FORMATS } from '../../adapters/buddhist-date-adapter';
import { BuddhistCalendarHeaderComponent } from '../buddhist-calendar-header/buddhist-calendar-header.component';
import { TranslateService } from '@ngx-translate/core';

export class DatepickerErrorMatcher implements ErrorStateMatcher {
  constructor(private isCustomError: boolean) { }
  isErrorState(): boolean {
    return this.isCustomError;
  }
}

// กำหนดโครงสร้างข้อมูลสำหรับคอมโพเนนต์กลาง
export interface DateRangeValue {
  start: Date | null;
  end: Date | null;
}


@Component({
  selector: 'app-datepicker',
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule
  ],
  standalone: true,
  templateUrl: './datepicker.component.html',
  styleUrl: './datepicker.component.css',
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => DatepickerComponent),
    multi: true
  },
  // บังคับให้คอมโพเนนต์กลางตัวนี้ใช้ Custom Adapter ปี พ.ศ. ที่เราสร้างไว้
  { provide: DateAdapter, useClass: BuddhistDateAdapter },
  { provide: MAT_DATE_FORMATS, useValue: BUDDHIST_DATE_FORMATS },
  { provide: MAT_DATE_LOCALE, useValue: 'th-TH' } // ใช้ชื่อเดือนภาษาไทย
  ]
})
export class DatepickerComponent implements ControlValueAccessor {

  translateService = inject(TranslateService);

  @Input() label: string = '';
  @Input() placeholder: string = this.translateService.instant('text.please.datepicker.value');

  innerValue: Date | null = null;
  isDisabled = false;
  headerComponent = BuddhistCalendarHeaderComponent;

  dateObject: Date | null = null; // เก็บในคอมโพเนนต์เป็นค่า Date เพื่อคุยกับปฏิทิน
  isInvalidFormat: boolean = false; // ตัวแปรเช็กเออเรอร์เพื่อโชว์ mat-error

  onChange: any = () => { };
  onTouch: any = () => { };

  // 4. ประกาศตัวแปรรับค่า Matcher สำหรับคุมสีกรอบอินพุต
  get errorMatcher() {
    return new DatepickerErrorMatcher(this.isInvalidFormat);
  }

  get value(): Date | null { return this.innerValue; }
  set value(v: Date | null) {
    if (v !== this.innerValue) {
      this.innerValue = v;
      this.onChange(v);
    }
  }

  writeValue(value: Date): void { this.innerValue = value; }
  registerOnChange(fn: any): void { this.onChange = fn; }
  registerOnTouched(fn: any): void { this.onTouch = fn; }
  setDisabledState(isDisabled: boolean): void { this.isDisabled = isDisabled; }

  // 3. ฟังก์ชันแปลงจาก Date object ➡️ String 'dd-mm-yyyy'
  formatDateToString(date: Date | null): string | null {
    if (!date || isNaN(date.getTime())) return null;
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear() + 543; // แปลงพาสตามฐานปี พ.ศ.
    return `${day}-${month}-${year}`;
  }

  // 4. ฟังก์ชันแปลงจาก String 'dd-mm-yyyy' ➡️ Date object
  parseStringToDate(dateStr: string): Date | null {
    const regex = /^(\d{2})-(\d{2})-(\d{4})$/;
    if (!regex.test(dateStr)) return null;

    const [_, day, month, year] = dateStr.match(regex)!;
    const christianYear = parseInt(year, 10) - 543; // ลบกลับเป็น ค.ศ. เพื่อสร้าง Date Object ของระบบ

    const date = new Date(christianYear, parseInt(month, 10) - 1, parseInt(day, 10));
    return isNaN(date.getTime()) ? null : date;
  }

  // เคสที่ 1: ผู้ใช้คลิกเลือกวันที่จากปฏิทิน Popup
  onDateSelect(value: Date | null) {
    this.isInvalidFormat = false;
    this.dateObject = value;
    const formattedValue = this.formatDateToString(value);
    this.onChange(formattedValue); // คืนค่าเป็นรูปแบบ dd-mm-yyyy ส่งกลับไปที่ฟอร์มหลัก 🌟
  }

  // เคสที่ 2: ผู้ใช้พิมพ์วันที่ลงในช่องด้วยตัวเอง
  onTextInput(event: any) {
    const inputVal = event.target.value;

    // ถ้าช่องว่างเปล่าให้เคลียร์ฟอร์ม
    if (!inputVal) {
      this.isInvalidFormat = false;
      this.onChange(null);
      return;
    }

    const parsedDate = this.parseStringToDate(inputVal);

    if (parsedDate) {
      this.isInvalidFormat = false;
      this.dateObject = parsedDate;
      this.onChange(inputVal); // คืนค่าสติงที่ถูกต้องกลับไป
    } else {
      // 5. หากรูปแบบผิด (เช่น พิมพ์ผิดโครงสร้าง หรือ วันที่ไม่มีจริง เช่น 31-02-2569) สั่งแจ้งเตือนทันที
      this.isInvalidFormat = true;
      this.onChange(null); // ส่งค่า null ไปที่ฟอร์มหลักเพื่อให้ฟอร์มหลักมองว่า Invalid
    }
  }


}
