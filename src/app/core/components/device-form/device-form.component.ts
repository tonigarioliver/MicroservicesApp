import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DeviceModel } from 'src/app/core/models/device-model';
import { DeviceRequest } from 'src/app/core/models/device-request';
import { DeviceApiService } from 'src/app/core/services/device-api.service';
import { DeviceModelApiService } from 'src/app/core/services/device-model-api.service';

@Component({
  selector: 'app-device-form',
  templateUrl: './device-form.component.html',
  styleUrls: ['./device-form.component.css']
})
export class DeviceFormComponent implements OnInit {

  deviceRequest: DeviceRequest = {
    deviceId: null,
    deviceModelId: 0,
    manufactureCode: '',
    price: null,
    manufactureDate: ''
  };
  deviceModels: DeviceModel[] = []

  deviceForm: FormGroup

  isEditMode: boolean

  constructor(
    private dialogRef: MatDialogRef<DeviceFormComponent>,
    @Inject(MAT_DIALOG_DATA) public dialogData: { deviceRequest: DeviceRequest; isEditMode: boolean },
    private deviceApiService: DeviceApiService,
    private formBuilder: FormBuilder,
    private deviceModelApiService: DeviceModelApiService
  ) {
    this.deviceRequest = dialogData.deviceRequest;
    this.isEditMode = dialogData.isEditMode
    this.fetchDeviceModels();
    this.deviceForm = this.formBuilder.group({
      deviceModel: [this.getFirstMatchingDeviceModel(), [Validators.required]],
      manufactureDate: [new Date(this.deviceRequest.manufactureDate), [Validators.required]],
      price: [this.deviceRequest.price, [Validators.required]],
      manufactureCode: [this.deviceRequest.manufactureCode, [Validators.required]],
    })
  }
  ngOnInit(): void {
    this.fetchDeviceModels();
    this.deviceForm = this.formBuilder.group({
      deviceModel: [this.getFirstMatchingDeviceModel(), [Validators.required]],
      manufactureDate: [new Date(this.deviceRequest.manufactureDate), [Validators.required]],
      price: [this.deviceRequest.price, [Validators.required]],
      manufactureCode: [this.deviceRequest.manufactureCode, [Validators.required]],
    })
  }
  getFirstMatchingDeviceModel(): DeviceModel | null {
    const matchingModel = this.deviceModels.find(deviceModel => deviceModel.deviceModelId === this.deviceRequest.deviceModelId);
    return matchingModel ? matchingModel : null;
  }
  fetchDeviceModels(): void {
    this.deviceModelApiService.getDeviceModels().subscribe(
      (deviceModels: DeviceModel[]) => {
        this.deviceModels = deviceModels;
      },
      (error) => {
        console.error('Error fetching device models:', error);
      }
    );
  }


  saveChangesDialog(): void {
    const request: DeviceRequest = {
      deviceId: this.deviceRequest.deviceId,
      deviceModelId: this.deviceForm.value.deviceModel,
      manufactureCode: this.deviceForm.value.manufactureCode,
      price: this.deviceForm.value.price,
      manufactureDate: this.deviceForm.value.manufactureDate.toISOString()
    };
    console.debug(request)
    if (this.isEditMode) {
      this.deviceApiService.updateDeviceRequest(request).subscribe(
        response => {
          console.log('Respuesta exitosa:', response);
          this.dialogRef.close();
        },
        error => {
          console.error('Error al actualizar el dispositivo:', error);
        }
      );
    } else {
      this.deviceApiService.createDeviceRequest(request).subscribe(
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


  onNoClick(): void {
    this.dialogRef.close();
  }
}
