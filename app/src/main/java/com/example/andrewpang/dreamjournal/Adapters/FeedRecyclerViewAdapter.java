package com.example.andrewpang.dreamjournal.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.andrewpang.dreamjournal.Holders.AlarmEntryHolder;
import com.example.andrewpang.dreamjournal.Entries.DreamEntry;
import com.example.andrewpang.dreamjournal.Holders.DreamEntryHolder;
import com.example.andrewpang.dreamjournal.Entries.Entry;
import com.example.andrewpang.dreamjournal.Holders.EntryHolder;
import com.example.andrewpang.dreamjournal.R;

import java.util.ArrayList;

public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<EntryHolder> {

    private ArrayList<Entry> dataSet;
    private static CardClickListener cardClickListener;
    private EntryHolder entryHolder;
    private int type;
    private static final int ALARM_TYPE = 2;
    private View view;

    public interface CardClickListener {
        void onItemClick(int position, View v);
    }

    public static void setCardClickListener(CardClickListener cardClickListener) {
        FeedRecyclerViewAdapter.cardClickListener = cardClickListener;
    }

    public FeedRecyclerViewAdapter(int type, ArrayList<Entry> dataSet) {
        this.type = type;
        this.dataSet = dataSet;
    }

    @Override
    public EntryHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        final int layoutId = getLayoutIdFromType();
        view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        entryHolder = createEntryHolderFromType();
        return entryHolder;
    }

    private int getLayoutIdFromType() {
        switch (type) {
            case ALARM_TYPE:
                return R.layout.alarm_card_view_row;
            default:
                return R.layout.card_view_row;
        }
    }

    private EntryHolder createEntryHolderFromType() {
        switch (type) {
            case ALARM_TYPE:
                return new AlarmEntryHolder(view);
            default:
                return new DreamEntryHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(EntryHolder holder, int position) {
        if (entryHolder instanceof DreamEntryHolder){
            final DreamEntryHolder dreamEntryHolder = (DreamEntryHolder) entryHolder;
            final DreamEntry dreamEntry = (DreamEntry) dataSet.get(position);

            dreamEntryHolder.dateTextView.setText(dreamEntry.getDateDay());
            dreamEntryHolder.monthTextView.setText(dreamEntry.getDateMonth());
            dreamEntryHolder.dreamTextView.setText(dreamEntry.getEntry());
        }

    }

    public void addItem(Entry dataObj, int index) {
        dataSet.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        dataSet.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
