import { TestBed } from '@angular/core/testing';

import { AddNewPropertyService } from './add-new-property.service';

describe('AddNewPropertyServiceService', () => {
  let service: AddNewPropertyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddNewPropertyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
