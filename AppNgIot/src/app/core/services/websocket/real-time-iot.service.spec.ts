import { TestBed } from '@angular/core/testing';

import { RealTimeIotService } from './real-time-iot.service';

describe('RealTimeIotService', () => {
  let service: RealTimeIotService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RealTimeIotService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
