import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeviceMeasurementFormComponent } from './device-measurement-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDialogModule } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';



@NgModule({
  declarations: [
    DeviceMeasurementFormComponent
  ],
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
  ],
  exports: [
    DeviceMeasurementFormComponent,
  ],
})
export class DeviceMeasurementFormModule { }
