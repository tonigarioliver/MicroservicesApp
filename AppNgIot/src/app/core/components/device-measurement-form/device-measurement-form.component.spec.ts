import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceMeasurementFormComponent } from './device-measurement-form.component';

describe('DeviceMeasurementFormComponent', () => {
  let component: DeviceMeasurementFormComponent;
  let fixture: ComponentFixture<DeviceMeasurementFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
    imports: [DeviceMeasurementFormComponent]
});
    fixture = TestBed.createComponent(DeviceMeasurementFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
