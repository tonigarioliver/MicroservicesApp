import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'maintenance', loadChildren: () => import('./core/pages/maintenance/maintenance.module').then(m => m.MaintenanceModule) },
  {
    path: 'dashboard',
    loadChildren: () => import('./core/pages/dashboard/dashboard.module').then(m => m.DashboardModule)
  },
  {
    path: 'login',
    loadChildren: () => import('./core/pages/login-form/login-form.module').then(m => m.LoginFormModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
