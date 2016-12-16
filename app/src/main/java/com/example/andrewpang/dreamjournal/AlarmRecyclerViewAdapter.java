package com.example.andrewpang.dreamjournal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.AlarmEntryHolder> {

    private ArrayList<AlarmEntry> dataSet;
    private static CardClickListener cardClickListener;

    public interface CardClickListener {
        void onItemClick(int position, View v);
    }

    public static class AlarmEntryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView dateTextView;
        TextView dreamTextView;

        public AlarmEntryHolder(View itemView) {
            super(itemView);
//            dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
//            dreamTextView = (TextView) itemView.findViewById(R.id.dreamTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //cardClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public static void setCardClickListener(CardClickListener cardClickListener) {
        AlarmRecyclerViewAdapter.cardClickListener = cardClickListener;
    }

    public AlarmRecyclerViewAdapter(ArrayList<AlarmEntry> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public AlarmEntryHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.alarm_card_view_row, parent, false);

        AlarmEntryHolder AlarmEntryHolder = new AlarmEntryHolder(view);
        return AlarmEntryHolder;
    }

    @Override
    public void onBindViewHolder(AlarmEntryHolder holder, int position) {
//        holder.dateTextView.setText(dataSet.get(position).getDateDay());
//        holder.dreamTextView.setText(dataSet.get(position).getEntry());
    }

    public void addItem(AlarmEntry dataObj, int index) {
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
