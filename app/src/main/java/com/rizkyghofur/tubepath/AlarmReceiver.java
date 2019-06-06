package com.rizkyghofur.tubepath;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    MediaPlayer player;

    @Override
    public void onReceive(Context context, Intent intent) {
        //TODO Auto-generated method stub
        Toast.makeText(context, "Alarm Aktif!", Toast.LENGTH_LONG).show();
        player = MediaPlayer.create(context, R.raw.alarm);
        player.start();
        
    }
}

