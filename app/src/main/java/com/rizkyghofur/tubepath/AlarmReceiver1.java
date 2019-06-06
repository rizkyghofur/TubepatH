package com.rizkyghofur.tubepath;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Jaison on 17/06/17.
 */

public class AlarmReceiver1 extends BroadcastReceiver {

    String TAG = "AlarmReceiver1";

    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO Auto-generated method stub
        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.
                Log.d(TAG, "onReceive: BOOT_COMPLETED");
                LocalData localData = new LocalData(context);
                NotificationScheduler.setReminder(context, AlarmReceiver1.class, localData.get_hour(), localData.get_min());
                return;
            }
        }
        Log.d(TAG, "onReceive: ");

        //Trigger the notification
        NotificationScheduler.showNotification(context, BelajarActivity.class,
                "You have 5 unwatched videos", "Watch them now?");
    }
}


