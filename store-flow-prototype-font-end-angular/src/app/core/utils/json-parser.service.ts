import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class JsonParserService {
  toStringArray(value: any): string[] {
    // check NULL or undefined.
    if (value === null || value === undefined) {
      return [];
    }

    // check Array Type.
    if (Array.isArray(value)) {
      return value.map(item => String(item).trim());
    }

    // Check parameter string.
    if (typeof value === 'string') {
      const trimmedValue = value.trim();

      // if value is empty.
      if (!trimmedValue) return [];

      // check "[" and "]" of value
      if (trimmedValue.startsWith('[') && trimmedValue.endsWith(']')) {
        try {
          const validJsonString = trimmedValue.replace(/'/g, '"');
          const parsed = JSON.parse(validJsonString);
          if (Array.isArray(parsed)) {
            return parsed.map(item => String(item).trim());
          }
        } catch (e) {
          const cleaned = trimmedValue.replace(/[\[\]']/g, '').trim();
          return cleaned ? cleaned.split(',').map(item => item.trim()) : [];
        }
      }
      return [trimmedValue];
    }

    // 4. กรณีหลุดนอกเหนือจากโครงสร้างข้างต้น
    return [];
  }
}
