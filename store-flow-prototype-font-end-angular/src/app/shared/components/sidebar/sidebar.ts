import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';
import { SwitchLanguageComponent } from '../switch-language/switch-language.component';

@Component({
  selector: 'app-sidebar',
  imports: [
    CommonModule,
    RouterModule,
    TranslatePipe,
    SwitchLanguageComponent
  ],
  standalone: true, // กำหนดเป็น Standalone Component 
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {

  private authService = inject(AuthService);
  private translate = inject(TranslateService);
  private router = inject(Router);

  // 💡 ข้อควรรู้: ใน v18+ คำสั่ง setDefaultLang() ถูกเปลี่ยนชื่อเป็น setFallbackLang()
  constructor() {
    this.translate.setFallbackLang('th');
    this.translate.use('th').subscribe({
      next: () => console.log('โหลดคลังคำศัพท์สำเร็จแล้ว!'),
      error: (err) => console.error('ยิงดึงไฟล์ JSON ไม่ผ่านเนื่องจาก:', err)
    });
  }

  // เริ่มต้นให้ปิดเมนูไว้ (false)
  isMenuOpen = false;

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  // ฟังก์ชันกดออกจากระบบ
  onLogout() {
    this.authService.logout(); // สั่งให้สถานะล็อกอินใน Service เป็น false
    this.router.navigate(['/login']); // ดีดผู้ใช้กลับไปหน้า Login
  }

}
