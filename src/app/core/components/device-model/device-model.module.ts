import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeviceModelComponent } from 'src/app/core/components/device-model/device-model.component';
import { MatDialogModule } from '@angular/material/dialog';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';

import { MatPaginatorModule } from '@angular/material/paginator';
import { DeviceModelFormModule } from 'src/app/core/components/device-model-form/device-model-form.module';



@NgModule({
  declarations: [DeviceModelComponent],
  imports: [
    CommonModule,
    MatDialogModule,
    MatTableModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    MatIconModule,
    ReactiveFormsModule,
    MatPaginatorModule,
    DeviceModelFormModule,
  ],
  exports:[DeviceModelComponent]
})
export class DeviceModelModule { }
