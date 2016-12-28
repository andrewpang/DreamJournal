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
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

public class FeedFragment extends Fragment {

    private static final int ALARM_TYPE = 2;

    private static final String TAG = "FEED_FRAGMENT";
    private View view;
    private int type;
    private RecyclerView.Adapter recyclerViewAdapter;
    private FloatingActionButton addButton;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private int hour, minute;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private LinearLayoutManager llm;
    private ValueEventListener valueEventListener;
    private FeedRecyclerViewAdapter feedRecyclerViewAdapter;

    public FeedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_feed, container, false);
        alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        type = getArguments().getInt("type");
        recyclerViewAdapter = new FeedRecyclerViewAdapter(type, mockDreamEntries());
        setupRecyclerView(recyclerViewAdapter);
        setupAddButton();
        if(type != ALARM_TYPE){
            setupDreamEntryValueEventListener();
        }

        //getDreamEntries();

        return view;
    }

    private void getDreamEntries() {
        mDatabase.child("DreamEntries").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, dataSnapshot.toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
               // Log.d(TAG, dataSnapshot.toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setupDreamEntryValueEventListener() {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for(DataSnapshot entrySnapshot: dataSnapshot.getChildren()){
                    final String entry = (String) entrySnapshot.child("entry").getValue();
                    final boolean isPublic = (Boolean) entrySnapshot.child("public").getValue();
                    final long dateSince1970 = (long) entrySnapshot.child("dateSince1970").getValue();
                    final DreamEntry dreamEntry = new DreamEntry(entry, isPublic, dateSince1970);

                    addEntryView(dreamEntry);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.child("DreamEntries").child(user.getUid()).addListenerForSingleValueEvent(valueEventListener);
    }

    private void addEntryView(DreamEntry dreamEntry){
        feedRecyclerViewAdapter = (FeedRecyclerViewAdapter) recyclerViewAdapter;
        feedRecyclerViewAdapter.addItem(dreamEntry, 0);
        llm.scrollToPosition(0);
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
                final DreamEntry dreamEntry = new DreamEntry(userInput.getText().toString(), publicSwitch.isChecked(), date);
                postNewDreamEntry(dreamEntry);
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

    private void postNewDreamEntry(DreamEntry dreamEntry) {
        addEntryView(dreamEntry);

        mDatabase.child(user.getUid()).push().setValue(dreamEntry);
        if (dreamEntry.isPublic()) {
            mDatabase.child("publicFeed").push().child(user.getUid()).setValue(dreamEntry);
        }
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
