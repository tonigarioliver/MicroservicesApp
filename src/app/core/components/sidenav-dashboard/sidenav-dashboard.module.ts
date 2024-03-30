import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { SidenavDashboardComponent } from 'src/app/core/components/sidenav-dashboard/sidenav-dashboard.component';
import { MatButtonModule } from '@angular/material/button';
import { DeviceMeasurementModule } from 'src/app/core/components/device-measurement/device-measurement.module';
import { DeviceModelModule } from 'src/app/core/components/device-model/device-model.module';
import { DeviceModule } from 'src/app/core/components/device/device.module';


@NgModule({
  declarations: [
    SidenavDashboardComponent
  ]
  ,
  imports: [
    CommonModule,
    MatIconModule,
    MatListModule,
    MatIconModule,
    MatButtonModule,
    MatSidenavModule,
    DeviceModule,
    DeviceModelModule,
    DeviceMeasurementModule
  ],
  exports: [
    SidenavDashboardComponent
  ]
})
export class SidenavDashboardModule { }
