// login.component.ts
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { MdbFormsModule } from 'mdb-angular-ui-kit/forms';
import { MdbRippleModule } from 'mdb-angular-ui-kit/ripple';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [MdbFormsModule, FormsModule, MdbRippleModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  user!: string;
  password!: string;
  showPassword = false;

  router = inject(Router);

  login() {
    if(this.user === 'admin' && this.password === 'admin') {
      this.router.navigate(['admin/products']);
    } else {
      alert('Wrong user or password');
    }
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }
}
