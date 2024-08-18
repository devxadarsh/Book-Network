import { Component } from '@angular/core';
import { AuthenticationRequest } from '../../services/models';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthenticationService } from '../../services/services';
import { Router } from '@angular/router';
import { TokenService } from '../../services/token/token.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  authRequest: AuthenticationRequest = { email: '', password: '' };
  errorMsg: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService
  ) {}

  login() {
    this.errorMsg = [];
    this.authService.authenticate({ body: this.authRequest }).subscribe({
      next: (res) => {
        this.tokenService.token = res.token as string;
        this.router.navigate(['books']);
      },
      error: (err) => {
        console.log(err);
        if (err.error.validationError) {
          this.errorMsg = err.error.validationError;
        } else {
          this.errorMsg.push(err.error.error);
        }
      },
    });
  }

  register() {
    this.router.navigate(['/register']);
  }
}
