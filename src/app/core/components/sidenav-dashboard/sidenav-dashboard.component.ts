import { Component, ViewChild, OnInit } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { DeviceMeasurementComponent } from 'src/app/core/components/device-measurement/device-measurement.component';
import { DeviceModelComponent } from 'src/app/core/components/device-model/device-model.component';
import { DeviceComponent } from 'src/app/core/components/device/device.component';
import { DashboardComponent } from 'src/app/core/pages/dashboard/dashboard.component';
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
  isMenuOpen = false;

  ngOnInit() {
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

}
