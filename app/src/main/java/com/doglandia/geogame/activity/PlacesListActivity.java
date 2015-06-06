package com.doglandia.geogame.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import com.doglandia.geogame.R;

/**
 * Created by Thomas on 6/5/2015.
 */
public class PlacesListActivity extends FragmentActivity {

    private RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_activity);
    }
}
