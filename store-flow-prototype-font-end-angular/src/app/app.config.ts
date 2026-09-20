import { ApplicationConfig, provideZonelessChangeDetection } from '@angular/core';
import { provideRouter, TitleStrategy } from '@angular/router';
import { routes } from './app.routes';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { provideTranslateService } from '@ngx-translate/core';
import { provideTranslateHttpLoader } from '@ngx-translate/http-loader';
import { authInterceptor } from './core/interceptors/auth-interceptor';
import { CustomTitleStrategy } from '../app/core/services/custom-title.strategy';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { JWT_OPTIONS, JwtHelperService } from '@auth0/angular-jwt';

import { USER_SERVICE_TOKEN } from './core/interfaces/user-service.interface';
import { UsersService } from './core/services/users.service';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZonelessChangeDetection(),
    provideAnimationsAsync(),
    provideRouter(routes),
    provideHttpClient(withFetch(), withInterceptors([authInterceptor])),
    { provide: TitleStrategy, useClass: CustomTitleStrategy },

    provideTranslateService({
      loader: provideTranslateHttpLoader({
        prefix: './assets/i18n/',
        suffix: '.json'
      })
    }),
    // ลงทะเบียนเพื่อให้ระบบ DI รู้จักและสามารถใช้ inject(JwtHelperService) ได้
    { provide: JWT_OPTIONS, useValue: JWT_OPTIONS },
    JwtHelperService,
    { provide: USER_SERVICE_TOKEN, useClass: UsersService }
  ]
};
