import { Component } from '@angular/core';
import { AuthenticationService } from '../../services/services';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CodeInputModule } from 'angular-code-input';

@Component({
  selector: 'app-activate-account',
  standalone: true,
  imports: [CommonModule, CodeInputModule],
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.scss'
})
export class ActivateAccountComponent {

  message: string = '';
  isOkay: boolean = false;
  subbmitted: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService,
  ){}

  onCodeCompleted(token: string) {
    this.comfirmAccount(token)
  }

  comfirmAccount(token: string) {
    this.authService.confirm({ token }).subscribe({
      next: () => {
        this.message = 'Your Account has been Successfully Activated\nNow you can proceed to LogIn';
        this.subbmitted = true;
        this.isOkay = true;
      },
      error: (err) => {
        this.message = 'Token has been expired or invalid';
        this.subbmitted = true; 
        this.isOkay = false;
      }
    })
  }

  redirectToLogin() {
    this.router.navigate([''])
  }

}
