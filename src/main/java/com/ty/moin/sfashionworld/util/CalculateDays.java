package com.ty.moin.sfashionworld.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalculateDays {

	static long difference_In_Days;

	public static long findDifference(String start_date, String end_date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {

			Date d1 = sdf.parse(start_date);
			Date d2 = sdf.parse(end_date);

			long difference_In_Time = d2.getTime() - d1.getTime();

			long difference_In_Seconds = TimeUnit.MILLISECONDS.toSeconds(difference_In_Time) % 60;

			long difference_In_Minutes = TimeUnit.MILLISECONDS.toMinutes(difference_In_Time) % 60;

			long difference_In_Hours = TimeUnit.MILLISECONDS.toHours(difference_In_Time) % 24;

			difference_In_Days = TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365;

		} catch (ParseException e) {
			e.printStackTrace();

		}
		return difference_In_Days;
	}
}
