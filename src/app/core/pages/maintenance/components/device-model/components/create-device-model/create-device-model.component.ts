import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { DeviceModelCreateRequest } from 'src/app/core/models/device-model-create-request';
import { DeviceModelApiService } from 'src/app/core/services/device-model-api.service';


@Component({
  selector: 'app-create-device-model',
  templateUrl: './create-device-model.component.html',
  styleUrls: ['./create-device-model.component.css']
})
export class CreateDeviceModelComponent {
  createDeviceModelRequest:DeviceModelCreateRequest={
    name:'',
    serialNumber:''
  };
  constructor(private dialogRef: MatDialogRef<CreateDeviceModelComponent>,
      @Inject(MAT_DIALOG_DATA) public deviceModelRequest:DeviceModelCreateRequest,
      private deviceModelApiService:DeviceModelApiService) { 
        this.createDeviceModelRequest=deviceModelRequest;
      }
  
  postCreateDeviceModelRequest():void{
    this.deviceModelApiService.postCreateDeviceModelRequest(this.createDeviceModelRequest).subscribe(
      response => {
        console.log('Respuesta exitosa:', response);
        this.dialogRef.close()
      },
      error => {
        console.error('Error al crear el modelo de dispositivo:', error);
      }
    );
  }
  onNoClick(): void {
    console.debug(this.createDeviceModelRequest)
    this.dialogRef.close();
  }
}
