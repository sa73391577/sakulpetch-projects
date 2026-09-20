import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, map, of } from 'rxjs';
import { ApiResponse } from '../models/api-response';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ApiService {

  // กำหนด URL ของ API ภายนอกที่ต้องการเรียกใช้
  //private apiUrl = 'http://localhost:8080/api';
  //private apiUrl = '/api';
  private apiUrl = environment.apiUrl;

  private http = inject(HttpClient);

  // ปรับ POST กลาง ให้ทำการ Cast Type ของ res ก่อนเข้าถึง .data
  post<T = any>(path: string, data: any): Observable<T> {
    // ส่งข้อข้อมูลกลับไปทั้งก้อนตรงๆ และแปลงไทป์ดิบให้กลายเป็น T ทันที
    return this.http.post<any>(this.apiUrl + path, data).pipe(
      map((res: any) => res as T),
      catchError((error: any) => {
        if (error.status === 401 || error.status === 403) {
          console.log("===>>> error from call api error 401 or 403.");
        }
        return of(null as any);
      })
    );
  }

  get<T = any>(path: string): Observable<T> {
    // ส่งข้อมูลทั้งก้อนกลับไปตรงๆ และแปลงไทป์ให้กลายเป็น T ตามที่เรียกใช้งาน
    return this.http.get<any>(this.apiUrl + path).pipe(
      map((res: any) => res as T),
      catchError((error: any) => {
        if (error.status === 401 || error.status === 403) {
          console.log("===>>> error from call api error 401 or 403.");
        }
        return of(null as any);
      })
    );
  }

}
