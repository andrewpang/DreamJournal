package com.example.andrewpang.dreamjournal.Entries;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@IgnoreExtraProperties
public class DreamEntry extends Entry {
    private String entry;
    private boolean isPublic;
    private long dateSince1970;
    private Date date;


    public DreamEntry() {
    }

    public DreamEntry(String entry, boolean isPublic, Date date) {
        this.entry = entry;
        this.date = date;
        this.isPublic = isPublic;
        setDateSince1970();
    }

    public DreamEntry(String entry, boolean isPublic, long dateSince1970) {
        this.entry = entry;
        this.isPublic = isPublic;
        this.dateSince1970 = dateSince1970;
        this.date = new Date(dateSince1970);
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public long getDateSince1970() {
        return dateSince1970;
    }

    public void setDateSince1970() {
        this.dateSince1970 = CalendarUtil.getTimeSince1970FromDate(this.date);
    }

    @Exclude
    public Date getDate() {
        return date;
    }

    @Exclude
    public String getDateDay() {
        return CalendarUtil.getDayNumberFromDate(this.date);
    }

    @Exclude
    public String getDateMonth() {
        return CalendarUtil.getMonthFromDate(this.date);
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
