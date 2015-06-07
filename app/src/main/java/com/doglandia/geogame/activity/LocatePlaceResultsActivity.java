package com.doglandia.geogame.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;

import com.doglandia.geogame.R;
import com.doglandia.geogame.map.PlaceLocateResultMapFragment;
import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.PlaceLocateResult;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Thomas on 6/6/2015.
 */
public class LocatePlaceResultsActivity extends FragmentActivity {

    private static final String RESULT_MAP_FRAGMENT_TAG = "result_map_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locate_place_results);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setTitle("Results");


        LatLng guessedLocation = getIntent().getParcelableExtra("guessed_location");
        addMapFragment(guessedLocation);



    }

    private void addMapFragment(LatLng guessedLocation) {
        Bundle args = new Bundle();
        args.putParcelable("guessed_location", guessedLocation);
        PlaceLocateResultMapFragment fragment = new PlaceLocateResultMapFragment();
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.locate_place_results_fragment_holder, fragment,RESULT_MAP_FRAGMENT_TAG).commit();
    }

    private void onPlaceDataReceived(PlaceLocateResult placeLocateResult){
        PlaceLocateResultMapFragment fragment = (PlaceLocateResultMapFragment) getSupportFragmentManager().findFragmentByTag(RESULT_MAP_FRAGMENT_TAG);
        fragment.showBothLocations(placeLocateResult.getActualLocation());
    }
}
