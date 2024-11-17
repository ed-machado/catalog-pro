import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import Swal from 'sweetalert2';

export const meuhttpInterceptor: HttpInterceptorFn = (request, next) => {

  let router = inject(Router);

  let token = localStorage.getItem('token');
  if (token && !router.url.includes('/login')) {
    request = request.clone({
      setHeaders: { Authorization: 'Bearer ' + token },
    });
  }

  return next(request).pipe(
    catchError((err: any) => {
      if (err instanceof HttpErrorResponse) {
        if (err.status === 401) {
          Swal.fire({
            icon: 'error',
            title: 'Unauthorized',
            text: '401 - You are not authorized to access this resource.',
          }).then(() => {
            router.navigate(['/login']);
          });
        } else if (err.status === 403) {
          Swal.fire({
            icon: 'error',
            title: 'Forbidden',
            text: '403 - Access to this resource is forbidden.',
          }).then(() => {
            router.navigate(['/login']);
          });
        } else {
          Swal.fire({
            icon: 'error',
            title: 'HTTP Error',
            text: `An error occurred: ${err.message}`,
          });
        }
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: `An error occurred: ${err.message}`,
        });
      }

      return throwError(() => err);
    })
  );
};
