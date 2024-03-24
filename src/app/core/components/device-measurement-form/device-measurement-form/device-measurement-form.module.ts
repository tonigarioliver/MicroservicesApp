import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeviceMeasurementFormComponent } from './device-measurement-form.component';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    DeviceMeasurementFormComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  exports: [
    DeviceMeasurementFormComponent,
  ],
})
export class DeviceMeasurementFormModule { }
