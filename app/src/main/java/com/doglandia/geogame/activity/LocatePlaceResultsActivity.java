package com.doglandia.geogame.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.doglandia.geogame.R;
import com.doglandia.geogame.map.PlaceLocateResultMapFragment;
import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.PlaceLocateResult;
import com.doglandia.geogame.server.GeoCodeTask;

import org.parceler.Parcels;

public class LocatePlaceResultsActivity extends CalligraphyActivity {

    public static final String FROM_RECENT_LOCATIONS = "from_recent_locations";

    private static final String RESULT_MAP_FRAGMENT_TAG = "result_map_fragment";

    private TextView countryTv;
    private TextView stateTv;
    private TextView cityTv;
    private TextView distanceTv;
    private TextView scoreTv;
    private View resultsHolder;

    private Button nextLocationButton;

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    private PlaceLocateResult placeLocateResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locate_place_results);

        countryTv = (TextView) findViewById(R.id.results_country);
        stateTv = (TextView) findViewById(R.id.results_state);
        cityTv = (TextView) findViewById(R.id.results_city);
        distanceTv = (TextView) findViewById(R.id.result_distance_value);
        scoreTv = (TextView) findViewById(R.id.result_score_value);
        nextLocationButton = (Button) findViewById(R.id.next_location_button);
        resultsHolder = findViewById(R.id.results_holder);
        resultsHolder.setVisibility(View.INVISIBLE);

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        boolean fromRecentLocations = getIntent().getBooleanExtra(FROM_RECENT_LOCATIONS,true);
        if(!fromRecentLocations){
            nextLocationButton.setVisibility(View.INVISIBLE);
            toolbar.setTitle("Locate Result");
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }else{
            appBarLayout.setVisibility(View.GONE);
        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        toolbar.setTitle("Results");
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setResult(Activity.RESULT_OK);
//                finish();
//            }
//        });

        placeLocateResult = Parcels.unwrap(getIntent().getParcelableExtra("locate_result"));

        addMapFragment();

        GeoCodeTask geoCodeTask = new GeoCodeTask(this){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                showLocationDetails(placeLocateResult);
            }
        };
        geoCodeTask.execute(placeLocateResult.getActualLocation());

//        placeLocateResult.getActualLocation().geocode(new Geocoder(this, Locale.getDefault()));



        nextLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void addMapFragment() {
        Bundle args = new Bundle();
        args.putParcelable("locate_result", getIntent().getParcelableExtra("locate_result"));
        PlaceLocateResultMapFragment fragment = new PlaceLocateResultMapFragment();
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.locate_place_results_fragment_holder, fragment,RESULT_MAP_FRAGMENT_TAG).commit();
    }

    private void showLocationDetails(PlaceLocateResult placeLocateResult) {
        Place place = placeLocateResult.getActualLocation();
        countryTv.setText(place.getCountry());
        cityTv.setText(place.getCity());
        if(place.getState() != null) {
            stateTv.setText(place.getState());
        }else{
            stateTv.setText("");
        }

        distanceTv.setText(getDistanceText(placeLocateResult));
        scoreTv.setText(String.valueOf(placeLocateResult.getScore()));
        resultsHolder.setVisibility(View.VISIBLE);

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    private static String getDistanceText(PlaceLocateResult placeLocateResult) {
        return placeLocateResult.getDistanceString();
    }
}