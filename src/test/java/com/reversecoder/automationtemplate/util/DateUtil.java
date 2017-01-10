package com.reversecoder.automationtemplate.util;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Date;

public class DateUtil {
	private DateTime date;

	public DateUtil() {
		this.date = new DateTime();
	}

	public static Integer minsInDay() {
		return 60 * 24;
	}

	public static DateUtil toIkiDate(int min) {
		return new DateUtil(min * 60 * 1000L);
	}

	public DateUtil(Date date) {
		this.date = new DateTime(date);
	}

	public DateUtil(DateTime date) {
		this.date = date;
	}

	public DateUtil(Long date) {
		this.date = new DateTime(date);
	}

	public DateUtil(String datetime) {
		date = ISODateTimeFormat.dateTime().parseDateTime(datetime);
	}

	public DateUtil(String datetime, String format) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
		this.date = dtf.parseDateTime(datetime);
	}

	public String toDateStr() {
		return date.toString("yyyy/MM/dd");
	}

	public String toMySQLDateStr() {
		return toDateStr().replace("/", "-");
	}

	public String toTimeStr() {
		return date.toString("hh:mm a");
	}

	public String toTimeStr24HourWithoutMin() {
		return date.toString("HH:mm");
	}

	public String toTimeStr24Hour() {
		return date.toString("HH:mm:ss");
	}

	public String toDateTimeStr() {
		return date.toString("yyyy/MM/dd HH:mm:ss");
	}

	public String toTimestampStr() {
		return date.toString("yyMMddHHmmss");
	}

	public String toDayName() {
		return date.toString("EEEE").toUpperCase();
	}

	public String toMonthName() {
		return date.toString("MMMM");
	}

	public String toShortMonthName() {
		return date.toString("MMM");
	}

	public String toMonthDayName() {
		return date.toString("MMMM dd").toUpperCase();
	}

	public String toMonthYearName() {
		return date.toString("MMMM, yyyy").toUpperCase();
	}

	public String toYearName() {
		return date.toString("yyyy");
	}

	public String toYearShort() {
		return date.toString("yy");
	}

	public String toDayShort() {
		return date.toString("dd");
	}

	public String toMonthShort() {
		return date.toString("MM");
	}

	public String toHourShort() {
		return date.toString("HH");
	}

	public String toMinShort() {
		return date.toString("mm");
	}

	public String toSecShort() {
		return date.toString("ss");
	}

	public String toStr(String format) {
		return date.toString(format);
	}

	public DateUtil plusMillisecond(Long ms) {
		return new DateUtil(date.plusMillis(ms.intValue()));
	}

	public DateUtil plusMins(Integer mins) {
		return new DateUtil(date.plusMinutes(mins));
	}

	public DateUtil plusDays(Integer days) {
		return new DateUtil(date.plusDays(days));
	}

	public DateUtil plusMonths(Integer months) {
		return new DateUtil(date.plusMonths(months));
	}

	public DateUtil plusYears(Integer years) {
		return new DateUtil(date.plusYears(years));
	}

	public DateUtil minusDays(Integer days) {
		return new DateUtil(date.minusDays(days));
	}

	public DateUtil minusMonths(Integer months) {
		return new DateUtil(date.minusMonths(months));
	}

	public DateUtil minusYears(Integer years) {
		return new DateUtil(date.minusYears(years));
	}

	public boolean isAfter(DateUtil date) {
		return this.date.isAfter(date.date);
	}

	public boolean isBefore(DateUtil date) {
		return this.date.isBefore(date.date);
	}

	public DateUtil beginOfDay() {
		return new DateUtil(new DateTime(date.getYear(), date.getMonthOfYear(),
				date.getDayOfMonth(), 0, 0));
	}

	public DateUtil beginOfWeek() {
		Integer plusDays = 0;
		if(date.getDayOfWeek() == 7) plusDays = 7;
		return new DateUtil(date.withDayOfWeek(DateTimeConstants.MONDAY)).plusDays(plusDays);
	}

	public DateUtil beginOfMonth() {
		return new DateUtil(new DateTime(date.getYear(), date.getMonthOfYear(),
				1, 0, 0));
	}

	public DateUtil beginOfYear() {
		return new DateUtil(new DateTime(date.getYear(), 1, 1, 0, 0));
	}

	public boolean isBetweenInTime(DateUtil date1, DateUtil date2) {
		if (date.getMillisOfDay() >= date1.date.getMillisOfDay()
				&& date.getMillisOfDay() < date2.date.getMillisOfDay())
			return true;
		return false;
	}

	public boolean isBetweenDay(DateUtil date1, DateUtil date2) {
		if (!date.isBefore(date1.date) && !date.isAfter(date2.date)) return true;
		return false;
	}
	
	public int dayOfWeek() {
		return date.getDayOfWeek();
	}

	public int getMonthOfYear() {
		return date.getMonthOfYear();
	}

	public int dayOfMonth() {
		return date.getDayOfMonth();
	}

	public int minOfDay() {
		return date.getMinuteOfDay();
	}

	public Date toDate() {
		return date.toDate();
	}

	public Long getMillis() {
		return date.getMillis();
	}

	public int compareTo(DateUtil date) {
		return this.date.compareTo(date.date);
	}

	public String getDayViewCustomDate() {
		return this.toStr("MMMM dd, yyyy").toUpperCase();
	}

	public Integer getDaysBetween(DateUtil date1, DateUtil date2) {
		return Days.daysBetween(date1.date, date2.date).getDays();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof DateUtil))
			return false;
		DateUtil obj = (DateUtil) o;
		return date.isEqual(obj.date);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(date).toHashCode();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
