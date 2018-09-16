package com.export.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {
	public static String formatDate(LocalDate localDate, String regex) {
		String str = "";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(regex);
		str = localDate.format(formatter);
		return str;
	}
}
