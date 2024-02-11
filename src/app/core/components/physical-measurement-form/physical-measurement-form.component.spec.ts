import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PhysicalMeasurementFormComponent } from './physical-measurement-form.component';

describe('PhysicalMeasurementFormComponent', () => {
  let component: PhysicalMeasurementFormComponent;
  let fixture: ComponentFixture<PhysicalMeasurementFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PhysicalMeasurementFormComponent]
    });
    fixture = TestBed.createComponent(PhysicalMeasurementFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
