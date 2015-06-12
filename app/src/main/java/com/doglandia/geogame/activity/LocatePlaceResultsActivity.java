package com.doglandia.geogame.activity;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.doglandia.geogame.R;
import com.doglandia.geogame.map.PlaceLocateResultMapFragment;
import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.PlaceLocateResult;
import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Thomas on 6/6/2015.
 */
public class LocatePlaceResultsActivity extends AppCompatActivity {

    private static final String RESULT_MAP_FRAGMENT_TAG = "result_map_fragment";

    private TextView countryTv;
    private TextView stateTv;
    private TextView cityTv;
    private TextView distanceTv;

    private PlaceLocateResult placeLocateResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locate_place_results);

        countryTv = (TextView) findViewById(R.id.results_country);
        stateTv = (TextView) findViewById(R.id.results_state);
        cityTv = (TextView) findViewById(R.id.results_city);
        distanceTv = (TextView) findViewById(R.id.results_distance);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setTitle("Results");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });

        placeLocateResult = Parcels.unwrap(getIntent().getParcelableExtra("locate_result"));

        addMapFragment();

        placeLocateResult.getActualLocation().geocode(new Geocoder(this,Locale.getDefault()));

        showLocationDetails(placeLocateResult.getActualLocation());

    }

    private void addMapFragment() {
        Bundle args = new Bundle();
        args.putParcelable("locate_result", getIntent().getParcelableExtra("locate_result"));
        PlaceLocateResultMapFragment fragment = new PlaceLocateResultMapFragment();
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.locate_place_results_fragment_holder, fragment,RESULT_MAP_FRAGMENT_TAG).commit();
    }

    private void showLocationDetails(Place place) {
        countryTv.setText(place.getCountry());
        cityTv.setText(place.getCity() + ",");
        if(place.getLocality() != null) {
            stateTv.setText(place.getLocality());
        }

        distanceTv.setText(getDistanceText(placeLocateResult));

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    private static String getDistanceText(PlaceLocateResult placeLocateResult) {
        return "distance: "+placeLocateResult.getDistanceString();
    }
}