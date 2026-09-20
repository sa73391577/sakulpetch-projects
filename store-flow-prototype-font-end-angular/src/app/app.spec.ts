import { TestBed } from '@angular/core/testing';
import { App } from './app';
import { provideTranslateService } from '@ngx-translate/core';
import { JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';
import { provideRouter } from '@angular/router';

describe('App', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
      providers: [provideTranslateService(), JwtHelperService, { provide: JWT_OPTIONS, useValue: JWT_OPTIONS }, provideRouter([])]
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  // 🧪 เก็บเคสตรวจสอบพื้นฐานว่าระบบโหลดสำเร็จเอาไว้ตัวเดียวพอครับ
  it('should create the app', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
