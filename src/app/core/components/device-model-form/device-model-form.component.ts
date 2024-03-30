import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { DeviceModelRequest } from 'src/app/core/models/device-model-request';
import { DeviceModelApiService } from 'src/app/core/services/api/device-model-api.service';

@Component({
  selector: 'app-create-device-model',
  templateUrl: './device-model-form.component.html',
  styleUrls: ['./device-model-form.component.css']
})
export class DeviceModelFormComponent {
  deviceModelRequest: DeviceModelRequest = {
    deviceModelId: 0,
    name: '',
    serialNumber: ''
  };

  deviceModelForm: FormGroup

  isEditMode: boolean

  constructor(
    private dialogRef: MatDialogRef<DeviceModelFormComponent>,
    @Inject(MAT_DIALOG_DATA) public dialogData: { deviceModelRequest: DeviceModelRequest; isEditMode: boolean },
    private deviceModelApiService: DeviceModelApiService,
    private formBuilder: FormBuilder,
  ) {
    this.deviceModelRequest = dialogData.deviceModelRequest;
    this.deviceModelForm = this.formBuilder.group({
      name: [dialogData.deviceModelRequest.name, [Validators.required]],
      serialNumber: [dialogData.deviceModelRequest.serialNumber, [Validators.required]],
    })
    this.isEditMode = dialogData.isEditMode
  }

  saveChangesDialog(): void {
    this.deviceModelRequest = {
      ...this.deviceModelRequest,
      deviceModelId: this.deviceModelRequest.deviceModelId,
      ...this.deviceModelForm.value
    };
    if (this.isEditMode) {
      this.deviceModelApiService.updateDeviceModelRequest(this.deviceModelRequest).subscribe(
        response => {
          this.dialogRef.close()
        },
      )
    } else {
      this.deviceModelApiService.createDeviceModelRequest(this.deviceModelRequest).subscribe(
        response => {
          this.dialogRef.close()
        },
      )
    }
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}
