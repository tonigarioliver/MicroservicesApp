import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card';
import { LoginFormComponent } from 'src/app/core/components/login-form/login-form.component';
import { LoginFormRoutingModule } from 'src/app/core/components/login-form/login-form-routing.module';


@NgModule({
  declarations: [LoginFormComponent],
  imports: [
    CommonModule,
    LoginFormRoutingModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule
  ],
  exports: [LoginFormComponent]
})
export class LoginFormModule { }
