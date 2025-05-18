import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterFormComponent } from 'src/app/core/pages/register-form/register-form.component';

const routes: Routes = [{ path: '', component: RegisterFormComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RegisterFormRoutingModule { }
