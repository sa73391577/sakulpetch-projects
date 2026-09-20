import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Login } from './login.component';
import { JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';
import { provideTranslateService } from '@ngx-translate/core';
import { provideRouter } from '@angular/router';

describe('Login', () => {
  let component: Login;
  let fixture: ComponentFixture<Login>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Login],
      providers: [provideTranslateService(), JwtHelperService, { provide: JWT_OPTIONS, useValue: JWT_OPTIONS }, provideRouter([])]
    }).compileComponents();

    fixture = TestBed.createComponent(Login);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
