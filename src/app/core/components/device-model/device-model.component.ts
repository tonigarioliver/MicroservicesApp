import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { DeviceModel } from 'src/app/core/models/device-model';
import { DeviceModelApiService } from 'src/app/core/services/device-model-api.service';
import { MatDialog } from '@angular/material/dialog';
import { DeviceModelFormComponent } from 'src/app/core/components/device-model-form/device-model-form.component';
import { DeviceModelRequest } from 'src/app/core/models/device-model-request';

@Component({
  selector: 'app-device-model',
  templateUrl: './device-model.component.html',
  styleUrls: ['./device-model.component.scss'],
})
export class DeviceModelComponent implements OnInit {
  displayedColumns: string[] = ['modelName', 'serialNumber', 'actions'];
  tableData = new MatTableDataSource<DeviceModel>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private deviceModelApiService: DeviceModelApiService, private deviceModelDialog: MatDialog) { }

  ngOnInit(): void {
    this.tableData.paginator = this.paginator;
    this.tableData.sort = this.sort;
    this.fetchDeviceModels();
  }

  fetchDeviceModels(): void {
    this.deviceModelApiService.getDeviceModels().subscribe(
      (deviceModels: DeviceModel[]) => {
        this.tableData.data = deviceModels;
      },
      (error) => {
        console.error('Error fetching device models:', error);
      }
    );
  }

  addDeviceModel(): void {
    const deviceModelRequest: DeviceModelRequest = {
      deviceModelId: null,
      name: '',
      serialNumber: ''
    };
    const isEditMode = false
    const dialogRef = this.deviceModelDialog.open(DeviceModelFormComponent, {
      width: '400px',
      data: { deviceModelRequest, isEditMode },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.fetchDeviceModels();
    });
  }

  editDeviceModel(deviceModel: DeviceModel): void {
    const deviceModelRequest: DeviceModelRequest = {
      deviceModelId: deviceModel.deviceModelId,
      name: deviceModel.name,
      serialNumber: deviceModel.serialNumber
    };
    const isEditMode = true
    const dialogRef = this.deviceModelDialog.open(DeviceModelFormComponent, {
      width: '400px',
      data: { deviceModelRequest, isEditMode },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.fetchDeviceModels();
    });
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
