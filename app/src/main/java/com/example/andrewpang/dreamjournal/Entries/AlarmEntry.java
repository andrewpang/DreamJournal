package com.example.andrewpang.dreamjournal.Entries;

public class AlarmEntry {

    private int hour, minute;

    public AlarmEntry(){
    }

    public AlarmEntry(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

}
