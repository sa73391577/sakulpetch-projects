import { Injectable, inject } from '@angular/core';
import { ApiService } from './api.service';
import { UserInfo } from '../models/user.model';
import { SignUpForm } from '../models/signup.model';
import { ApiResponse } from '../models/api-response';
import { UserService } from '../interfaces/user-service.interface';

@Injectable({
  providedIn: 'root',
})
export class UsersService implements UserService {

  private apiService = inject(ApiService);
  private rootUrlPath = "/user";

  add(dataForm: SignUpForm) {
    return this.apiService.post(this.rootUrlPath + '/new', dataForm);
  }

  update(dataForm: SignUpForm) {
    return this.apiService.post(this.rootUrlPath + '/update', dataForm);
  }

  list() {
    return this.apiService.get<ApiResponse<UserInfo[]>>('/user/list');
  }

  getByUsername(username: string | null) {
    return this.apiService.post(this.rootUrlPath + '/user-profile/username', { username: username });
  }

  deleteByUsername(username: string | null) {
    return this.apiService.post(this.rootUrlPath + '/user-profile/username', { username: username });
  }

}
