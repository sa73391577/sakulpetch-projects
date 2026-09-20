import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideTranslateService } from '@ngx-translate/core';
import { UserManagementComponent } from './user-management.component';
import { JwtHelperService } from '@auth0/angular-jwt';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { vi, type Mock } from 'vitest';
import { USER_SERVICE_TOKEN } from '../../core/interfaces/user-service.interface';
import { of, throwError } from 'rxjs';

describe('UserManagementComponent', () => {
  let component: UserManagementComponent;
  let fixture: ComponentFixture<UserManagementComponent>;
  let mockUsersService: { getByUsername: Mock; add: Mock; update: Mock; list: Mock };

  beforeEach(async () => {

    mockUsersService = {
      getByUsername: vi.fn().mockReturnValue(of({ data: { nameTH: 'สมชาย', username: 'test_edit' } })),
      add: vi.fn().mockReturnValue(of({ success: true })),
      update: vi.fn().mockReturnValue(of({ success: true })),
      // ฟังก์ชัน list() จำลองรายชื่อสมาชิกดักไว้ตรงนี้ เพื่อไม่ให้เกิดคำสั่ง error ตอนรันหน้าแรก
      list: vi.fn().mockReturnValue(of({
        data: [
          { id: 1, username: 'user01', nameTH: 'ทดสอบ คนที่หนึ่ง' },
          { id: 2, username: 'user02', nameTH: 'ทดสอบ คนที่สอง' }
        ]
      }))
    };

    await TestBed.configureTestingModule({
      imports: [
        UserManagementComponent
      ],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        provideTranslateService(),
        { provide: USER_SERVICE_TOKEN, useValue: mockUsersService },
        {
          provide: JwtHelperService,
          useValue: {
            decodeToken: vi.fn(),
            isTokenExpired: vi.fn(),
            getTokenExpirationDate: vi.fn()
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(UserManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
