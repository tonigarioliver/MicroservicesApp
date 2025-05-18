import { Component, OnInit, ViewChild } from '@angular/core';
import { SidenavDashboardComponent } from 'src/app/core/components/sidenav-dashboard/sidenav-dashboard.component';
import { Device } from 'src/app/core/models/device';
import { DeviceMeasurement } from 'src/app/core/models/device-measurement';
import { DeviceMeasurementDetails } from 'src/app/core/models/device-measurement-details';
import { DeviceApiService } from 'src/app/core/services/api/device-api.service';
import { DeviceMeasurementApiService } from 'src/app/core/services/api/device-measurement-api.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { MatToolbar } from '@angular/material/toolbar';
import { MatIconButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { SidenavDashboardComponent as SidenavDashboardComponent_1 } from '../../components/sidenav-dashboard/sidenav-dashboard.component';

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css'],
    standalone: true,
    imports: [MatToolbar, MatIconButton, MatIcon, SidenavDashboardComponent_1]
})
export class DashboardComponent implements OnInit {
  devices: Device[] = [];
  deviceMeasurements: DeviceMeasurement[] = [];
  deviceMeasurementDetails: DeviceMeasurementDetails | undefined;

  @ViewChild(SidenavDashboardComponent) sidenavDashboard!: SidenavDashboardComponent;

  constructor(
    private deviceApiService: DeviceApiService,
    private deviceMeasurementApiService: DeviceMeasurementApiService,
    private toastService: ToastService
  ) { }

  ngOnInit(): void {
    this.deviceApiService.getDevices().subscribe(
      (devices: Device[]) => {
        this.devices = devices;
      },
      (error) => {
        this.toastService.showError(error.message);
      }
    );

    this.deviceMeasurementApiService.getDeviceMeasurements().subscribe(
      (measurements: DeviceMeasurement[]) => {
        this.deviceMeasurements = measurements;
        if (measurements.length > 0) {
          this.deviceMeasurementApiService.getDeviceMeasurementDetails(measurements[0].deviceMeasurementId)
            .subscribe(
              (details: DeviceMeasurementDetails) => {
                this.deviceMeasurementDetails = details;
              },
              (error) => {
                this.toastService.showError(error.message);
              }
            );
        }
      },
      (error) => {
        this.toastService.showError(error.message);
      }
    );
  }

  toggleMenu(): void {
    if (this.sidenavDashboard) {
      this.sidenavDashboard.toggleMenu();
    }
  }
}
