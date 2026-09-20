import { inject, Injectable } from '@angular/core';
import { ApiService } from '../../core/services/api.service';

@Injectable({
  providedIn: 'root',
})
export class RolesService {
  private apiService = inject(ApiService);


  list() {
    return this.apiService.get("/role/list");
  }


  getByCode(code: string) {
    return this.apiService.post("/role/get/code", { "roleCode": code });
  }



}
