package com.example.andrewpang.dreamjournal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedRecyclerViewAdapter.DreamEntryHolder> {

    private ArrayList<DreamEntry> dataSet;
    private static CardClickListener cardClickListener;

    public interface CardClickListener {
        void onItemClick(int position, View v);
    }

    public static class DreamEntryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView dateTextView;
        TextView dreamTextView;

        public DreamEntryHolder(View itemView) {
            super(itemView);
            dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
            dreamTextView = (TextView) itemView.findViewById(R.id.dreamTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //cardClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public static void setCardClickListener(CardClickListener cardClickListener) {
        FeedRecyclerViewAdapter.cardClickListener = cardClickListener;
    }

    public FeedRecyclerViewAdapter(ArrayList<DreamEntry> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public DreamEntryHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.card_view_row, parent, false);

        DreamEntryHolder DreamEntryHolder = new DreamEntryHolder(view);
        return DreamEntryHolder;
    }

    @Override
    public void onBindViewHolder(DreamEntryHolder holder, int position) {
        holder.dateTextView.setText(dataSet.get(position).getDateDay());
        holder.dreamTextView.setText(dataSet.get(position).getEntry());
    }

    public void addItem(DreamEntry dataObj, int index) {
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
