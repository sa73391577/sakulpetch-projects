import { Directive, HostListener, HostBinding } from '@angular/core';

@Directive({
  standalone: true,
  selector: '[appOnlyAlphanumeric]',
})
export class OnlyAlphanumericDirective {

  //check word in input.
  private isError: boolean = false;

  @HostListener('input', ['$event'])
  onInputChange(event: Event) {
    const input = event.target as HTMLInputElement;
    input.value = input.value.replace(/[^a-zA-Z0-9]/g, '');
  }
  //Add style on app.css
  @HostBinding('class.border-red')
  get hasError(): boolean {
    return this.isError;
  }

  @HostListener('blur')
  onBlur(): void {
    this.isError = false;
  }
}
