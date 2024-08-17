import {
  HttpInterceptorFn,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpHeaders,
  HttpErrorResponse,
} from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { TokenService } from '../token/token.service';
import { Router } from '@angular/router';

export const httpTokenInterceptor: HttpInterceptorFn = (req, next) => {
  // // const token = this.tokenService.token;
  // const tokenService = inject(TokenService);
  // const token = tokenService.token;
  // if (token) {
  //   const authReq = req.clone({
  //     headers: new HttpHeaders({
  //       Authorization: 'Bearer ' + token
  //     })
  //   });
  //   return next(authReq);
  // }
  // return next(req);

  const tokenService = inject(TokenService);
  const router = inject(Router);
  // Retrieve the token from the TokenService
  const token = tokenService.token;

  // Clone the request and add the Authorization header if the token exists
  let authReq = req;
  if (token) {
    authReq = req.clone({
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`,
      }),
    });
  }

  // Proceed with the request and handle potential errors
  return next(authReq).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        // Token is expired or invalid, redirect to the login page
        alert('Token is expired or invalid, redirect to the login page');
        router.navigate(['']);
      }
      return throwError(error);
    })
  );
};

// export const httpTokenInterceptor: HttpInterceptorFn = (req, next) => {
//   const tokenService = inject(TokenService);
//   const token = tokenService.token;

//   if (token) {
//     const authReq = req.clone({
//       headers: req.headers.set('Authorization', `Bearer ${token}`)
//     });
//     return next(authReq);
//   }

//   return next(req);
// };
