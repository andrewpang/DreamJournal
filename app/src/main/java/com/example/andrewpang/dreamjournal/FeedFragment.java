package com.example.andrewpang.dreamjournal;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import butterknife.ButterKnife;
import com.example.andrewpang.dreamjournal.Adapters.FeedRecyclerViewAdapter;
import com.example.andrewpang.dreamjournal.Entries.AlarmEntry;
import com.example.andrewpang.dreamjournal.Entries.DreamEntry;
import com.example.andrewpang.dreamjournal.Entries.Entry;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

public class FeedFragment extends Fragment {

    private View view;
    private int type;
    private static final int ALARM_TYPE = 2;
    private RecyclerView.Adapter recyclerViewAdapter;
    private FloatingActionButton addButton;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private int hour, minute;
    private DatabaseReference mDatabase;
    private LinearLayoutManager llm;

    public FeedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed, container, false);
        alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        type = getArguments().getInt("type");
        recyclerViewAdapter = new FeedRecyclerViewAdapter(type, mockDreamEntries());
        setupRecyclerView(recyclerViewAdapter);
        setupAddButton();

        return view;
    }

    private void setupRecyclerView(RecyclerView.Adapter recyclerViewAdapter) {
        final RecyclerView feedRecyclerView = ButterKnife.findById(view, R.id.feed_recycler_view);
        feedRecyclerView.setHasFixedSize(true);
        feedRecyclerView.setAdapter(recyclerViewAdapter);
        llm = new LinearLayoutManager(getActivity());
        feedRecyclerView.setLayoutManager(llm);
    }

    private void setupAddButton() {
        addButton = (FloatingActionButton) view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (type == ALARM_TYPE) {
                    showTimeDialog();
                } else {
                    showEntryDialog();
                }
            }
        });
    }

    public void showEntryDialog() {
        View promptView = LayoutInflater.from(getActivity()).inflate(R.layout.prompt_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(promptView);
        final EditText userInput = (EditText) promptView
                .findViewById(R.id.editTextDialogUserInput);
        final Switch publicSwitch = (Switch) promptView.findViewById(R.id.publicSwitch);

        builder.setMessage("What do you remember?")
               .setTitle("Dream Journal");
        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                final Date date = new Date();
                final DreamEntry dreamEntry = new DreamEntry(userInput.getText().toString(), date, publicSwitch.isChecked());
                addNewDreamEntry(dreamEntry);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.blue_grey_800);
    }

    private void showTimeDialog() {
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog
                .OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                setAlarm(hourOfDay, minute);
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }

    private void setAlarm(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        Intent myIntent = new Intent(getActivity(), AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, myIntent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void addNewDreamEntry(DreamEntry dreamEntry) {
        FeedRecyclerViewAdapter feedRecyclerViewAdapter = (FeedRecyclerViewAdapter) recyclerViewAdapter;
        feedRecyclerViewAdapter.addItem(dreamEntry, 0);
        llm.scrollToPosition(0);
    }

    private ArrayList<Entry> mockDreamEntries() {
        ArrayList<Entry> dreamEntries = new ArrayList<>();
//        dreamEntries.add(new DreamEntry("Hi", Calendar.getInstance().getTime(), true));
//        dreamEntries.add(new DreamEntry("Hey this a dream aaslkdfjlask jdf;laksjdf;lashf;kj sdkfljl skdljf sdfjkl skdjlflj sdfkljsdj sldfkjsdflksjf slkdjflksdfjkldf slkdfjslkdjflk slkfjdkls aslfkj;asf a;sldkfj;slkafj lskdjfkldjsflkj", Calendar.getInstance().getTime(), true));
//        dreamEntries.add(new DreamEntry("Hey, nightmare man", Calendar.getInstance().getTime(), true));
//        dreamEntries.add(new DreamEntry("Hy", Calendar.getInstance().getTime(), true));
        return dreamEntries;
    }

    private ArrayList<AlarmEntry> mockAlarmEntries() {
        ArrayList<AlarmEntry> alarmEntries = new ArrayList<>();
        alarmEntries.add(new AlarmEntry());
        alarmEntries.add(new AlarmEntry());
        return alarmEntries;
    }



}
