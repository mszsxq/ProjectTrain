package com.example.carepet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;

import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTabHost fragmentTabHost = findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this,
                getSupportFragmentManager(),
                android.R.id.tabcontent);

        TabHost.TabSpec tabSpec1 = fragmentTabHost.newTabSpec("社区").setIndicator("社区");
        fragmentTabHost.addTab(tabSpec1,
                CommunityFragment.class,
                null);
        TabHost.TabSpec tabSpec2 = fragmentTabHost.newTabSpec("add").setIndicator("+");
        fragmentTabHost.addTab(tabSpec2,
                AddFragment.class,
                null);
        TabHost.TabSpec tabSpec3 = fragmentTabHost.newTabSpec("地图").setIndicator("地图");
        fragmentTabHost.addTab(tabSpec3,
                MapFragment.class,
                null);

        fragmentTabHost.setCurrentTab(0);
    }

}
