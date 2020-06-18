package pt.isel.ls.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Class containing helper methods for Dates
 */
public class DateUtils {

    public static Date parseTimeWithTimezone(String date, String pattern) throws ParseException {
        return parseTime(date, pattern, TimeZone.getDefault());
    }

    public static Date parseTime(String date, String pattern) throws ParseException {
        return parseTime(date, pattern, TimeZone.getTimeZone("UTC"));
    }

    /**
     * Gets a Date with the given parameters
     * @param date String containing the date
     * @param pattern Pattern used to represent the String date
     * @param tz Timezone to be used for the Date
     * @return The parsed Date
     * @throws ParseException Whenever the String date does not correspond to the pattern
     */
    private static Date parseTime(String date, String pattern, TimeZone tz) throws ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat(pattern);
        formatDate.setTimeZone(tz);
        return formatDate.parse(date);
    }

    // Rounds date passed as argument to the closest multiple of ten minute value

    /**
     * Rounds a date to ten minutes, as in, sums the necessary amount to get to the next multiple of ten
     * @param date Date to be rounded
     * @return The rounded date
     */
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

    /**
     * Checks if a Date is multiple of a given value (x)
     * @param date Date to be checked
     * @param x Value that represents the desired Multiple that will be checked
     * @return whether the Date is multiple of x
     */
    public static boolean isDateMinutesMultipleOf(Date date, int x) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int minutes = calendar.get(Calendar.MINUTE);
        return minutes % x == 0;
    }

    public static String formatDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
}
