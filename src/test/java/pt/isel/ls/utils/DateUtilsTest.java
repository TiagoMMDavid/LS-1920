package pt.isel.ls.utils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateUtilsTest {

    @Test
    public void roundDateToTenMinTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.JANUARY, 1, 12, 25, 0);
        Date date = DateUtils.roundDateToXMin(calendar.getTime(), 10);
        calendar.setTime(date);

        Calendar expected = Calendar.getInstance();
        expected.set(2020, Calendar.JANUARY, 1, 12, 30, 0);

        assertEquals(expected.get(Calendar.MINUTE), calendar.get(Calendar.MINUTE));
    }

    @Test
    public void isDateMultipleOfTenMinTest_ShouldReturnTrue() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.JANUARY, 1, 12, 30, 0);

        assertTrue(DateUtils.isDateMinutesMultipleOf(calendar.getTime(), 10));
    }

    @Test
    public void isDateMultipleOfTenMinTest_ShouldReturnFalse() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.JANUARY, 1, 12, 35, 0);

        assertFalse(DateUtils.isDateMinutesMultipleOf(calendar.getTime(), 10));
    }

    @Test
    public void formatDateTest() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm (z)");
        assertEquals(format.format(date), DateUtils.formatDate(date, "dd-MM-yyyy HH:mm (z)"));
    }
}
