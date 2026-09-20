import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AdminLayout } from './admin-layout';
import { JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';
import { provideTranslateService } from '@ngx-translate/core';
import { provideRouter } from '@angular/router';

describe('AdminLayout', () => {
  let component: AdminLayout;
  let fixture: ComponentFixture<AdminLayout>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminLayout],
      providers: [provideTranslateService(),
        JwtHelperService,
      { provide: JWT_OPTIONS, useValue: JWT_OPTIONS }, provideRouter([])]
    }).compileComponents();

    fixture = TestBed.createComponent(AdminLayout);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
