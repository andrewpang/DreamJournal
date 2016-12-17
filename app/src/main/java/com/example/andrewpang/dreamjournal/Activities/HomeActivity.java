package com.example.andrewpang.dreamjournal.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.andrewpang.dreamjournal.R;
import com.example.andrewpang.dreamjournal.Adapters.ViewPagerAdapter;

public class HomeActivity extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;

    TabLayout.Tab personalFeed;
    TabLayout.Tab publicFeed;
    TabLayout.Tab alarmsTab;

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setupTabLayout();
    }

    private void setupTabLayout() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        createTabs();
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void createTabs() {
        personalFeed = tabs.newTab();
        publicFeed = tabs.newTab();
        alarmsTab = tabs.newTab();

        personalFeed.setText("My Dreams");
        publicFeed.setText("Dream Feed");
        alarmsTab.setText("Alarms");

        tabs.addTab(personalFeed, 0);
        tabs.addTab(publicFeed, 1);
        tabs.addTab(alarmsTab, 2);

        tabs.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
        tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.blue_grey_200));
    }


}
