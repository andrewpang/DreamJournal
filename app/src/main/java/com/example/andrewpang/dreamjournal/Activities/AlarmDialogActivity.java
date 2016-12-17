package com.example.andrewpang.dreamjournal.Activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.example.andrewpang.dreamjournal.Activities.HomeActivity;
import com.example.andrewpang.dreamjournal.Activities.SnoozedActivity;
import com.example.andrewpang.dreamjournal.AlarmReceiver;
import com.example.andrewpang.dreamjournal.R;

import java.util.Calendar;

public class AlarmDialogActivity extends Activity {

    AlarmManager alarmManager;
    Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        startVibrator();
        playAlarmSound();
        setupAlertDialog();
    }

    void startVibrator() {
        Vibrator vibrator;
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);
    }

    void playAlarmSound() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupAlertDialog() {
        View promptView = LayoutInflater.from(this).inflate(R.layout.prompt_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(promptView);
        final EditText userInput = (EditText) promptView
                .findViewById(R.id.editTextDialogUserInput);

        builder.setMessage("What do you remember?")
               .setTitle("Dream Journal");
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Log.d("Message", userInput.getText().toString());
                r.stop();
                goToHomeActivity();
            }
        });
        builder.setNegativeButton("Snooze", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                r.stop();
                setSnoozeAlarm();
                goToSnoozedActivity();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void setSnoozeAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);

        Intent myIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    void goToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    void goToSnoozedActivity() {
        Intent intent = new Intent(this, SnoozedActivity.class);
        startActivity(intent);
    }

}
