import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardModule } from 'src/app/core/pages/dashboard/dashboard.module';
const routes: Routes = [
  { path: 'maintenance', loadChildren: () => import('./core/pages/maintenance/maintenance.module').then(m => m.MaintenanceModule) },
  {
    path: 'dashboard',
    loadChildren: () => import('./core/pages/dashboard/dashboard.module').then(m => m.DashboardModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
