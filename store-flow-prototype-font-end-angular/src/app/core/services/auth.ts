import { Injectable, inject, PLATFORM_ID } from '@angular/core'; // เพิ่ม PLATFORM_ID
import { isPlatformBrowser } from '@angular/common'; // เพิ่ม isPlatformBrowser
import { ApiService } from './api.service';
import { LoginRequest } from '../models/auth.model';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiService = inject(ApiService);
  private jwtHelper = inject(JwtHelperService);
  private platformId = inject(PLATFORM_ID); // inject ตัวเช็ค Platform เข้ามาใช้งาน

  constructor() { }

  // ฟังก์ชันเซ็ต Token เมื่อล็อกอินสำเร็จ
  saveToken(token: string): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.setItem('token', token);
    }
  }

  getToken(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem('token');
    }
    return null;
  }

  // 1. ดึงข้อมูลทั้งหมดที่อยู่ใน Token (Payload)
  getDecodedToken(): any {
    const token = this.getToken();
    return token ? this.jwtHelper.decodeToken(token) : null;
  }

  // 2. ดึง Role ออกมาจาก Token 
  getUserRole(): string | string[] | null {
    const decoded = this.getDecodedToken();
    console.log("decoded jwt : ", decoded);
    // คืนค่ากลับไป (Backend ส่วนใหญ่ส่งมาเป็น Array เช่น ['ADMIN'] หรือ String เช่น 'ADMIN')
    return decoded ? decoded.roles : null;
  }

  getCurrentUserLogin() {
    const decoded = this.getDecodedToken();
    console.log("decoded jwt : ", decoded);
    return decoded ? decoded.username : null;
  }

  // 3. ฟังก์ชันเช็คว่าผู้ใช้มี Role ตรงกับที่กำหนดไหม
  hasRole(expectedRoles: string[]): boolean {
    const userRole = this.getUserRole();
    if (!userRole) return false;

    // กรณีที่ Role จาก Token ส่งมาเป็น Array (เช่น ['USER', 'ADMIN'])
    if (Array.isArray(userRole)) {
      return userRole.some(role => expectedRoles.includes(role));
    }

    // กรณีที่ Role จาก Token ส่งมาเป็น String เดี่ยวๆ (เช่น 'ADMIN')
    return expectedRoles.includes(userRole);
  }

  // เช็คว่า Token หมดอายุหรือยัง (ปลอดภัยเพราะ getToken ด้านบนถูกครอบด้วย isPlatformBrowser แล้ว)
  isAuthenticated(): boolean {
    const token = this.getToken();
    return token ? !this.jwtHelper.isTokenExpired(token) : false;
  }

  login(req: LoginRequest): Observable<any> {
    return this.apiService.post('/auth/login', req);
  }

  // check status login (เปลี่ยนมาใช้มาตรฐานเดียวกับด้านบน)
  isLoggedIn(): boolean {
    if (isPlatformBrowser(this.platformId)) {
      const token = this.getToken();
      return !!token;
    }
    return false;
  }

  // logout user
  logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('token');
    }
  }
}
