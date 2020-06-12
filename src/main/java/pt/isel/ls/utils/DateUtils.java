package pt.isel.ls.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    // Rounds date passed as argument to the closest multiple of ten minute value
    public static Date roundDateToTenMin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int unroundedMinutes = calendar.get(Calendar.MINUTE);
        int mod = unroundedMinutes % 10;
        calendar.add(Calendar.MINUTE, mod == 0 ? 10 : 10 - mod);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String formatDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
}
