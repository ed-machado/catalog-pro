// login.component.ts
import { Component, inject, ViewEncapsulation } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { MdbRippleModule } from 'mdb-angular-ui-kit/ripple';
import { LoginService } from '../../../auth/login.service';
import { Login } from '../../../auth/login';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

interface RegisterData {
  username: string;
  password: string;
  confirmPassword: string;
}

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [MdbFormsModule, FormsModule, MdbRippleModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent {
  loginData: Login = new Login();
  registerData: RegisterData = {
    username: '',
    password: '',
    confirmPassword: ''
  };
  showPassword = false;
  isRegisterMode = false;
  errorMessage = '';

  router = inject(Router);
  loginService = inject(LoginService);

  constructor() {
    this.loginService.removerToken();
  }

  login() {
    this.loginService.login(this.loginData).subscribe({
      next: (token: string) => {
        if (token) {
          this.loginService.addToken(token);
          this.router.navigate(['/admin/products']);
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Login Failed',
            text: 'Invalid login credentials. Please try again.',
          });
        }
      },
      error: (err) => {
        console.error('Login error:', err);
        Swal.fire({
          icon: 'error',
          title: 'Login Error',
          text: err.error?.message || 'An error occurred during login. Please try again.',
        });
      }
    });
  }

  register() {
    this.errorMessage = '';

    if (!this.registerData.username || !this.registerData.password || !this.registerData.confirmPassword) {
      this.errorMessage = 'Please fill in all fields';
      return;
    }

    if (this.registerData.password !== this.registerData.confirmPassword) {
      this.errorMessage = 'Passwords do not match';
      return;
    }

    if (this.registerData.password.length < 6) {
      this.errorMessage = 'Password must be at least 6 characters long';
      return;
    }

    const registerPayload = {
      username: this.registerData.username,
      password: this.registerData.password
    };

    this.loginService.register(registerPayload).subscribe({
      next: (response) => {
        if (response) {
          this.isRegisterMode = false;
          this.loginData.username = this.registerData.username;
          this.loginData.password = this.registerData.password;

          this.registerData = {
            username: '',
            password: '',
            confirmPassword: ''
          };

          Swal.fire({
            icon: 'success',
            title: 'Registration Successful',
            text: 'You can now login.',
          }).then(() => {
            this.login();
          });
        }
      },
      error: (err) => {
        console.error('Registration error:', err);
        this.errorMessage = err.error?.message || 'Registration failed. Please try again.';
        Swal.fire({
          icon: 'error',
          title: 'Registration Failed',
          text: this.errorMessage,
          });
        }
      });
    }

    togglePassword() {
      const passwordField = document.getElementById('password') as HTMLInputElement;
      if (passwordField.type === 'password') {
        passwordField.type = 'text';
      } else {
        passwordField.type = 'password';
      }
    }
  }
