package com.walarm.wuasta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

/**
 * Created by AnushaB05 & GokulVSD on 24-09-2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //Pending intent calls calls this method

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        //Incase a default Alarm ringtone is not set
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        }

        //Ringtone is played
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        //UI for the Alarm ringing is called
        Intent intent1 = new Intent(context, AlarmRingingActivity.class);
        context.startActivity(intent1);
    }
}
