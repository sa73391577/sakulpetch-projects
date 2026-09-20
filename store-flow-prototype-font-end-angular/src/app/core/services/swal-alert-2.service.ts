import { Injectable, inject } from "@angular/core";
import Swal from 'sweetalert2';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: "root",
})
export class SwalAlert2Service {

  private translateService = inject(TranslateService);

  success(title: string = '', text: string = '') {
    return Swal.fire({
      icon: 'success',
      title: title,
      text: text,
      confirmButtonColor: '#1ce351',
      confirmButtonText: this.translateService.instant('button.confirm'),
      position: 'center'
    });
  }

  error(title: string = '', text: string = '') {
    return Swal.fire({
      icon: 'error',
      title: title,
      text: text,
      confirmButtonColor: '#d33',
      confirmButtonText: this.translateService.instant('button.close'),
      position: 'center'
    });
  }

  info(title: string = '', text: string = '') {
    return Swal.fire({
      icon: 'info',
      title: title,
      text: text,
      confirmButtonText: this.translateService.instant('button.close'),
      position: 'center'
    });
  }



}
