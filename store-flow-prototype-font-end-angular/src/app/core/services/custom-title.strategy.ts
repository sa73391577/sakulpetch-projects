import { Injectable, inject } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { RouterStateSnapshot, TitleStrategy } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CustomTitleStrategy extends TitleStrategy {

  private readonly titleService = inject(Title);
  private readonly translate = inject(TranslateService);

  private currentTitleSubject = new BehaviorSubject<String>('');
  currentTitle$ = this.currentTitleSubject.asObservable();

  override updateTitle(routerState: RouterStateSnapshot): void {
    const titleKey = this.buildTitle(routerState);
    if (titleKey) {
      // ดึงข้อความแปลภาษาผ่าน ngx-translate
      this.translate.get(titleKey).subscribe((translatedTitle) => {
        // 1. เปลี่ยนชื่อบนแท็บเบราว์เซอร์ให้โดยอัตโนมัติ
        this.titleService.setTitle(translatedTitle);
        // 2. ส่งชื่อที่แปลแล้วเข้าไปในแชร์สเตตเพื่อส่งให้ Header Component
        this.currentTitleSubject.next(translatedTitle);
      });
    }
  }
}