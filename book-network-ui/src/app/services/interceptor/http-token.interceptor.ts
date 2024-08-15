import { HttpInterceptorFn, HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenService } from '../token/token.service';

export const httpTokenInterceptor: HttpInterceptorFn = (req, next) => {
  // const token = this.tokenService.token;
  const tokenService = inject(TokenService);
  const token = tokenService.token;
  if (token) {
    const authReq = req.clone({
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + token
      })
    });
    return next(authReq);
  }
  return next(req);
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
