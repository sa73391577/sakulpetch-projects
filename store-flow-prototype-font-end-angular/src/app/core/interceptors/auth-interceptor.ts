// core/interceptors/auth-interceptor.ts
import { HttpInterceptorFn } from '@angular/common/http';
import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const platformId = inject(PLATFORM_ID);
  const router = inject(Router);

  // ป้องกัน SSR: ถ้ารันบนเซิร์ฟเวอร์ ให้ปล่อย Request ผ่านไปเลย ไม่ต้องแตะ localStorage
  if (!isPlatformBrowser(platformId)) {
    return next(req);
  }

  //URL of API Do not check token
  const urlIgnoreList = ['/login', '.json', 'assets'];
  const isIgnoreURL = urlIgnoreList.some(path => req.url.includes(path));

  if (isIgnoreURL) {
    return next(req);
  }

  // ปลอดภัยแน่นอนเพราะอยู่ภายใต้เงื่อนไข isPlatformBrowser ด้านบนแล้ว
  const token = localStorage.getItem('token');
  if (token) {
    const newReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(newReq);
  }

  router.navigate(["/error_401"]);
  return next(req);
};
