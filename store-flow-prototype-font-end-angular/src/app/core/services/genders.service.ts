import { Injectable, inject } from "@angular/core";
import { ApiService } from '../../core/services/api.service';

@Injectable({
  providedIn: "root",
})
export class GendersService {

  private apiService = inject(ApiService);

  list() {
    return this.apiService.get('/gender/list');
  }

}
