import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { SidenavDashboardComponent } from 'src/app/core/components/sidenav-dashboard/sidenav-dashboard.component';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DeviceMeasurementModule } from 'src/app/core/components/device-measurement/device-measurement.module';




@NgModule({
  declarations: [
    SidenavDashboardComponent
  ]
  ,
  imports: [
    CommonModule,
    MatSidenavModule,
    MatIconModule,
    MatSidenavModule,
    MatListModule,
    RouterModule,
    MatIconModule,
    MatButtonModule,
    MatToolbarModule,
    MatSidenavModule,
    DeviceMeasurementModule
  ],
  exports: [
    SidenavDashboardComponent
  ]
})
export class SidenavDashboardModule { }
