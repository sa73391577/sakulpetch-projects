import { Injectable, Inject, LOCALE_ID } from "@angular/core";
import { formatDate } from '@angular/common';

@Injectable({
  providedIn: "root",
})
export class DateUtilsService {

  // Inject LOCALE_ID for Default Time.
  constructor(@Inject(LOCALE_ID) private local: string) { }
  //dateStr formate : dd-mm-yyyy.
  convertFormat(dateStr: string, format: string): string {
    if (!dateStr) return '';
    const parts = dateStr.split('-');
    if (parts.length != 3) {
      console.log(" Invalid data format. Expect dd-mm-yyyy");
      return dateStr;
    }
    console.log("dateStr : ", dateStr);
    console.log("parts : ", parts);
    const dateObj = new Date(`${parts[2]}-${parts[1]}-${parts[0]}`);
    if (isNaN(dateObj.getTime())) {
      console.log("Invalid Date.");
      return dateStr;
    }
    return formatDate(dateObj, format, this.local);
  }
}
