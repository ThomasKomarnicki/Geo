package com.doglandia.geogame.activity;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.doglandia.geogame.R;
import com.doglandia.geogame.adapter.NavigationAdapter;

/**
 * Created by Thomas on 6/10/2015.
 */
public class RecentLocationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_locations_activity);

        NavigationAdapter.setUpNavDrawerActivity(this);

        recyclerView = (RecyclerView) findViewById(R.id.recent_locations_recycler_view);
    }
}
