package com.doglandia.geogame.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.doglandia.geogame.R;
import com.doglandia.geogame.fragment.HeatDialogMapFragment;
import com.doglandia.geogame.fragment.PlaceDetailsFragment;
import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.PlaceDetails;
import com.doglandia.geogame.server.Server;

import org.parceler.Parcels;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Thomas on 6/13/2015.
 */
public class PlaceDetailsActivity extends AppCompatActivity implements OnHeatMapClickedListener, View.OnClickListener{

    private PlaceDetailsFragment placeDetailsFragment;

    private FrameLayout heatMapHolder;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Place place = Parcels.unwrap(getIntent().getParcelableExtra("place"));

        setContentView(R.layout.place_details_activity);

        heatMapHolder = (FrameLayout) findViewById(R.id.heat_map_holder);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setTitle(place.getCity());
        toolbar.setNavigationOnClickListener(this);

        placeDetailsFragment = (PlaceDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.place_etails_fragment);


        placeDetailsFragment.getPlaceDetails(place);
    }


    @Override
    public boolean onHeatMapClicked() {

        HeatDialogMapFragment heatDialogMapFragment = new HeatDialogMapFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(heatMapHolder.getId(),heatDialogMapFragment,"heat_dialog_map_fragment")
                .addToBackStack("heat_dialog_map_fragment")
                .commit();

        return false;
    }

    @Override
    public void onClick(View v) {
//        if(heatMapHolder.getChildCount() == 0) {
//            finish();
//        }else {
//            getSupportFragmentManager().popBackStack();
//        }
        onBackPressed();
    }
}
