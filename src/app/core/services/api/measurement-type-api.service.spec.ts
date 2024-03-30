import { TestBed } from '@angular/core/testing';

import { MeasurementTypeApiService } from './measurement-type-api.service';

describe('MeasurementTypeApiService', () => {
  let service: MeasurementTypeApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MeasurementTypeApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
