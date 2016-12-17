package com.example.andrewpang.dreamjournal;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import com.example.andrewpang.dreamjournal.Activities.AlarmDialogActivity;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
//        ringtone.play();

//        Activity activity = (Activity) context;
//        AlarmDialogActivity dialogFragment = new AlarmDialogActivity();
//        dialogFragment.show(activity.getFragmentManager(), "Alarm");

        Intent i = new Intent(context, AlarmDialogActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }


}
