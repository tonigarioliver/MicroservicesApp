import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeviceMeasurementComponent } from './device-measurement.component';
import { DeviceMeasurementFormModule } from 'src/app/core/components/device-measurement-form/device-measurement-form/device-measurement-form.module';



@NgModule({
  declarations: [
    DeviceMeasurementComponent
  ],
  imports: [
    CommonModule,
    DeviceMeasurementFormModule,
  ],
  exports: [
    DeviceMeasurementComponent,
  ],
})
export class DeviceMeasurementModule { }
