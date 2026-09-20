import { TestBed } from '@angular/core/testing';
import { provideTranslateService } from '@ngx-translate/core';
import { CustomTitleStrategy } from './custom-title.strategy';

describe('CustomTitleStrategy', () => {
  let service: CustomTitleStrategy;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [provideTranslateService()]
    }).compileComponents();
    TestBed.configureTestingModule({});
    service = TestBed.inject(CustomTitleStrategy);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
