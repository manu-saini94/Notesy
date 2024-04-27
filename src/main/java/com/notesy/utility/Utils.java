package com.notesy.utility;


import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Utils {
	

	private static final String ERR_STRING_FORMAT = "Error in formating string, possibly due to mismatched number of placeholders and objects";

	public static String formatSafe(String format, Object... args) {
		try {
			return String.format(format, args);
		} catch (Exception ex) {
			log.error(ERR_STRING_FORMAT, ex);
		}
		return null;
	}
	
	

}
