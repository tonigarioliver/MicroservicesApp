import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTabsModule } from '@angular/material/tabs';
import { MaintenanceRoutingModule } from './maintenance-routing.module';
import { MaintenanceComponent } from './maintenance.component';
import { DeviceModelModule } from 'src/app/core/components/device-model/device-model.module';
import { PhysicalMeasurementModule } from 'src/app/core/components/physical-measurement/physical-measurement.module';

@NgModule({
  declarations: [
    MaintenanceComponent,
  ],
  imports: [
    CommonModule,
    MaintenanceRoutingModule,
    MatTabsModule,
    DeviceModelModule,
    PhysicalMeasurementModule,
  ]
})
export class MaintenanceModule { }
