package com.quigley.time;

import java.text.DecimalFormat;

public class Duration {
	private long milliseconds;
	private static DecimalFormat formatter = new DecimalFormat("00");

	private static long SECOND = 1000;
	private static long MINUTE = SECOND * 60;
	private static long HOUR = MINUTE * 60;
	private static long DAY = HOUR * 24;
	private static long YEAR = DAY * 365;
	
	public Duration() {
		this.milliseconds = 0;
	}
	
	public Duration(long milliseconds) {
		this.milliseconds = milliseconds;
	}

	public long getMilliseconds() {
		return milliseconds;
	}

	public void setMilliseconds(long milliseconds) {
		this.milliseconds = milliseconds;
	}
	
	public void add(Duration duration) {
		milliseconds += duration.getMilliseconds();
	}
	
	public String asHoursMinutes() {
		long accumulator = milliseconds;
		
		long hours = (long) accumulator / HOUR;
		accumulator -= hours * HOUR;
		
		long minutes = (long) accumulator / MINUTE;
		accumulator -= minutes * MINUTE;
		
		StringBuilder out = new StringBuilder();
		
		out.append(formatter.format(hours)).append(":");
		out.append(formatter.format(minutes));
		
		return out.toString();
	}
	
	public String asLongFormat() {
		long accumulator = milliseconds;
		
		long years = (long) accumulator / YEAR;
		accumulator -= years * YEAR;
		
		long days = (long) accumulator / DAY;
		accumulator -= days * DAY;
		
		long hours = (long) accumulator / HOUR;
		accumulator -= hours * HOUR;
		
		long minutes = (long) accumulator / MINUTE;
		accumulator -= minutes * MINUTE;
		
		long seconds = (long) accumulator / SECOND;
		accumulator -= seconds * SECOND;
		
		StringBuilder out = new StringBuilder();
		if(years > 0) {
			out.append(years).append("y");
		}
		if(days > 0) {
			out.append(days).append("d ");
		}
		out.append(formatter.format(hours)).append(":");
		out.append(formatter.format(minutes)).append(":");
		out.append(formatter.format(seconds)).append(":");
		out.append("0.").append(milliseconds);
		
		return out.toString();
	}
}
