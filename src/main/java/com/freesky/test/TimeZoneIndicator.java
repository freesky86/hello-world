package com.freesky.test;

import java.util.Calendar;
import java.util.TimeZone;

public class TimeZoneIndicator {

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		TimeZone tz = cal.getTimeZone();
		
		System.out.println(tz.getID());
		System.out.println(tz.getDisplayName());
		
		
	}

}
