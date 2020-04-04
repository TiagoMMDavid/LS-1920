package pt.isel.ls.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    public static Date parseTimeWithTimezone(String date, String pattern) throws ParseException {
        return parseTime(date, pattern, TimeZone.getDefault());
    }

    public static Date parseTime(String date, String pattern) throws ParseException {
        return parseTime(date, pattern, TimeZone.getTimeZone("UTC"));
    }

    private static Date parseTime(String date, String pattern, TimeZone tz) throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
        formatDate.setTimeZone(tz);
        return formatDate.parse(date);
    }
}
