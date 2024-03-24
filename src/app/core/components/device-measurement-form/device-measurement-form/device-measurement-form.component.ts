import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DeviceApiService } from 'src/app/core/services/device-api.service';
import { Device } from 'src/app/core/models/device';
import { MeasurementType } from 'src/app/core/models/measurement-type';
import { MeasurementTypeApiService } from 'src/app/core/services/measurement-type-api.service';
import { DeviceMeasurement } from 'src/app/core/models/device-measurement';

@Component({
  selector: 'app-device-measurement-form',
  templateUrl: './device-measurement-form.component.html',
  styleUrls: ['./device-measurement-form.component.css']
})
export class DeviceMeasurementFormComponent implements OnInit {
  deviceMeasurementForm: FormGroup;
  devices: Device[] = [];
  measurementTypes: MeasurementType[] = []

  constructor(
    private fb: FormBuilder,
    private deviceApiService: DeviceApiService,
    private measurementTypeApiService: MeasurementTypeApiService
  ) {
    this.deviceMeasurementForm = this.fb.group({
      deviceMeasurementId: [null],
      device: [null, Validators.required],
      name: ['', Validators.required],
      unit: ['', Validators.required],
      measurementType: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    this.deviceApiService.getDevices().subscribe(devices => this.devices = devices);
    this.measurementTypeApiService.getMeasurementTypes().subscribe(types => this.measurementTypes = types);
  }

  onSubmit() {

    const formData = this.deviceMeasurementForm.value as DeviceMeasurement;
    console.log(formData);
  }
}
