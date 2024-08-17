import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  constructor() {}

  set token(token: string) {
    if (token) {
      localStorage.setItem('token', token);
    } else {
      localStorage.removeItem(token);
    }
  }

  get token() {
    return localStorage.getItem('token') as string;
  }
}
