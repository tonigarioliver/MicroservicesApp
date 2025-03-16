import { Component } from '@angular/core';
import { MatTabGroup, MatTab } from '@angular/material/tabs';
import { DeviceComponent } from '../../components/device/device.component';
import { DeviceModelComponent } from '../../components/device-model/device-model.component';
import { DeviceMeasurementComponent } from '../../components/device-measurement/device-measurement.component';

@Component({
    selector: 'app-maintenance',
    templateUrl: './maintenance.component.html',
    styleUrls: ['./maintenance.component.css'],
    standalone: true,
    imports: [MatTabGroup, MatTab, DeviceComponent, DeviceModelComponent, DeviceMeasurementComponent]
})
export class MaintenanceComponent {

}
