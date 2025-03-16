import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { DeviceApiService } from 'src/app/core/services/api/device-api.service';
import { Device } from 'src/app/core/models/device';
import { MeasurementType } from 'src/app/core/models/measurement-type';
import { MeasurementTypeApiService } from 'src/app/core/services/api/measurement-type-api.service';
import { DeviceMeasurement } from 'src/app/core/models/device-measurement';
import { DeviceMeasurementRequest } from 'src/app/core/models/device-measurement-request';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogTitle, MatDialogContent, MatDialogActions } from '@angular/material/dialog';
import { DeviceMeasurementApiService } from 'src/app/core/services/api/device-measurement-api.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { CdkScrollable } from '@angular/cdk/scrolling';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatSelect } from '@angular/material/select';
import { NgFor } from '@angular/common';
import { MatOption } from '@angular/material/core';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';

@Component({
    selector: 'app-device-measurement-form',
    templateUrl: './device-measurement-form.component.html',
    styleUrls: ['./device-measurement-form.component.css'],
    standalone: true,
    imports: [MatDialogTitle, CdkScrollable, MatDialogContent, ReactiveFormsModule, MatFormField, MatLabel, MatSelect, NgFor, MatOption, MatInput, MatDialogActions, MatButton]
})
export class DeviceMeasurementFormComponent implements OnInit {
  deviceMeasurementForm: FormGroup;
  devices: Device[] = [];
  measurementTypes: MeasurementType[] = []
  request: DeviceMeasurementRequest;
  isEditMode: boolean


  constructor(
    private dialogRef: MatDialogRef<DeviceMeasurementFormComponent>,
    @Inject(MAT_DIALOG_DATA) public dialogData: { request: DeviceMeasurementRequest; isEditMode: boolean },
    private fb: FormBuilder,
    private deviceMeasurementApiService: DeviceMeasurementApiService,
    private deviceApiService: DeviceApiService,
    private measurementTypeApiService: MeasurementTypeApiService,
    private toastService: ToastService,
  ) {
    this.isEditMode = dialogData.isEditMode
    this.request = dialogData.request;
    this.deviceMeasurementForm = this.fb.group({
      device: [null, Validators.required],
      measurementName: ['', Validators.required],
      units: ['', Validators.required],
      measurementType: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    this.deviceApiService.getDevices().subscribe(devices => this.devices = devices);
    this.measurementTypeApiService.getMeasurementTypes().subscribe(types => this.measurementTypes = types);
    this.deviceMeasurementForm = this.fb.group({
      deviceId: [this.getFirstMatchingDevice, [Validators.required]],
      measurementName: [this.request.name, [Validators.required]],
      units: [this.request.unit, [Validators.required]],
      measurementTypeId: [this.getFirstMatchingMeasurementTypes, [Validators.required]],
    })
  }

  onSubmit(): void {

    const formData: DeviceMeasurementRequest = {
      ...this.deviceMeasurementForm.value,
      deviceMeasurementId: this.request.deviceMeasurementId,
    };
    if (this.isEditMode) {
      this.deviceMeasurementApiService.updateDeviceMeasurement(formData).subscribe(
        response => {
          this.dialogRef.close();
        },
        error => {
          this.toastService.showError(error.message)
        }
      );
    } else {
      this.deviceMeasurementApiService.createDeviceMeasurement(formData).subscribe(
        response => {
          this.dialogRef.close();
        },
        error => {
          this.toastService.showError(error.message)
        }
      );
    }
  }
  getFirstMatchingMeasurementTypes(): MeasurementType | null {
    const match = this.measurementTypes.find(type => type.measurementTypeId === this.request.measurementTypeId);
    return match ? match : null;
  }
  getFirstMatchingDevice(): Device | null {
    const match = this.devices.find(device => device.deviceId === this.request.deviceId);
    return match ? match : null;
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}
