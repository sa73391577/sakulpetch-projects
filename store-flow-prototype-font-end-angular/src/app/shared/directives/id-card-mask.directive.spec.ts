import { IdCardMaskDirective } from './id-card-mask.directive';
import { ElementRef } from '@angular/core';
import { NgControl } from '@angular/forms';

describe('IdCardMaskDirective', () => {
  it('should create an instance', () => {
    const mockEleRef = {} as ElementRef;
    const mockNgControl = {} as NgControl;
    const directive = new IdCardMaskDirective(mockEleRef, mockNgControl);
    expect(directive).toBeTruthy();
  });
});
