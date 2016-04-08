package com.wjwl.mobile.taocz.untils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dateformat {

	public static int getWeek(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getDay();
	}

	static String[] dates = new String[] { "00", "01", "02", "03", "04", "05",
			"06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16",
			"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
			"28", "29", "30", "31" };

	public static String getDate(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dates[date.getDate()];
	}

	public static int getMonth(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getMonth();
	}

	public static int getYear(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getYear() + 1900;
	}

	public static int DateToInt(String dateStr) {
		String day = getYear(dateStr) + "" + getMonth(dateStr) + ""
				+ getDate(dateStr);
		return Integer.parseInt(day);
	}

	public static int DateThanDate(String nowdate, String otherdate) {
		// isLeapYear(Dateformat.getYear(nowdate.toString()));
		int days1 = getDaystoNewyear(Dateformat.getYear(nowdate.toString()),
				Dateformat.getMonth(nowdate.toString()),
				Integer.parseInt(Dateformat.getDate(nowdate.toString())));
		int days2 = getDaystoNewyear(Dateformat.getYear(otherdate.toString()),
				Dateformat.getMonth(otherdate.toString()),
				Integer.parseInt(Dateformat.getDate(otherdate.toString())));
		return days2 - days1;
	}

	public static int getDaystoNewyear(int year, int month, int day) {
		int days = 0;
		switch (month) {
		case 12:
			days += 30;
		case 11:
			days += 31;
		case 10:
			days += 30;
		case 9:
			days += 31;
		case 8:
			days += 31;
		case 7:
			days += 30;
		case 6:
			days += 31;
		case 5:
			days += 30;
		case 4:
			days += 31;
		case 3:
			if (isLeapYear(year))
				days += 29;
			else
				days += 28;
		case 2:
			days += 31;
		case 1:
			days += 0;
			break;
		}
		days += day;
		return days;
	}

	public static boolean isLeapYear(int year) {
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			return true;
		} else {
			return false;
		}
	}
}
