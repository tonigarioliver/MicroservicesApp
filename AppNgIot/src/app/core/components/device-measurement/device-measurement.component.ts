import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource, MatTable, MatColumnDef, MatHeaderCellDef, MatHeaderCell, MatCellDef, MatCell, MatHeaderRowDef, MatHeaderRow, MatRowDef, MatRow } from '@angular/material/table';
import { DeviceMeasurementFormComponent } from 'src/app/core/components/device-measurement-form/device-measurement-form.component';
import { DeviceMeasurement } from 'src/app/core/models/device-measurement';
import { DeviceMeasurementRequest } from 'src/app/core/models/device-measurement-request';
import { RealTimeWebSocketRequest } from 'src/app/core/models/real-time-iot-message';
import { DeviceMeasurementApiService } from 'src/app/core/services/api/device-measurement-api.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { RealTimeIotService } from 'src/app/core/services/websocket/real-time-iot.service';
import { MatIconButton, MatFabButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';

@Component({
    selector: 'app-device-measurement',
    templateUrl: './device-measurement.component.html',
    styleUrls: ['./device-measurement.component.css'],
    standalone: true,
    imports: [MatTable, MatColumnDef, MatHeaderCellDef, MatHeaderCell, MatCellDef, MatCell, MatIconButton, MatIcon, MatHeaderRowDef, MatHeaderRow, MatRowDef, MatRow, MatPaginator, MatFabButton]
})
export class DeviceMeasurementComponent implements OnInit {
  public interval: number = 1;
  displayedColumns: string[] = ['measurementName', 'measurementUnit', 'measurementTopic', 'measurementType', 'measurementDevice', 'actions'];
  tableData = new MatTableDataSource<DeviceMeasurement>();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private deviceMeasurementApiService: DeviceMeasurementApiService,
    private deviceDialog: MatDialog,
    private toastService: ToastService,
  ) {
  }

  ngOnInit(): void {
    this.tableData.paginator = this.paginator;
    this.tableData.sort = this.sort;
    this.fetchDeviceMeasurements();
  }

  addRealTimeMeasure(deviceMeasurement: DeviceMeasurement): void {
    const req: RealTimeWebSocketRequest = {
      topic: deviceMeasurement.topic
    }
  }

  fetchDeviceMeasurements(): void {
    this.deviceMeasurementApiService.getDeviceMeasurements().subscribe(
      (deviceMeasurements: DeviceMeasurement[]) => {
        this.tableData.data = deviceMeasurements;
      },
      (error) => {
        this.toastService.showError(error.message)
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
          this.toastService.showError(error.message)
        }
      );
  }
}


