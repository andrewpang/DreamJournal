package com.example.andrewpang.dreamjournal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FeedFragment();
            case 1:
                return new FeedFragment();
            case 2:
                return new AlarmsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
