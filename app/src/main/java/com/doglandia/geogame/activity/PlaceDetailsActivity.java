package com.doglandia.geogame.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.doglandia.geogame.R;
import com.doglandia.geogame.model.Place;

import org.parceler.Parcels;

/**
 * Created by Thomas on 6/13/2015.
 */
public class PlaceDetailsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Place place = Parcels.unwrap(getIntent().getParcelableExtra("place"));

        setContentView(R.layout.place_details_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setTitle(place.getCity());
    }
}
