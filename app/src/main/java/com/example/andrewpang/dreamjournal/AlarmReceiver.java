package com.example.andrewpang.dreamjournal;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
//        ringtone.play();

//        Activity activity = (Activity) context;
//        AlarmDialogFragment dialogFragment = new AlarmDialogFragment();
//        dialogFragment.show(activity.getFragmentManager(), "Alarm");

        Intent i = new Intent(context, AlarmDialogFragment.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }


}
