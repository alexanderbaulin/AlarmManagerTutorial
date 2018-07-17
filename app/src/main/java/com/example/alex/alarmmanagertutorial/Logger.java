package com.example.alex.alarmmanagertutorial;

import android.app.AlarmManager;
import android.util.Log;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Alex on 21.06.2018.
 */

public class Logger {
    private static String LOG = "myLogs1";
    static void log() {
        Log.d(LOG, "test log");
    }


    static void log(String s) {
        Log.d(LOG, s);
    }

    static void log(long time, String message) {
        Date date = new Date(time);
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK);
        DateFormat tf = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.UK);
        String formattedDate = df.format(date);
        String formattedTime = tf.format(date);
        Logger.log(message + " " + formattedDate + " " + formattedTime);
    }



    private void log(Data data) {
        /*
        log(data.timeBegin, "start");
        log(data.timeEnd, "end");
        boolean[] cd = getArrayStartFromToday(data.checkedDays, getTodayDayIndex());
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
        */
    }

}
