import { Component, inject } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';


@Component({
  selector: 'app-switch-language',
  imports: [],
  templateUrl: './switch-language.component.html',
  styleUrl: './switch-language.component.css',
})
export class SwitchLanguageComponent {

  private translate = inject(TranslateService);

  isCurrentLang(lang: string): boolean {
    return this.translate.currentLang() === lang; // 💡 เติมวงเล็บ () หลัง currentLang
  }

  changeLanguage(lang: string) {
    this.translate.use(lang);
    localStorage.setItem('user_lang', lang);
  }

}
