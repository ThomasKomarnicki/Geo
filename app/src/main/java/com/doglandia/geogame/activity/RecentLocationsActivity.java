package com.doglandia.geogame.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.doglandia.geogame.R;
import com.doglandia.geogame.adapter.LocateResultsAdapter;
import com.doglandia.geogame.adapter.NavigationAdapter;
import com.doglandia.geogame.map.PlaceLocateResultMapFragment;
import com.doglandia.geogame.model.PlaceLocateResult;
import com.doglandia.geogame.server.Server;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Thomas on 6/10/2015.
 */
public class RecentLocationsActivity extends AppCompatActivity implements LocateResultsAdapter.LocateResultClickListener {

    private RecyclerView recyclerView;
//    private Toolbar toolbar;
    private FrameLayout mapFragmentHolder;
    private PlaceLocateResultMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_locations_activity);

        recyclerView = (RecyclerView) findViewById(R.id.recent_locations_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mapFragmentHolder = (FrameLayout) findViewById(R.id.recent_locations_map_holder);
        mapFragment = (PlaceLocateResultMapFragment) getSupportFragmentManager().findFragmentById(R.id.recent_locations_map);

        NavigationAdapter.setUpNavDrawerActivity(this);

        getRecentLocations();
    }

    private void getRecentLocations(){
        Server.getInstance().getUserPlaceLocateResults(0, new Callback<List<PlaceLocateResult>>() {
            @Override
            public void success(List<PlaceLocateResult> placeLocateResults, Response response) {
                recyclerView.setAdapter(new LocateResultsAdapter(placeLocateResults,RecentLocationsActivity.this));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void onLocateResultClicked(int index, PlaceLocateResult placeLocateResult) {
        mapFragment.showPlaceLocateResult(placeLocateResult);
    }
}
