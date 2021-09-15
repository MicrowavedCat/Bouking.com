package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateConvert {
	public static String toString(long ms) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ms);
	}
	
	public static long toLong(int year, int month, int date, int hour, int minute, int second) {
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, date, hour, minute, second);
		
		return c.getTimeInMillis();
	}
}
