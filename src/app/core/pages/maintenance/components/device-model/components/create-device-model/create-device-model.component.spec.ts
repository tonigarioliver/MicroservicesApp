import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateDeviceModelComponent } from './create-device-model.component';

describe('CreateDeviceModelComponent', () => {
  let component: CreateDeviceModelComponent;
  let fixture: ComponentFixture<CreateDeviceModelComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateDeviceModelComponent]
    });
    fixture = TestBed.createComponent(CreateDeviceModelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
