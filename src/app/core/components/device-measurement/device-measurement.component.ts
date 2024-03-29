import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { DeviceFormComponent } from 'src/app/core/components/device-form/device-form.component';
import { DeviceMeasurementFormComponent } from 'src/app/core/components/device-measurement-form/device-measurement-form.component';
import { Device } from 'src/app/core/models/device';
import { DeviceMeasurement } from 'src/app/core/models/device-measurement';
import { DeviceMeasurementRequest } from 'src/app/core/models/device-measurement-request';
import { DeviceRequest } from 'src/app/core/models/device-request';
import { DeviceMeasurementApiService } from 'src/app/core/services/device-measurement-api.service';

@Component({
  selector: 'app-device-measurement',
  templateUrl: './device-measurement.component.html',
  styleUrls: ['./device-measurement.component.css']
})
export class DeviceMeasurementComponent implements OnInit {

  displayedColumns: string[] = ['measurementName', 'measurementUnit', 'measurementTopic', 'measurementType', 'measurementDevice'];
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
        console.debug(deviceMeasurements)
        this.tableData.data = deviceMeasurements;
      },
      (error) => {
        console.error('Error fetching device deviceMeasurement:', error);
      }
    );
  }

  addMeasurement(): void {
    const request: DeviceMeasurementRequest = {
      deviceMeasurementId: null,
      deviceId: null,
      name: '',
      unit: '',
      measurementTypeId: null
    };
    const isEditMode = false
    const dialogRef = this.deviceDialog.open(DeviceMeasurementFormComponent, {
      width: '400px',
      data: { request, isEditMode },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.fetchDeviceMeasurements();
    });
  }

  editMeasurement(measurement: DeviceMeasurement): void {
    const request: DeviceMeasurementRequest = {
      deviceMeasurementId: measurement.deviceMeasurementId,
      deviceId: measurement.device.deviceId,
      name: measurement.name,
      unit: measurement.unit,
      measurementTypeId: measurement.measurementType.measurementTypeId
    };
    const isEditMode = true
    const dialogRef = this.deviceDialog.open(DeviceMeasurementFormComponent, {
      width: '400px',
      data: { request, isEditMode },
    });

    dialogRef.afterClosed().subscribe(result => {
      this.fetchDeviceMeasurements();
    });
  }

  deleteMeasurement(measurement: DeviceMeasurement): void {
    this.deviceMeasurementApiService.deleteDeviceMeasurement(measurement.deviceMeasurementId)
      .subscribe(
        () => {
          this.fetchDeviceMeasurements();
        },
        (error) => {
          console.error('Error deleting measurement:', error);
        }
      );
  }
}

