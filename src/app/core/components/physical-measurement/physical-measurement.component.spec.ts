import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PhysicalMeasurementComponent } from './physical-measurement.component';

describe('PhysicalMeasurementComponent', () => {
  let component: PhysicalMeasurementComponent;
  let fixture: ComponentFixture<PhysicalMeasurementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PhysicalMeasurementComponent]
    });
    fixture = TestBed.createComponent(PhysicalMeasurementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
