import { TestBed } from '@angular/core/testing';

import { LoglineServiceService } from './logline-service.service';

describe('LoglineServiceService', () => {
  let service: LoglineServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoglineServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
