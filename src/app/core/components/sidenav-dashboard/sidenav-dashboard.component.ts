import { Component } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { DeviceMeasurementComponent } from 'src/app/core/components/device-measurement/device-measurement.component';
import { DeviceModelComponent } from 'src/app/core/components/device-model/device-model.component';
import { DeviceComponent } from 'src/app/core/components/device/device.component';
import { DeviceMeasurement } from 'src/app/core/models/device-measurement';
import { RealTimeWebSocketRequest } from 'src/app/core/models/real-time-iot-message';
import { DeviceMeasurementApiService } from 'src/app/core/services/api/device-measurement-api.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { RealTimeIotService } from 'src/app/core/services/websocket/real-time-iot.service';
interface ComponentToShow {
  name: string;
  component: any; // Type of the component
  visible: boolean;
}

@Component({
  selector: 'app-sidenav-dashboard',
  templateUrl: './sidenav-dashboard.component.html',
  styleUrls: ['./sidenav-dashboard.component.scss']
})
export class SidenavDashboardComponent {
  componentsToShow: ComponentToShow[] = [
    { name: 'device-measurement', component: DeviceMeasurementComponent, visible: false },
    { name: 'devices', component: DeviceComponent, visible: false },
    { name: 'device-models', component: DeviceModelComponent, visible: false },
  ];
  response: String = ''
  isMenuOpen = false;
  deviceMeasurements: DeviceMeasurement[] = []

  constructor(
    private deviceMeasurementApiService: DeviceMeasurementApiService,
    private toastService: ToastService,
    private realTimeIotService: RealTimeIotService,
  ) {

  }

  onDeviceMeasurementSelect(event: MatCheckboxChange, measurement: DeviceMeasurement) {
    const req: RealTimeWebSocketRequest = {
      topic: measurement.topic
    }
    if (event.checked) {
      this.realTimeIotService.sendSubscribeIOTRequest(req)
    } else {
      this.realTimeIotService.sendUnsusbscribeIOTRequest(req)
    }
  }

  ngOnInit() {
    this.realTimeIotService.connect();
    this.realTimeIotService.connected.subscribe((status: boolean) => {
      this.realTimeIotService.messageReceived.subscribe((message: string) => {
        this.response = message
      })
      this.fetchDeviceMeasurements();
    })
  }
  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
    if (!this.isMenuOpen) {
      this.componentsToShow.forEach(componentToShow => componentToShow.visible = false)
    }
  }
  loadComponent(componentName: string): void {
    this.componentsToShow.forEach(component => {
      if (component.name === componentName) {
        component.visible = !component.visible;
      } else {
        component.visible = false;
      }
    });
  }

  private fetchDeviceMeasurements(): void {
    this.deviceMeasurementApiService.getDeviceMeasurements().subscribe(
      (deviceMeasurements: DeviceMeasurement[]) => {
        this.deviceMeasurements = deviceMeasurements;
      },
      (error) => {
        this.toastService.showError(error.message)
      }
    );
  }


}
