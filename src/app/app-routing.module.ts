import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
const routes: Routes = [
  { path: 'maintenance', loadChildren: () => import('./core/pages/maintenance/maintenance.module').then(m => m.MaintenanceModule) },
  { path: 'dashboard', loadChildren: () => import('./core/pages/dashboard/dashboard.module').then(m => m.Dashboardodule) },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' } // Redirige a 'main' cuando la ruta está vacía
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
