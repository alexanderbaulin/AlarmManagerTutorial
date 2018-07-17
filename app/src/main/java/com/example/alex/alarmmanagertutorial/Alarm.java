package com.example.alex.alarmmanagertutorial;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Alex on 18.05.2018.
 */

public class Alarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() == null) return;
      //  Log.d("myLogs", "on Receive");
        switch (intent.getAction()) {
            case Intent.ACTION_BOOT_COMPLETED:
                Log.d("myLogs1", "boot completed");
                Main.setAlarm1(1, Main.getTime(14, 40));
                //Main.setAlarm2(2, 0);
                break;
        }

        String action = intent.getAction();
        if(action != null) {
                /*
                Log.d("myLogs", "boot compltede");
                Intent i = new Intent(context, Alarm.class);
                i.setAction("testAction");
                AlarmManager manager = (AlarmManager)context.getSystemService(
                        Context.ALARM_SERVICE);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                //Uri defaultRintoneUri = RingtoneManager.getActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_RINGTONE);
                Calendar now = Calendar.getInstance();
                now.set(Calendar.HOUR_OF_DAY, 12);
                long millisec = now.getTimeInMillis();
                manager.setRepeating(AlarmManager.RTC_WAKEUP, millisec, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
                */
            if(action.equals("vibration")) {

                Main.setAlarm1(1, System.currentTimeMillis() + 60000);

                Log.d("myLogs1", "vibration");
                AudioManager am= (AudioManager) MyApp.getAppContext().getSystemService(Context.AUDIO_SERVICE);
                NotificationManager notificationManager = (NotificationManager) MyApp.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && !notificationManager.isNotificationPolicyAccessGranted()) {
                    Intent settingAccessPolicy = new Intent(
                            android.provider.Settings
                                    .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    context.startActivity(settingAccessPolicy);
                } else {
                    am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                   // Log.d("myLogs1", "onClick ringer " + am.isVolumeFixed() + " ringerMode = " + am.getRingerMode());
                }

                /*
                Toast.makeText(context, "receive alarm", Toast.LENGTH_SHORT).show();
                Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone defaultRingtone = RingtoneManager.getRingtone(context, ringtone);
                defaultRingtone.play();
                */
            } else if(action.equals("noSound")) {
                Log.d("myLogs1", "noSound");
                Main.setAlarm2(2, System.currentTimeMillis() + 60000);

            } else if(action.equals("normalMode")) {
                Log.d("myLogs1", "normalMode");
            }
        }
    }
}


// PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
// PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,"My Tag");
//wl.acquire(1000);
                /*
                WakeLocker.acquire(context);
                AudioManager am= (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && !notificationManager.isNotificationPolicyAccessGranted()) {
                    Intent settingAccessPolicy = new Intent(
                            android.provider.Settings
                                    .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    context.startActivity(settingAccessPolicy);
                } else {
                    am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    Log.d("myLogs", "onClick ringer " + am.isVolumeFixed() + " ringerMode = " + am.getRingerMode());
                }
               // wl.release();
                WakeLocker.release();
                */