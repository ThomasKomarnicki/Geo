package com.doglandia.geogame.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.doglandia.geogame.R;
import com.doglandia.geogame.adapter.NavigationAdapter;

/**
 * Created by Thomas on 9/6/2015.
 */
public class ProfileStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_stats);
        NavigationAdapter.setUpNavDrawerActivity(this, "Agent Statistics");

    }
}
