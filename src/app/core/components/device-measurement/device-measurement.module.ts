import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeviceMeasurementComponent } from './device-measurement.component';
import { DeviceMeasurementFormModule } from 'src/app/core/components/device-measurement-form/device-measurement-form.module';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';



@NgModule({
  declarations: [
    DeviceMeasurementComponent
  ],
  imports: [
    CommonModule,
    DeviceMeasurementFormModule,
    MatIconModule,
    MatPaginatorModule,
    MatTableModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  exports: [
    DeviceMeasurementComponent,
  ],
})
export class DeviceMeasurementModule { }
