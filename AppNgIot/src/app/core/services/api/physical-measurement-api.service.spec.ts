import { TestBed } from '@angular/core/testing';

import { PhysicalMeasurementApiService } from './physical-measurement-api.service';

describe('PhysicalMeasurementApiService', () => {
  let service: PhysicalMeasurementApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PhysicalMeasurementApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
