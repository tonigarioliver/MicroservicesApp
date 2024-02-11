import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { PhysicalMeasurementComponent } from 'src/app/core/components/physical-measurement/physical-measurement.component';
import { PhysicalMeasurementFormModule } from 'src/app/core/components/physical-measurement-form/physical-measurement-form.module';



@NgModule({
  declarations: [
    PhysicalMeasurementComponent,
  ],
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    MatPaginatorModule,
    PhysicalMeasurementFormModule,
  ],
  exports:[
    PhysicalMeasurementComponent,
  ]
})
export class PhysicalMeasurementModule { }
