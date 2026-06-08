package com.example.prototype.services.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DateUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
	
	private static final DateTimeFormatter DATE_FORMATTER_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	//this error if new Object.
	private DateUtils() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}
	
	public static LocalDate convertStringToLocalDate(String dateStr) {
		try {
			logger.info("convertStringToLocalDate [ dateStr ] : {}",dateStr);
	        return LocalDate.parse(dateStr, DATE_FORMATTER_YYYY_MM_DD);
		}
		catch(DateTimeParseException err) {
			logger.error("DateTimeParseException : {}",err);
			return null;
		}
	}
	
	
	
}
