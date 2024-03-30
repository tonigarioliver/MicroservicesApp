import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from 'src/app/core/pages/dashboard/dashboard.component';
import { DashboardRoutingModule } from 'src/app/core/pages/dashboard/dashboard-routing.module';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { SidenavDashboardModule } from 'src/app/core/components/sidenav-dashboard/sidenav-dashboard.module';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RouterModule } from '@angular/router';




@NgModule({
  declarations: [
    DashboardComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    SidenavDashboardModule,
    MatSidenavModule,
    MatIconModule,
    MatSidenavModule,
    MatListModule,
    RouterModule,
    MatIconModule,
    MatButtonModule,
    MatToolbarModule,
    MatSidenavModule,

  ], exports: [
    DashboardComponent
  ]
})
export class DashboardModule { }
