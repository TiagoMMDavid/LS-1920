package pt.isel.ls.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
    public static Date parseTime(String date, String pattern) throws ParseException {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
        formatDate.setTimeZone(tz);
        return formatDate.parse(date);
    }
}
