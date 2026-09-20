import { ThaiOnlyDirective } from './thai-only.directive';
import { ElementRef } from '@angular/core';
import { NgControl } from '@angular/forms';

describe('ThaiOnlyDirective', () => {
  it('should create an instance', () => {
    const mockEleRef = {} as ElementRef;
    const mockNgControl = {} as NgControl;
    const directive = new ThaiOnlyDirective(mockEleRef, mockNgControl);
    expect(directive).toBeTruthy();
  });
});
