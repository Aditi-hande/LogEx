import { TestBed } from '@angular/core/testing';

import { ElasticSearchIndexServiceService } from './elastic-search-index-service.service';

describe('ElasticSearchIndexServiceService', () => {
  let service: ElasticSearchIndexServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ElasticSearchIndexServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
