package com.example.prototype.services.utils;

import java.util.regex.Pattern;

public final class ValidationUtils {
	
	//this error if new Object.
	private ValidationUtils() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}
	
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }
	
    public static boolean isValidIdCard(String idCard) {
    	
    	idCard = idCard.replace("-", "").trim();
    	
        // 1. ตรวจสอบเบื้องต้น: ห้ามเป็น null และต้องเป็นตัวเลข 13 หลักเท่านั้น
        if (idCard == null || !idCard.matches("^\\d{13}$")) {
            return false;
        }

        // 2. คำนวณผลรวมตามสูตร Check Digit ของบัตรประชาชนไทย
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            // ดึงตัวเลขแต่ละหลัก (จากซ้ายไปขวา ตัวที่ 1 ถึง 12)
            int digit = Character.getNumericValue(idCard.charAt(i));
            // ตัวคูณจะเริ่มจาก 13 ลดลงไปเรื่อยๆ จนถึง 2
            sum += digit * (13 - i); 
        }

        // 3. นำผลรวมมา Mod 11
        int step1 = sum % 11;
        
        // 4. นำ 11 ไปลบออกด้วยผลลัพธ์จากข้อ 3 แล้ว Mod 10 เพื่อหาตัวเลขหลักสุดท้ายที่ถูกต้อง
        int checkDigit = (11 - step1) % 10;

        // 5. นำตัวเลขหลักสุดท้ายที่คำนวณได้ ไปเทียบกับเลขหลักที่ 13 ของบัตรจริง
        int lastDigit = Character.getNumericValue(idCard.charAt(12));

        return checkDigit == lastDigit;
    }
	
	
}
