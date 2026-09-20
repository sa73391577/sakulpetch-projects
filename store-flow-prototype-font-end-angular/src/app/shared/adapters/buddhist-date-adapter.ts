import { Injectable } from '@angular/core';
import { NativeDateAdapter } from '@angular/material/core';

@Injectable()
export class BuddhistDateAdapter extends NativeDateAdapter {

    // 1. สั่งให้แสดงผลตัวเลขปีบนหน้าจอให้ +543 ปี
    override format(date: Date, displayFormat: Object): string {
        if (displayFormat === 'input') {
            const day = date.getDate().toString().padStart(2, '0');
            const month = (date.getMonth() + 1).toString().padStart(2, '0');
            const year = date.getFullYear() + 543; // บวก 543 ปีสำหรับ พ.ศ.
            return `${day}-${month}-${year}`;
        }
        return date.toDateString();
    }

    // 2. ปรับการดึงค่าตอนดึงข้อมูลจากเมนูปฏิทินรายปี
    override getYearName(date: Date): string {
        return (date.getFullYear() + 543).toString();
    }
}

// ตั้งค่า Format ที่ต้องการให้ตัวปฏิทินเรียกใช้
export const BUDDHIST_DATE_FORMATS = {
    parse: {
        dateInput: { month: 'short', year: 'numeric', day: 'numeric' },
    },
    display: {
        dateInput: 'input',
        monthYearLabel: { year: 'numeric', month: 'short' },
        dateA11yLabel: { year: 'numeric', month: 'long', day: 'numeric' },
        monthYearA11yLabel: { year: 'numeric', month: 'long' },
    },
};
