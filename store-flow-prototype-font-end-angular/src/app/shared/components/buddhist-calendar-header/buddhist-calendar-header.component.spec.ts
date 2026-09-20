import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BuddhistCalendarHeaderComponent } from './buddhist-calendar-header.component';
import { MatCalendar } from '@angular/material/datepicker';
import { DateAdapter } from '@angular/material/core';
import { ChangeDetectorRef } from '@angular/core';
import { Subject, of } from 'rxjs';

describe('BuddhistCalendarHeaderComponent', () => {
  let component: BuddhistCalendarHeaderComponent;
  let fixture: ComponentFixture<BuddhistCalendarHeaderComponent>;

  // 💡 1. สร้างก้อน Mock Object ของ MatCalendar จำลองค่าเท่าที่โค้ดจริงเรียกใช้
  const mockMatCalendar = {
    stateChanges: new Subject<void>(), // รองรับการ .pipe(takeUntil(...)).subscribe(...) ใน constructor
    activeDate: new Date(2026, 8, 14), // จำลองวันที่เริ่มต้นเป็น 14 ก.ย. 2026
    currentView: 'month',
    addCalendarMonths: () => new Date(),
    addCalendarYears: () => new Date()
  };

  // 💡 2. สร้างก้อน Mock ของ DateAdapter จำลองฟังก์ชันการบวกลบเดือน/ปี
  const mockDateAdapter = {
    addCalendarMonths: (date: Date, months: number) => {
      const d = new Date(date);
      d.setMonth(d.getMonth() + months);
      return d;
    },
    addCalendarYears: (date: Date, years: number) => {
      const d = new Date(date);
      d.setFullYear(d.getFullYear() + years);
      return d;
    }
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        BuddhistCalendarHeaderComponent // ใส่ตรงนี้เพราะเป็น Standalone Component
      ],
      providers: [
        // 💡 3. ฉีดของจำลอง (Mock) ทั้งหมดเข้าไปแทนที่ของจริงในระบบทดสอบ
        { provide: MatCalendar, useValue: mockMatCalendar },
        { provide: DateAdapter, useValue: mockDateAdapter },
        ChangeDetectorRef
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(BuddhistCalendarHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges(); // กระตุ้นให้คอมโพเนนต์ทำงานครั้งแรก
  });

  // เทสที่ 1: ตรวจสอบการสร้างคอมโพเนนต์
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // เทสที่ 2: ตรวจสอบ Logic การแปลงปี ค.ศ. เป็น พ.ศ. ของคุณ
  it('ควรคำนวณและแสดงค่าเดือนไทยและปี พ.ศ. ได้ถูกต้อง', () => {
    // วันที่จำลองใน mock คือ เดือน 8 (กันยายนใน JavaScript) ปี 2026
    // ผลลัพธ์ที่คาดหวัง: "ก.ย. 2569" (2026 + 543 = 2569)
    expect(component.periodLabel).toBe('ก.ย. 2569');
  });
});
