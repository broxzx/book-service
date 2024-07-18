import {Component} from '@angular/core';
import {AuthenticationRequest} from "../../services/models/authentication-request";
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    FormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  authRequest: AuthenticationRequest = {email: '', password: ''}
  errorMsg: Array<String> = [];

  constructor(private router: Router, private authService: AuthenticationService) {
  }

  login() {
    this.errorMsg = [];

    this.authService.authenticate({
      body: this.authRequest
    }).subscribe({
      next: () => {
        // todo: save the token
        this.router.navigate(['books']);
      },
      error: err => {
        console.log(err)
        if (err.errors.validationErrors) {
          this.errorMsg = err;
        } else {
          this.errorMsg.push(err.error.error);
        }
      }
    })
  }

  register() {
    this.router.navigate(['register']);
  }
}
