import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DeviceModel } from 'src/app/core/models/device-model';
import { DeviceRequest } from 'src/app/core/models/device-request';
import { DeviceApiService } from 'src/app/core/services/api/device-api.service';
import { DeviceModelApiService } from 'src/app/core/services/api/device-model-api.service';
import { ToastService } from 'src/app/core/services/toast.service';

@Component({
  selector: 'app-device-form',
  templateUrl: './device-form.component.html',
  styleUrls: ['./device-form.component.css']
})
export class DeviceFormComponent implements OnInit {


  deviceForm: FormGroup

  isEditMode: boolean

  deviceRequest: DeviceRequest = {
    deviceId: null,
    deviceModelId: 0,
    manufactureCode: '',
    price: null,
    manufactureDate: new Date
  };
  deviceModels: DeviceModel[] = []

  constructor(
    private dialogRef: MatDialogRef<DeviceFormComponent>,
    @Inject(MAT_DIALOG_DATA) public dialogData: { deviceRequest: DeviceRequest; isEditMode: boolean },
    private deviceApiService: DeviceApiService,
    private formBuilder: FormBuilder,
    private deviceModelApiService: DeviceModelApiService,
    private toastService: ToastService,
  ) {
    this.deviceRequest = dialogData.deviceRequest;
    this.isEditMode = dialogData.isEditMode
    this.deviceForm = this.formBuilder.group({
      deviceModel: ['', [Validators.required]],
      manufactureDate: [null, [Validators.required]],
      price: ['', [Validators.required]],
      manufactureCode: ['', [Validators.required]],
    })
  }
  ngOnInit(): void {
    this.fetchDeviceModels();
    this.deviceForm = this.formBuilder.group({
      deviceModel: [this.getFirstMatchingDeviceModel(), [Validators.required]],
      manufactureDate: [null, [Validators.required]],
      price: [this.deviceRequest.price, [Validators.required]],
      manufactureCode: [this.deviceRequest.manufactureCode, [Validators.required]],
    })
  }
  addEvent(event: MatDatepickerInputEvent<Date>): void {
    this.deviceForm.patchValue({ manufactureDate: event.value });
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
        this.toastService.showError(error.message)
      }
    );
  }


  saveChangesDialog(): void {
    const request: DeviceRequest = {
      deviceId: this.deviceRequest.deviceId,
      deviceModelId: this.deviceForm.value.deviceModel.deviceModelId,
      manufactureCode: this.deviceForm.value.manufactureCode,
      price: this.deviceForm.value.price,
      manufactureDate: this.deviceForm.value.manufactureDate.toISOString()
    };
    console.debug(request)
    if (this.isEditMode) {
      this.deviceApiService.updateDeviceRequest(request).subscribe(
        response => {
          this.dialogRef.close();
        },
        error => {
          this.toastService.showError(error.message)
        }
      );
    } else {
      this.deviceApiService.createDeviceRequest(request).subscribe(
        response => {
          this.dialogRef.close();
        },
        error => {
          this.toastService.showError(error.message)
        }
      );
    }
  }


  onNoClick(): void {
    this.dialogRef.close();
  }
}
