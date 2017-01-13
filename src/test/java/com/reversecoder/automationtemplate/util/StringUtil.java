package com.reversecoder.automationtemplate.util;

/**
 * @author Md. Rashsadul Alam
 *
 */
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtil {

    private static final String REPLACE_MEASUREMENT_DATE_HEAD = "/Date(";

    private static final String REPLACE_MEASUREMENT_DATE_FOOTER = ")/";

    public static final String FORMAT_MEASUREMENT_DATE_PATTERN_24 = "yyyy:MM:dd:HH:mm:ss";
    public static final String FORMAT_MEASUREMENT_DATE_PATTERN_24_NEW = "yyyy:MMMM:dd:HH:mm:ss:EEEE";

    public static final String FORMAT_MEASUREMENT_DATE_PATTERN_12 = "yyyy:MM:dd:hh:mm:ss:a";

    public static final String FORMAT_MEASUREMENT_READINGS_DATE_PATTERN_24 = "yy:MM:dd:HH:mm:ss";

    public static final String FORMAT_MEASUREMENT_READINGS_DATE_PATTERN_12 = "yy:MM:dd:hh:mm:ss:a";
    public static final String FORMAT_MEASUREMENT_READINGS_DATE_PATTERN_1 = "h:mm a";
    public static final String FORMAT_MEASUREMENT_READINGS_DATE_PATTERN_2 = "HH:mm";

    public static final String FORMAT_MEASUREMENT_DATE_PATTERN_N1 = "yyyy:MMMM:dd:hh:mm:ss:a";
    public static final String FORMAT_MEASUREMENT_DATE_PATTERN_N2 = "yyyy:MMMM:dd:hh:mm:ss:a:EEEE";
    public static final String FORMAT_MEASUREMENT_DATE_PATTERN_N3 = "MMMM dd, yyyy";
    public static final String FORMAT_MEASUREMENT_DATE_PATTERN_N4 = "MMM dd, yyyy";
    public static final String FORMAT_MEASUREMENT_DATE_PATTERN_N5 = "dd, yyyy";
    public static final String FORMAT_MEASUREMENT_DATE_PATTERN_N6 = "MMMM, yyyy";
    public static final String FORMAT_MEASUREMENT_DATE_PATTERN_N7 = "yyyy";

    private static final String FORMAT_MEASUREMENT_DATE_TIMEZONE = "GMT";

    private static final String SPLITFORMAT_MEASUREMENT_DATE_PATTERN = ":";

    public static final String FORMAT_GETMEASUREMENTDATA_DATE = "MM-dd-yyyy";

    public static String getFormattedHypenString(int value) {

        return value + "";

    }

    public static long formatMeasurementServerTime(String dateStr) {

        String newString = dateStr;

        newString = newString.replace(REPLACE_MEASUREMENT_DATE_HEAD, "");

        newString = newString.replace(REPLACE_MEASUREMENT_DATE_FOOTER, "");

        long unixtime = Long.valueOf(newString.substring(0, 13));

        String calculationSign = newString.substring(13, 14);

        long calculationValue = Long.valueOf(newString.substring(14, 16));

        calculationValue = (calculationValue * 60) + Long.valueOf(newString.substring(16));

        long returnValue = 0;

        if (calculationSign.equals("-")) {
            returnValue = unixtime - (calculationValue * 60 * 1000);
        } else {
            returnValue = unixtime + (calculationValue * 60 * 1000);
        }
        return returnValue;
    }

    public static long formatMeasurementServerTime201(String dateStr) {

        int year = Integer.valueOf(dateStr.substring(0, 4));

        int month = Integer.valueOf(dateStr.substring(5, 7));

        int day = Integer.valueOf(dateStr.substring(8, 10));

        int hour = Integer.valueOf(dateStr.substring(11, 13));

        int minute = Integer.valueOf(dateStr.substring(14, 16));

        int second = Integer.valueOf(dateStr.substring(17, 19));

        Calendar cal = Calendar.getInstance();

        cal.clear();

        cal.set(year, month - 1, day, hour, minute, second);

        long unixTime = cal.getTimeInMillis();

        return unixTime;
    }

    public static String[] splitMeasurementDate(long date, String format) {
        if (format == null) {
            format = FORMAT_MEASUREMENT_DATE_PATTERN_24;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Date time = new Date(date);

        String strDate = sdf.format(time);

        return strDate.split(SPLITFORMAT_MEASUREMENT_DATE_PATTERN);
    }

    public static String convertGetMeasurementDateType(long date) {

        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_GETMEASUREMENTDATA_DATE);

        Date time = new Date(date);

        String strDate = sdf.format(time);

        return strDate;
    }

    public static String convertGetMeasurementDateType(long date, String format) {

        return convertGetMeasurementDate(date, FORMAT_MEASUREMENT_DATE_PATTERN_N3) + " at "
                + convertGetMeasurementDate(date, format);
    }

    public static String convertGetMeasurementDate(long date, String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Date time = new Date(date);

        String strDate = sdf.format(time);

        return strDate;
    }

}
