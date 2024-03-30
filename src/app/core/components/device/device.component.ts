import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { DeviceFormComponent } from 'src/app/core/components/device-form/device-form.component';
import { Device } from 'src/app/core/models/device';
import { DeviceRequest } from 'src/app/core/models/device-request';
import { DeviceApiService } from 'src/app/core/services/api/device-api.service';
import { ToastService } from 'src/app/core/services/toast.service';

@Component({
  selector: 'app-device',
  templateUrl: './device.component.html',
  styleUrls: ['./device.component.css']
})
export class DeviceComponent implements OnInit {

  displayedColumns: string[] = ['deviceModel', 'manufactureDate', 'price', 'manufactureCode', 'actions'];
  tableData = new MatTableDataSource<Device>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private deviceApiService: DeviceApiService,
    private deviceDialog: MatDialog,
    private toastService: ToastService
  ) { }

  ngOnInit(): void {
    this.tableData.paginator = this.paginator;
    this.tableData.sort = this.sort;
    this.fetchDevices();
  }

  fetchDevices(): void {
    this.deviceApiService.getDevices().subscribe(
      (devices: Device[]) => {
        this.tableData.data = devices;
      },
      (error) => {
        this.toastService.showError(error.message)
      }
    );
  }

  addDevice(): void {
    const deviceRequest: DeviceRequest = {
      deviceId: null,
      deviceModelId: 0,
      manufactureCode: '',
      price: null,
      manufactureDate: (new Date())
    };
    const isEditMode = false
    const dialogRef = this.deviceDialog.open(DeviceFormComponent, {
      width: '400px',
      data: { deviceRequest, isEditMode },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.fetchDevices();
    });
  }

  editDevice(device: Device): void {
    const deviceRequest: DeviceRequest = {
      deviceId: device.deviceId,
      deviceModelId: device.deviceModel.deviceModelId,
      manufactureCode: device.manufactureCode,
      price: device.price,
      manufactureDate: device.manufactureDate
    };
    const isEditMode = true
    const dialogRef = this.deviceDialog.open(DeviceFormComponent, {
      width: '400px',
      data: { deviceRequest, isEditMode },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.fetchDevices();
    });
  }

  deleteDevice(device: Device): void {
    this.deviceApiService.deleteDevice(device.deviceId)
      .subscribe(
        () => {
          this.fetchDevices();
        },
        (error) => {
          this.toastService.showError(error.message)
        }
      );
  }
}
