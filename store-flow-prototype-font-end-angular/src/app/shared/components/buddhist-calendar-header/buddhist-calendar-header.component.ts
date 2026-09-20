import { Component, Inject, ChangeDetectionStrategy, ChangeDetectorRef, OnDestroy } from '@angular/core';
import { MatCalendar } from '@angular/material/datepicker';
import { DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-buddhist-calendar-header',
  standalone: true,
  imports: [],
  templateUrl: './buddhist-calendar-header.component.html',
  styleUrl: './buddhist-calendar-header.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BuddhistCalendarHeaderComponent {
  private _destroyed = new Subject<void>();

  constructor(
    private _calendar: MatCalendar<Date>,
    private _dateAdapter: DateAdapter<Date>,
    cdr: ChangeDetectorRef
  ) {
    _calendar.stateChanges
      .pipe(takeUntil(this._destroyed))
      .subscribe(() => cdr.markForCheck());
  }

  ngOnDestroy() {
    this._destroyed.next();
    this._destroyed.complete();
  }

  // ดึงข้อความ เดือน และ ปี พ.ศ. มาแสดงผล
  get periodLabel() {
    const activeDate = this._calendar.activeDate as any;
    const months = ['ม.ค.', 'ก.พ.', 'มี.ค.', 'เม.ย.', 'พ.ค.', 'มิ.ย.', 'ก.ค.', 'ส.ค.', 'ก.ย.', 'ต.ค.', 'พ.ย.', 'ธ.ค.'];
    const thaiMonth = months[activeDate.getMonth()];
    const thaiYear = activeDate.getFullYear() + 543; // แปลงเป็น พ.ศ.

    return `${thaiMonth} ${thaiYear}`;
  }

  currentPeriodClicked() {
    this._calendar.currentView = this._calendar.currentView === 'month' ? 'multi-year' : 'month';
  }

  previousClicked() {
    this._calendar.activeDate = this._calendar.currentView === 'month'
      ? this._dateAdapter.addCalendarMonths(this._calendar.activeDate, -1)
      : this._dateAdapter.addCalendarYears(this._calendar.activeDate, -1);
  }

  nextClicked() {
    this._calendar.activeDate = this._calendar.currentView === 'month'
      ? this._dateAdapter.addCalendarMonths(this._calendar.activeDate, 1)
      : this._dateAdapter.addCalendarYears(this._calendar.activeDate, 1);
  }
}
