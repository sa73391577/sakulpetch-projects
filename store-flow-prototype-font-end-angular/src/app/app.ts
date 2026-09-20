import { Component, signal, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('talent-hub-web');
  private translate = inject(TranslateService);

  constructor() {
    this.translate.setFallbackLang('th');
    this.translate.use('th'); // บังคับให้โหลดและสลับมาใช้ 'th' ทันที
  }


}
