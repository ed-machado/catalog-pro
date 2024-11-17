import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { jwtDecode, JwtPayload } from "jwt-decode";
import { Login } from './login';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  http = inject(HttpClient);
  API = "http://backend:8080/api";


  constructor() { }


  login(login: Login): Observable<string> {
    return this.http.post<string>(`${this.API}/login`, login, {responseType: 'text' as 'json'});
  }

  register(login: Login): Observable<string> {
    return this.http.post<string>(`${this.API}/register`, login, {responseType: 'text' as 'json'});
  }

  addToken(token: string) {
    localStorage.setItem('token', token);
  }

  removerToken() {
    localStorage.removeItem('token');
  }

  getToken() {
    return localStorage.getItem('token');
  }

  jwtDecode() {
    let token = this.getToken();
    if (token) {
      return jwtDecode<JwtPayload>(token);
    }
    return "";
  }

  hasPermission(role: string) {
    let user = this.jwtDecode() as User;
    if (user.role == role)
      return true;
    else
      return false;
  }


}
