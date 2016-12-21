package com.example.andrewpang.dreamjournal.Holders;

import android.view.View;
import android.widget.TextView;
import com.example.andrewpang.dreamjournal.R;

public class DreamEntryHolder extends EntryHolder {

    public TextView dateTextView;
    public TextView monthTextView;
    public TextView dreamTextView;

    public DreamEntryHolder(View itemView) {
        super(itemView);
        dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
        monthTextView = (TextView) itemView.findViewById(R.id.monthTextView);
        dreamTextView = (TextView) itemView.findViewById(R.id.dreamTextView);
    }

//    @Override
//    public void onClick(View v) {
//        //cardClickListener.onItemClick(getAdapterPosition(), v);
//    }

}
