package com.example.andrewpang.dreamjournal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TimePicker;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @BindView(R.id.timePicker)
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    @OnClick(R.id.btn_logout)
    void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    @OnClick(R.id.btn_set_alarm)
    void setAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calendar.set(Calendar.MINUTE, timePicker.getMinute());

        Intent myIntent = new Intent(HomeActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(HomeActivity.this, 0, myIntent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }


}
