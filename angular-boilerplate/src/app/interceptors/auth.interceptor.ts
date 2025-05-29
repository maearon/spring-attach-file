import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);
  const toastr = inject(ToastrService);
  const platformId = inject(PLATFORM_ID);

  // Ensure we're in the browser environment before accessing local/session storage
  if (isPlatformBrowser(platformId)) {
    const token =
      localStorage.getItem('token') || sessionStorage.getItem('token');
    // const refreshToken =
    //   localStorage.getItem('refresh_token') ||
    //   sessionStorage.getItem('refresh_token');

    // Clone the request and add the authorization header if token exists
    if (token) {
      const authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });

      return next(authReq).pipe(
        catchError((error) => {
          // Handle 401 errors for session checks silently
          if (error.status === 401 && req.url.includes('/sessions')) {
            return next(req);
          }

          // Handle other 401 errors (unauthorized)
          if (error.status === 401) {
            // localStorage.removeItem('token');
            // localStorage.removeItem('refresh_token');
            // sessionStorage.removeItem('token');
            // sessionStorage.removeItem('refresh_token');
            // router.navigate(['/login']);
            toastr.error('Your session has expired. Please log in again.');
          }

          return throwError(() => error);
        }),
      );
    }
  }

  return next(req);
};
