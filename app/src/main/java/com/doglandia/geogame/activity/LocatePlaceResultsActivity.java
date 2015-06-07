package com.doglandia.geogame.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.doglandia.geogame.R;
import com.doglandia.geogame.map.PlaceLocateResultMapFragment;
import com.doglandia.geogame.model.PlaceLocateResult;

import org.parceler.Parcels;

/**
 * Created by Thomas on 6/6/2015.
 */
public class LocatePlaceResultsActivity extends FragmentActivity {

    private static final String RESULT_MAP_FRAGMENT_TAG = "result_map_fragment";

    private PlaceLocateResult placeLocateResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locate_place_results);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setTitle("Results");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        placeLocateResult = Parcels.unwrap(getIntent().getParcelableExtra("locate_result"));

        addMapFragment();
    }

    private void addMapFragment() {
        Bundle args = new Bundle();
        args.putParcelable("locate_result", getIntent().getParcelableExtra("locate_result"));
        PlaceLocateResultMapFragment fragment = new PlaceLocateResultMapFragment();
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.locate_place_results_fragment_holder, fragment,RESULT_MAP_FRAGMENT_TAG).commit();
    }
}