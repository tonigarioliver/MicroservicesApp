import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceMeasurementComponent } from './device-measurement.component';

describe('DeviceMeasurementComponent', () => {
  let component: DeviceMeasurementComponent;
  let fixture: ComponentFixture<DeviceMeasurementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeviceMeasurementComponent]
    });
    fixture = TestBed.createComponent(DeviceMeasurementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
