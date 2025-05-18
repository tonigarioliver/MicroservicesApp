import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'maintenance', loadComponent: () => import('./core/pages/maintenance/maintenance.component').then(m => m.MaintenanceComponent) },
  {
    path: 'dashboard',
    loadComponent: () => import('./core/pages/dashboard/dashboard.component').then(m => m.DashboardComponent)
  },
  {
    path: 'login',
    loadComponent: () => import('./core/pages/login-form/login-form.component').then(m => m.LoginFormComponent)
  }
];

