import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthResponse } from 'src/app/core/models/auth-response';
import { LoginForm } from 'src/app/core/models/login-form';
import { AuthApiService } from 'src/app/core/services/api/auth-api.service';
import { ToastService } from 'src/app/core/services/toast.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  public loginForm: FormGroup;
  loginData: LoginForm = {
    userName: '',
    password: ''
  }

  constructor(
    private readonly authApiService: AuthApiService,
    private fb: FormBuilder,
    private readonly toastService: ToastService
  ) {
    this.loginForm = this.fb.group({
      userName: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      userName: ['', Validators.required],
      password: ['', Validators.required]
    });
  }


  onSubmit(): void {
    if (this.loginForm?.valid) {
      const formValue: LoginForm = this.loginForm.value;
      this.loginData = formValue
      this.authApiService.loginUser(formValue).subscribe(
        response => {
          this.doLoginLogic(response);
        },
        error => {
          this.toastService.showError(error.message || 'An error occurred');
        }
      );
    }
  }

  private doLoginLogic(response: AuthResponse): void {
    console.log(response)
    const token = response.jwtToken;
    localStorage.setItem('jwtToken', token);
    localStorage.setItem('UserName', this.loginData.userName)
  }
}
