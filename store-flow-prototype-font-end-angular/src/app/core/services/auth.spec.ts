import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth';
import { JwtHelperService, JWT_OPTIONS } from '@auth0/angular-jwt';
import { provideTranslateService } from '@ngx-translate/core';
import { provideRouter } from '@angular/router';

describe('Auth', () => {
  let service: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideTranslateService(), JwtHelperService, { provide: JWT_OPTIONS, useValue: JWT_OPTIONS }, provideRouter([])]
    });
    service = TestBed.inject(AuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
