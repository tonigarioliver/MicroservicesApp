import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { AuthResponse } from 'src/app/core/models/auth-response';
import { LoginForm } from 'src/app/core/models/login-form';
import { RegisterForm } from 'src/app/core/models/register-form';

@Injectable({
  providedIn: 'root'
})
export class AuthApiService {

  private serviceUrl = 'http://localhost:8080/auth/api/v1/user'; // Replace with your API URL

  constructor(private http: HttpClient) { }

  loginUser(loginForm: LoginForm): Observable<AuthResponse> {
    const url = `${this.serviceUrl}/login`;
    return this.http.post<AuthResponse>(url, loginForm)
      .pipe(map(response => response));
  }

  registerUser(registerForm: RegisterForm): Observable<AuthResponse> {
    const url = `${this.serviceUrl}/register`;
    return this.http.post<AuthResponse>(this.serviceUrl, registerForm)
      .pipe(map(response => response));
  }


}
