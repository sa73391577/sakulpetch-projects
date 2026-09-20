import { Component, inject, OnInit, signal } from '@angular/core';
import { FontAwesomeModule, FaIconComponent } from '@fortawesome/angular-fontawesome';
import { faPenToSquare, faTrash } from '@fortawesome/free-solid-svg-icons';
import { UserInfo } from '../../core/models/user.model';
import { UsersService } from '../../core/services/users.service';
import { USER_SERVICE_TOKEN } from '../../core/interfaces/user-service.interface';


import { JsonParserService } from '../../core/utils/json-parser.service';
import { ThaiDatePipe } from '../../shared/pipes/thai-date.pipe';
import { AgePipe } from '../../shared/pipes/age.pipe';
import { UserFormModalComponent } from '../../shared/components/user-form-modal/user-form-modal.component';
import { TranslateService, TranslatePipe } from '@ngx-translate/core';


@Component({
  selector: 'app-user-management',
  imports: [FaIconComponent, FontAwesomeModule, ThaiDatePipe, AgePipe, UserFormModalComponent, TranslatePipe],
  standalone: true,
  templateUrl: './user-management.component.html',
  styleUrl: './user-management.component.css',
})
export class UserManagementComponent implements OnInit {

  userService = inject(USER_SERVICE_TOKEN);
  jsonParserService = inject(JsonParserService);

  //icon
  faPenToSquare = faPenToSquare;
  faTrash = faTrash;

  //user list
  //userList: UserInfo[] = [];
  userList = signal<UserInfo[]>([]);
  modalStatus = signal<boolean>(false);
  userIdSelected = signal<string>('');
  userNameSelected = signal<string>('');

  ngOnInit() {
    this.getUserList();
  }

  getUserList() {
    this.userService.list().subscribe({
      next: (res) => {
        if (res) {
          console.log("res : ", res);
          let dataList = res?.data;
          const userMapperList = (dataList || []).map((data: any) => {
            console.log("type of data?.roleNameTH : ", typeof data?.roleNameTH);
            return {
              id: data?.id || "",
              nameTH: data?.nameTH || "",
              surnameTH: data?.surnameTH || "",
              nameEN: data?.nameEN || "",
              surnameEN: data?.surnameEN || "",
              telephone: data?.telephone || "",
              mobilePhone: data?.mobilePhone || "",
              idCard: data?.idCard || "",
              email: data?.email || "",
              username: data?.username || "",
              brithDate: data?.brithDate || "",
              roleNameTH: this.jsonParserService.toStringArray(data?.roleNameTH),
              roleNameEN: this.jsonParserService.toStringArray(data?.roleNameEN)
            };
          });
          this.userList.set(userMapperList);
        }
      },
      error: (error) => {
        console.log("error : ", error);
      }
    });
  }

  openUserDetailModal(username: any) {
    console.log("openUserDetailModal working ");
    if (username) {
      console.log("username in openUserDetailModal : ", username);
      this.userNameSelected.set(username);
      console.log("this.userNameSelected() : ", this.userNameSelected());
    } else {
      this.userNameSelected.set("");
    }
    this.modalStatus.set(true);
  }

  handleCloseModal() {
    this.modalStatus.set(false);
    this.getUserList();
  }


}
