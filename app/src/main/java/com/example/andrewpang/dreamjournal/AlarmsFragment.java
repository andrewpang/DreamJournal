package com.example.andrewpang.dreamjournal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import butterknife.BindView;
import butterknife.OnClick;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class AlarmsFragment extends Fragment {

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @BindView(R.id.timePicker)
    TimePicker timePicker;

    public AlarmsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarms, container, false);
        alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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

        Intent myIntent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

}
