import { TestBed } from "@angular/core/testing";

import { ConvertDataUtilsService } from "./convert-data-utils.service";

describe("ConvertDataUtilsService", () => {
  let service: ConvertDataUtilsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConvertDataUtilsService);
  });

  it("should be created", () => {
    expect(service).toBeTruthy();
  });
});
