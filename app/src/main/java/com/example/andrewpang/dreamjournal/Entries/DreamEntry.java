package com.example.andrewpang.dreamjournal.Entries;

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
        return "08";
    }

    public String getDateMonth() {
        return "Nov";
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
