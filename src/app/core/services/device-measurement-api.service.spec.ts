import { TestBed } from '@angular/core/testing';

import { DeviceMeasurementApiService } from './device-measurement-api.service';

describe('DeviceMeasurementApiService', () => {
  let service: DeviceMeasurementApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeviceMeasurementApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
