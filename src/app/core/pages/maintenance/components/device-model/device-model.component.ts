import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { DeviceModel } from 'src/app/core/models/device-model';
import { DeviceModelApiService } from 'src/app/core/services/device-model-api.service';
import { MatDialog } from '@angular/material/dialog';
import { CreateDeviceModelComponent } from 'src/app/core/pages/maintenance/components/device-model/components/create-device-model/create-device-model.component';
import { DeviceModelCreateRequest } from 'src/app/core/models/device-model-create-request';

@Component({
  selector: 'app-device-model',
  templateUrl: './device-model.component.html',
  styleUrls: ['./device-model.component.css'],
})
export class DeviceModelComponent implements OnInit {
  displayedColumns: string[] = ['name', 'code','actions'];
  dataSource = new MatTableDataSource<DeviceModel>(/* tu array de datos aquí */);

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort; // Añade el signo de exclamación aquí

  deviceModelCreateRequest:DeviceModelCreateRequest={
    name:'',
    serialNumber:''
  };

  constructor(private deviceModelApiService: DeviceModelApiService,private createDeviceModelDialog:MatDialog) {}

  ngOnInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.fetchDeviceModels();
  }

  fetchDeviceModels():void{
    this.deviceModelApiService.getDeviceModels().subscribe(
      (deviceModels: DeviceModel[]) => {
        this.dataSource.data = deviceModels;
      },
      (error) => {
        console.error('Error fetching device models:', error);
      }
    );
  }
  addDeviceModelDialog(): void {
    const dialogRef = this.createDeviceModelDialog.open(CreateDeviceModelComponent, {
      width: '400px',
      data: this.deviceModelCreateRequest
    });
  
    dialogRef.afterClosed().subscribe(result => {
      this.fetchDeviceModels();
    });
  }
  editDeviceModel(deviceModel:DeviceModel):void{
    console.debug(deviceModel);
  }
  deleteDeviceModel(deviceModel: DeviceModel): void {
    this.deviceModelApiService.deleteDeviceModel(deviceModel.deviceModelId)
      .subscribe(
        () => {
          this.fetchDeviceModels(); 
        },
        (error) => {
          console.error('Error deleting device model:', error);
        }
      );
  }
}
