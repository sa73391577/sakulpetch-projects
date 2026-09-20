import { Component, inject } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth';
import { CommonModule } from '@angular/common';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';
import { OnlyAlphanumericDirective } from '../../shared/directives/only-alphanumeric.directive';
import { SwitchLanguageComponent } from '../../shared/components/switch-language/switch-language.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, TranslatePipe, OnlyAlphanumericDirective, SwitchLanguageComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class Login {


  private authService = inject(AuthService);
  private router = inject(Router);
  private translate = inject(TranslateService);

  loginForm !: FormGroup;
  isShowPassword: boolean = false;

  errorMessage = '';

  ngOnInit() {
    this.loginForm = new FormGroup({
      username: new FormControl('', [
        Validators.required,
        Validators.minLength(4)
      ]),
      password: new FormControl('', [
        Validators.required
      ])
    });
  }

  redirectToSignupPage() {
    console.log("Sign Page");
    this.router.navigateByUrl('/signup/form');
  }

  onSubmit() {
    console.log("Login Process !!!");
    // เช็คว่าฟอร์มกรอกข้อมูลถูกต้องตามเงื่อนไข Validators
    if (this.loginForm.invalid) {
      console.log("Login invalid !!!");
      this.loginForm.markAllAsTouched(); // แสดงเออร์เรอร์สีแดงทุกช่องที่ยังกรอกไม่ครบ
      return;
    }
    console.log("Login Submit !!!");
    // ดึงค่าจากฟอร์มออกมาใช้งาน
    const { username, password } = this.loginForm.value;

    this.authService.login(this.loginForm.value).subscribe({
      next: (res) => {
        console.log("res : ", res);
        let data = res?.data;
        if (null != data && 'undefined' !== data) {
          console.log("res : ", data?.token);
          localStorage.setItem('token', data?.token);
          this.router.navigate(['/dashboard']);
        }
      },
      error: (err) => {
        console.log("error : ", err);
        // เช็ก Error Code จากเซิร์ฟเวอร์
        if (err.status === 404) {
          console.error('ไม่พบข้อมูลที่ต้องการ (404 Not Found)');
        } else if (err.status === 500) {
          console.error('เซิร์ฟเวอร์ปลายทางมีปัญหา (500 Internal Server Error)');
        } else if (err.status === 0) {
          console.error('ไม่สามารถเชื่อมต่ออินเทอร์เน็ตได้ หรือติดปัญหา CORS');
        }
        else if (err.status === 401) {
          console.error('username หรือ password ผิดพลาด');
        }
        else {
          console.error('เกิดข้อผิดพลาดอื่นๆ:', err.message);
        }
      },
      complete: () => {
        console.log("call for login success.");
      }
    }
    );
  }

  togglePasswordVisibility(): void {
    this.isShowPassword = !this.isShowPassword;
  }

}
