import { TestBed } from '@angular/core/testing';

import { DeviceModelApiService } from './device-model-api.service';

describe('DeviceModelApiService', () => {
  let service: DeviceModelApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DeviceModelApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
