package com.example.andrewpang.dreamjournal.Entries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DreamEntry extends Entry {
    private String entry;
    private Date date;
    private boolean isPublic;

    public DreamEntry() {
    }

    public DreamEntry(String entry, Date date, boolean isPublic) {
        this.entry = entry;
        this.date = date;
        this.isPublic = isPublic;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public Date getDate() {
        return date;
    }

    //TODO: Convert Date
    public String getDateDay() {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        return Integer.toString(day);
    }

    public String getDateMonth() {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int month = cal.get(Calendar.MONTH);
        return new SimpleDateFormat("MMM").format(month);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

}
