package utils;

import java.util.Calendar;

public class DateConverter {
	public static long getEndOfDay(long ms) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(ms);
		
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return c.getTimeInMillis();
	}
}
