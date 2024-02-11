import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { PhysicalMeasurementFormComponent } from 'src/app/core/components/physical-measurement-form/physical-measurement-form.component';



@NgModule({
  declarations: [PhysicalMeasurementFormComponent,],
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
  exports:[
    PhysicalMeasurementFormComponent,
  ]
})
export class PhysicalMeasurementFormModule { }
