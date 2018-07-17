package com.example.alex.alarmmanagertutorial;

import android.app.AlarmManager;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.alex.alarmmanagertutorial", appContext.getPackageName());
    }

    @Test
    public void testCalendar() {
        Calendar now = Calendar.getInstance();
        long time = now.getTimeInMillis();
        log(time, "now");
        Data data = getData1();
        log(data);
    }

    private void log(Data data) {
        log(data.timeBegin, "start");
        log(data.timeEnd, "end");
        boolean[] cd = Data(data.checkedDays, getTodayDayIndex());
        Calendar c;
        long timeBegin, timeEnd;

        c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, data.timeBegin[0]);
        c.set(Calendar.MINUTE, data.timeBegin[1]);
        timeBegin = c.getTimeInMillis();

        c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, data.timeEnd[0]);
        c.set(Calendar.MINUTE, data.timeEnd[1]);
        timeEnd = c.getTimeInMillis();

        Logger.log(Arrays.toString(cd));

        for(boolean checkedDay: cd) {
            if(checkedDay) {
                log(timeBegin, "start");
            }
            timeBegin += AlarmManager.INTERVAL_DAY;
        }
    }

    private void log(int[] time, String message) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, time[0]);
        c.set(Calendar.MINUTE, time[1]);
        log(c.getTimeInMillis(), message);
    }

    private void log(long time, String message) {
        Date date = new Date(time);
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK);
        DateFormat tf = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.UK);
        String formattedDate = df.format(date);
        String formattedTime = tf.format(date);
        Logger.log(message + " " + formattedDate + " " + formattedTime);
    }

    private Data getData1() {
        Data data = new Data();
        data.timeBegin = new int[] { 9, 0 };
        data.timeEnd = new int[] { 18, 0};
        data.checkedDays = new boolean[] { true, true, true, true, true, false, false };
        return data;
    }

    @Test
    public void testParseDay() {
        assertEquals(getDayIndex(Calendar.MONDAY),0);
        assertEquals(getDayIndex(Calendar.TUESDAY),1);
        assertEquals(getDayIndex(Calendar.WEDNESDAY),2);
        assertEquals(getDayIndex(Calendar.THURSDAY),3);
        assertEquals(getDayIndex(Calendar.FRIDAY),4);
        assertEquals(getDayIndex(Calendar.SATURDAY),5);
        assertEquals(getDayIndex(Calendar.SUNDAY),6);
    }

    @Test
    public void testGetArrayStartFromToday() {
        boolean[] data1, data2;
        int todayDayIndex;

        todayDayIndex = getDayIndex(Calendar.MONDAY);

        data1 = new boolean[]{true, true, true, true, true, false, false};
        data2 = new boolean[]{true, true, true, true, true, false, false};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);
        data1 = new boolean[]{false, false, false, false, false, true, true};
        data2 = new boolean[]{false, false, false, false, false, true, true};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);
        data1 = new boolean[]{false, true, false, true, false, true, false};
        data2 = new boolean[]{false, true, false, true, false, true, false};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);

        todayDayIndex = getDayIndex(Calendar.TUESDAY);

        data1 = new boolean[]{true, true, true, true, true, false, false};
        data2 = new boolean[]{true, true, true, true, false, false, true};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);
        data1 = new boolean[]{false, false, false, false, false, true, true};
        data2 = new boolean[]{false, false, false, false, true, true, false};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);
        data1 = new boolean[]{false, true, false, true, false, true, false};
        data2 = new boolean[]{true, false, true, false, true, false, false};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);


        todayDayIndex = getDayIndex(Calendar.WEDNESDAY);

        data1 = new boolean[]{true, true, true, true, true, false, false};
        data2 =  new boolean[]{true, true, true, false, false, true, true};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);
        data1 = new boolean[]{false, false, false, false, false, true, true};
        data2 = new boolean[]{false, false, false, true, true, false, false};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);
        data1 = new boolean[]{false, true, false, true, false, true, false};
        data2 = new boolean[]{false, true, false, true, false, false, true};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);

        todayDayIndex = getDayIndex(Calendar.THURSDAY);

        data1 = new boolean[]{true, true, true, true, true, false, false};
        data2 = new boolean[]{true, true, false, false, true, true, true};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);
        data1 = new boolean[]{false, false, false, false, false, true, true};
        data2 = new boolean[]{false, false, true, true, false, false, false};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);
        data1 = new boolean[]{false, true, false, true, false, true, false};
        data2 = new boolean[]{true, false, true, false, false, true, false};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);


        todayDayIndex = getDayIndex(Calendar.FRIDAY);

        data1 = new boolean[]{true, true, true, true, true, false, false};
        data2 = new boolean[]{true, false, false, true, true, true, true};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);
        data1 = new boolean[]{false, false, false, false, false, true, true};
        data2 = new boolean[]{false, true, true, false, false, false, false};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);
        data1 = new boolean[]{false, true, false, true, false, true, false};
        data2 = new boolean[]{false, true, false, false, true, false, true};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);

        todayDayIndex = getDayIndex(Calendar.SATURDAY);

        data1 = new boolean[]{true, true, true, true, true, false, false};
        data2 = new boolean[]{false, false, true, true, true, true, true};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);
        data1 = new boolean[]{false, false, false, false, false, true, true};
        data2 = new boolean[]{true, true, false, false, false, false, false};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);
        data1 = new boolean[]{false, true, false, true, false, true, false};
        data2 = new boolean[]{true, false, false, true, false, true, false};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);

        todayDayIndex = getDayIndex(Calendar.SUNDAY);
        data1 = new boolean[]{true, true, true, true, true, false, false};
        data2 = new boolean[]{false, true, true, true, true, true, false };
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);
        data1 = new boolean[]{false, false, false, false, false, true, true};
        data2 = new boolean[]{true, false, false, false, false, false, true};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);
        data1 = new boolean[]{false, true, false, true, false, true, false};
        data2 = new boolean[]{false, false, true, false, true, false, true};
        assertArrayEquals(null, Data.getCheckedDaysFromToday(data1, todayDayIndex), data2);

    }

    private boolean[] Data(boolean[] checkedDays, int todayIndex) {
        boolean[] result = new boolean[7];
        int d = 0;
        for(int i = todayIndex; i < checkedDays.length; ++i) {
            result[d++] = checkedDays[i];
        }
        for(int i = 0; i < todayIndex; ++i) {
            result[d++] = checkedDays[i];
        }
        return result;
    }

    private int getDayIndex(int day) {
        if(day == Calendar.SUNDAY) return 6;
        else return day-2;
    }

    private int getTodayDayIndex() {
        Calendar now = Calendar.getInstance();
        int today = now.get(Calendar.DAY_OF_WEEK);
        return getDayIndex(today);
    }


}
