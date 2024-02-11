import { Component, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DeviceModelFormComponent } from 'src/app/core/components/device-model-form/device-model-form.component';
import { PhysicalMeasurementRequest } from 'src/app/core/models/physical-measurement-request';
import { PhysicalMeasurementApiService } from 'src/app/core/services/physical-measurement-api.service';

@Component({
  selector: 'app-physical-measurement-form',
  templateUrl: './physical-measurement-form.component.html',
  styleUrls: ['./physical-measurement-form.component.css']
})
export class PhysicalMeasurementFormComponent {
  physicalMeasurementRequest: PhysicalMeasurementRequest = {
    physicalMeasurementId:0,
    name: '',
    unit: ''
  };

  physicalMeasurementForm: FormGroup

  isEditMode: boolean

  constructor(
    private dialogRef: MatDialogRef<DeviceModelFormComponent>,
    @Inject(MAT_DIALOG_DATA) public dialogData: { physicalMeasurementRequest: PhysicalMeasurementRequest; isEditMode: boolean },
    private physicalMeasurementApiService: PhysicalMeasurementApiService,
    private formBuilder: FormBuilder,
  ) {
    this.physicalMeasurementRequest = dialogData.physicalMeasurementRequest;
    this.physicalMeasurementForm = this.formBuilder.group({
      name: [dialogData.physicalMeasurementRequest.name, [Validators.required]],
      unit: [dialogData.physicalMeasurementRequest.unit, [Validators.required]],
    })
    this.isEditMode = dialogData.isEditMode
  }

  saveChangesDialog(): void {
    this.physicalMeasurementRequest = {
      ...this.physicalMeasurementRequest,
      physicalMeasurementId: this.physicalMeasurementRequest.physicalMeasurementId, 
      ...this.physicalMeasurementForm.value  
    };
    if (this.isEditMode) {
      this.physicalMeasurementApiService.updatePhsyicalMeasurement(this.physicalMeasurementRequest).subscribe(
        response => {
          console.log('Respuesta exitosa:', response);
          this.dialogRef.close()
        },
      )
    } else {
      this.physicalMeasurementApiService.createPhysicalMeasurement(this.physicalMeasurementRequest).subscribe(
        response => {
          console.log('Respuesta exitosa:', response);
          this.dialogRef.close()
        },
      )
    }
  }
  onNoClick(): void {
    this.dialogRef.close();
  }

}
