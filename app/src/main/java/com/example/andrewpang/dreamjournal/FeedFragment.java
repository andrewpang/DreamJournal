package com.example.andrewpang.dreamjournal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.Calendar;

public class FeedFragment extends Fragment {

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        final RecyclerView feedRecyclerView = ButterKnife.findById(rootView, R.id.feed_recycler_view);
        feedRecyclerView.setHasFixedSize(true);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(mockDreamEntries());
        feedRecyclerView.setAdapter(recyclerViewAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        feedRecyclerView.setLayoutManager(llm);

        return rootView;
    }

    private ArrayList<DreamEntry> mockDreamEntries() {
        ArrayList<DreamEntry> dreamEntries = new ArrayList<>();
        dreamEntries.add(new DreamEntry("Hey", Calendar.getInstance().getTime(), true));
        dreamEntries.add(new DreamEntry("Hey this a dream aaslkdfjlask jdf;laksjdf;lashf;kj sdkfljl skdljf sdfjkl skdjlflj sdfkljsdj sldfkjsdflksjf slkdjflksdfjkldf slkdfjslkdjflk slkfjdkls aslfkj;asf a;sldkfj;slkafj lskdjfkldjsflkj", Calendar.getInstance().getTime(), true));
        dreamEntries.add(new DreamEntry("Hey, nightmare man", Calendar.getInstance().getTime(), true));
        dreamEntries.add(new DreamEntry("Hey", Calendar.getInstance().getTime(), true));
        return dreamEntries;
    }


}
