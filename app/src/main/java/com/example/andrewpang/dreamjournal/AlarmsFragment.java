package com.example.andrewpang.dreamjournal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class AlarmsFragment extends Fragment {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private FloatingActionButton addButton;
    private View view;
    private int hour, minute;

    public AlarmsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_alarms, container, false);
        alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

        setupRecyclerView();
        setupAddAlarmDialog();

        return view;
    }

    private void setupRecyclerView() {
        final RecyclerView feedRecyclerView = ButterKnife.findById(view, R.id.alarm_recycler_view);
        feedRecyclerView.setHasFixedSize(true);
        AlarmRecyclerViewAdapter alarmRecyclerViewAdapter = new AlarmRecyclerViewAdapter(mockAlarmEntries());
        feedRecyclerView.setAdapter(alarmRecyclerViewAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        feedRecyclerView.setLayoutManager(llm);
    }

    private ArrayList<AlarmEntry> mockAlarmEntries() {
        ArrayList<AlarmEntry> alarmEntries = new ArrayList<>();
        alarmEntries.add(new AlarmEntry());
        alarmEntries.add(new AlarmEntry());
        return alarmEntries;
    }

    private void setupAddAlarmDialog() {
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        addButton = (FloatingActionButton) view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setAlarm(hourOfDay, minute);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

//    @OnClick(R.id.btn_logout)
//    void logout() {
//        FirebaseAuth.getInstance().signOut();
//    }

    void setAlarm(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        Intent myIntent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }



}
