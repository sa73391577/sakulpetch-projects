import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // นำเข้าสำหรับใช้ Async Pipe
import { CustomTitleStrategy } from '../../../core/services/custom-title.strategy';



@Component({
  selector: 'app-header',
  imports: [CommonModule],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
  private titleStrategy = inject(CustomTitleStrategy);
  pageTitle$ = this.titleStrategy.currentTitle$;

  OnInit(): void {

  }

}
