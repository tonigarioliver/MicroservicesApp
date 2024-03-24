import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { DeviceFormComponent } from 'src/app/core/components/device-form/device-form.component';
import { Device } from 'src/app/core/models/device';
import { DeviceMeasurement } from 'src/app/core/models/device-measurement';
import { DeviceRequest } from 'src/app/core/models/device-request';
import { DeviceMeasurementApiService } from 'src/app/core/services/device-measurement-api.service';

@Component({
  selector: 'app-device-measurement',
  templateUrl: './device-measurement.component.html',
  styleUrls: ['./device-measurement.component.css']
})
export class DeviceMeasurementComponent implements OnInit {

  displayedColumns: string[] = ['deviceModel', 'manufactureDate', 'price', 'manufactureCode', 'actions'];
  tableData = new MatTableDataSource<DeviceMeasurement>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private deviceMeasurementApiService: DeviceMeasurementApiService, private deviceDialog: MatDialog) { }

  ngOnInit(): void {
    this.tableData.paginator = this.paginator;
    this.tableData.sort = this.sort;
    this.fetchDeviceMeasurements();
  }

  fetchDeviceMeasurements(): void {
    this.deviceMeasurementApiService.getDeviceMeasurements().subscribe(
      (deviceMeasurements: DeviceMeasurement[]) => {
        this.tableData.data = deviceMeasurements;
      },
      (error) => {
        console.error('Error fetching device models:', error);
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
      this.fetchDeviceMeasurements();
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
      this.fetchDeviceMeasurements();
    });
  }

  deleteDevice(device: Device): void {
    this.deviceMeasurementApiService.deleteDeviceMeasurement(device.deviceId)
      .subscribe(
        () => {
          this.fetchDeviceMeasurements();
        },
        (error) => {
          console.error('Error deleting device model:', error);
        }
      );
  }
}

