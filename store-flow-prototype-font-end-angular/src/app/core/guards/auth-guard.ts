import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth';
import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const platformId = inject(PLATFORM_ID);

  console.log("authService.getUserRole : ", authService.getUserRole());

  // Run On Server
  if (!isPlatformBrowser(platformId)) {
    return true;
  }

  // on Browser
  if (authService.isLoggedIn()) {
    return true;
  }

  //if do not login.
  router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
  return false;
};
