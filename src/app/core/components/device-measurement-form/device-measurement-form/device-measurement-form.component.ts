import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DeviceApiService } from 'src/app/core/services/device-api.service';
import { Device } from 'src/app/core/models/device';
import { MeasurementType } from 'src/app/core/models/measurement-type';
import { MeasurementTypeApiService } from 'src/app/core/services/measurement-type-api.service';
import { DeviceMeasurement } from 'src/app/core/models/device-measurement';
import { DeviceMeasurementRequest } from 'src/app/core/models/device-measurement-request';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DeviceMeasurementApiService } from 'src/app/core/services/device-measurement-api.service';

@Component({
  selector: 'app-device-measurement-form',
  templateUrl: './device-measurement-form.component.html',
  styleUrls: ['./device-measurement-form.component.css']
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
    private measurementTypeApiService: MeasurementTypeApiService
  ) {
    this.isEditMode = dialogData.isEditMode
    this.request = dialogData.request;
    this.deviceMeasurementForm = this.fb.group({
      deviceMeasurementId: [null],
      deviceId: [null, Validators.required],
      name: ['', Validators.required],
      unit: ['', Validators.required],
      measurementTypeId: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    this.deviceApiService.getDevices().subscribe(devices => this.devices = devices);
    this.measurementTypeApiService.getMeasurementTypes().subscribe(types => this.measurementTypes = types);
    this.deviceMeasurementForm = this.fb.group({
      device: [this.getFirstMatchingDevice, [Validators.required]],
      name: [this.request.name, [Validators.required]],
      unit: [this.request.unit, [Validators.required]],
      measurementType: [this.getFirstMatchingMeasurementTypes, [Validators.required]],
    })
  }

  onSubmit(): void {

    const formData: DeviceMeasurementRequest = {
      ...this.deviceMeasurementForm.value,
      deviceMeasurementId: this.request.deviceMeasurementId
    };
    /*
    const request: DeviceMeasurementRequest = {
      deviceMeasurementId: this.request.deviceMeasurementId,
      deviceId: this.deviceMeasurementForm.value.device.deviceId,
      name: this.deviceMeasurementForm.value.
      manufactureCode: this.deviceForm.value.manufactureCode,
      price: this.deviceForm.value.price,
      manufactureDate: this.deviceForm.value.manufactureDate.toISOString()
    };*/
    if (this.isEditMode) {
      this.deviceMeasurementApiService.updateDeviceMeasurement(formData).subscribe(
        response => {
          console.log('Respuesta exitosa:', response);
          this.dialogRef.close();
        },
        error => {
          console.error('Error al actualizar el dispositivo:', error);
        }
      );
    } else {
      this.deviceMeasurementApiService.createDeviceMeasurement(formData).subscribe(
        response => {
          console.log('Respuesta exitosa:', response);
          this.dialogRef.close();
        },
        error => {
          console.error('Error al crear el dispositivo:', error);
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
