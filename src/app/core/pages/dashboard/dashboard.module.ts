import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from 'src/app/core/pages/dashboard/dashboard.component';
import { DashboardRoutingModule } from 'src/app/core/pages/dashboard/dashboard-routing.module';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { SidenavDashboardModule } from 'src/app/core/components/sidenav-dashboard/sidenav-dashboard.module';


@NgModule({
  declarations: [
    DashboardComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    SidenavDashboardModule,
    MatIconModule,
    MatIconModule,
    MatButtonModule,
    MatToolbarModule,
  ], exports: [
    DashboardComponent
  ]
})
export class DashboardModule { }
