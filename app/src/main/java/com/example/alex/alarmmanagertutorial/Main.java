package com.example.alex.alarmmanagertutorial;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main extends AppCompatActivity {

    static AlarmManager manager;
    static Intent vibration;
    static Intent noSound;
    static Intent normalMode;
    static Map<Long, LinkedList<PendingIntent>> pendingIntents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.main);
        manager = (AlarmManager)MyApp.getAppContext().getSystemService(ALARM_SERVICE);
        pendingIntents = new TreeMap<>();
        initIntents();
        super.onCreate(savedInstanceState);
    }

    private void initIntents() {
        vibration = new Intent(MyApp.getAppContext(), Alarm.class);
        noSound = new Intent(MyApp.getAppContext(), Alarm.class);
        normalMode = new Intent(MyApp.getAppContext(), Alarm.class);
        vibration.setAction("vibration");
        noSound.setAction("noSound");

        normalMode.setAction("normalMode");
    }

    public void onStartClick(View view) {Log.d("myLogs1", "onStartClick");
        //AudioManager am= (AudioManager) MyApp.getAppContext().getSystemService(Context.AUDIO_SERVICE);
            //am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            // Log.d("myLogs1", "onClick ringer " + am.isVolumeFixed() + " ringerMode = " + am.getRingerMode());

       Data data = getData();
      // setAlarm(data);

       setAlarm1(1, getTime(10, 35));
       //setAlarm2(2, getTime(8, 5));
    }

    private void setAlarm(Data dataItem) {
        Toast.makeText(this, "startAlarm", Toast.LENGTH_SHORT).show();
        Intent startModeIntent = getStartIntent(dataItem.isVibrationAllowed);
        long timeStart = getTime(dataItem.timeBegin[0], dataItem.timeBegin[1]);
        long timeEnd = getTime(dataItem.timeEnd[0], dataItem.timeEnd[1]);
        int today = getTodayDayIndex();
        Logger.log("todayIndex " + today);
        boolean[] checkedDays = Data.getCheckedDaysFromToday(dataItem.checkedDays, today);

        long weekInterval = AlarmManager.INTERVAL_DAY * 7;
        LinkedList<PendingIntent> itemPendingIntents = new LinkedList<>();
        int requestCode = 0;
        PendingIntent pi;

        for(boolean isDayChecked: checkedDays) {
            Logger.log("checkedDay " + isDayChecked);
            if(isDayChecked) {
                pi = getPendingIntent(++requestCode, startModeIntent);
                itemPendingIntents.add(pi);
                //manager.setRepeating(AlarmManager.RTC, timeStart, weekInterval, pi);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    manager.setExactAndAllowWhileIdle(AlarmManager.RTC, timeStart, pi);
                }
                Logger.log(timeStart, "start");

                pi = getPendingIntent(++requestCode, normalMode);
                itemPendingIntents.add(pi);
                //manager.setRepeating(AlarmManager.RTC, timeEnd, weekInterval, pi);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    manager.setExactAndAllowWhileIdle(AlarmManager.RTC, timeEnd, pi);
                }
                Logger.log(timeEnd, "end");
            }
            timeStart += AlarmManager.INTERVAL_DAY;
            timeEnd += AlarmManager.INTERVAL_DAY;
        }
        pendingIntents.put(dataItem.id, itemPendingIntents);

        Log.d("myLogs", "days = " + Arrays.toString(checkedDays));
    }

    private int getTodayDayIndex() {
        Calendar now = Calendar.getInstance();
        int today = now.get(Calendar.DAY_OF_WEEK);
        return getDayIndex(today);
    }

    private int getDayIndex(int day) {
        if(day == Calendar.SUNDAY) return 6;
        else return day-2;
    }

    private Intent getStartIntent(boolean isVibrationAllowed) {
        if(isVibrationAllowed) {
            return vibration;
        } else {
            return noSound;
        }
    }

    public static long getTime(int hour, int minute) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, hour);
        now.set(Calendar.MINUTE, minute);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        return now.getTimeInMillis();
    }

    private static PendingIntent getPendingIntent(int requestCode, Intent i) {
        Log.d("myLogs1", " i isNull " + (i == null));
        return PendingIntent.getBroadcast(MyApp.getAppContext(), requestCode, i,  PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
    }

    public void onStopClick(View view) {
        LinkedList<PendingIntent> list = pendingIntents.get(Long.valueOf(2));
        Log.d("myLogs1", list.toString());
        for(PendingIntent pi: list) {
            manager.cancel(pi);
        }
        Log.d("myLogs1", "onStopClick");
    }




















    private Data getData() {
        Data data = new Data();
        data.id = 1;
        data.description = "description";
        data.checkedDays = new boolean[]{true, true, true, true, true, false, false};
        data.timeBegin = new int[] { 20, 0 };
        data.timeEnd = new int[] { 23, 0 };
        data.isAlarmOn = true;
        data.isVibrationAllowed = false;
        return data;
    }

    static void setAlarm1(long id, long time) {
        //vibration.setAction("testAction1");
        Log.d("myLogs1", "vibration alarm 1");
        LinkedList<PendingIntent> linkedList = new LinkedList<>();
        vibration = new Intent(MyApp.getAppContext(), Alarm.class);
        vibration.setAction("vibration");
        pendingIntents = new TreeMap<>();
        linkedList.add(getPendingIntent(1, vibration));
        pendingIntents.put(id, linkedList);
        manager = (AlarmManager)MyApp.getAppContext().getSystemService(ALARM_SERVICE);

        List<PendingIntent> pis = pendingIntents.get(Long.valueOf(1));
        for(PendingIntent pi: pis) {
           // manager.setRepeating(AlarmManager.RTC_WAKEUP, getTime(9, 40), 60 * 1000, pi);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                manager.setExactAndAllowWhileIdle(AlarmManager.RTC, time, pi);
            }
        }
    }

    static void setAlarm2(long id, long time) {
       // vibration.setAction("testAction2");

        LinkedList<PendingIntent> linkedList = new LinkedList<>();
        linkedList.add(getPendingIntent(2, noSound));
        linkedList.add(getPendingIntent(3, noSound));
        linkedList.add(getPendingIntent(4, noSound));
        pendingIntents.put(id, linkedList);

        List<PendingIntent> pis = pendingIntents.get(Long.valueOf(2));
        for(PendingIntent pi: pis) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                manager.setExactAndAllowWhileIdle(AlarmManager.RTC, time, pi);
            }
        }
    }
}

