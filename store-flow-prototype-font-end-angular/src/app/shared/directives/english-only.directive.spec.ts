import { EnglishOnlyDirective } from './english-only.directive';
import { ElementRef } from '@angular/core';
import { NgControl } from '@angular/forms';

describe('EnglishOnlyDirective', () => {
  it('should create an instance', () => {
    const mockEleRef = {} as ElementRef;
    const mockNgControl = {} as NgControl;
    const directive = new EnglishOnlyDirective(mockEleRef, mockNgControl);
    expect(directive).toBeTruthy();
  });
});
