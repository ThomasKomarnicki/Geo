package com.doglandia.geogame.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.doglandia.geogame.R;
import com.doglandia.geogame.adapter.LocateResultsAdapter;
import com.doglandia.geogame.adapter.NavigationAdapter;
import com.doglandia.geogame.fragment.error.NoDataFragment;
import com.doglandia.geogame.fragment.error.NoPlaceLocateResultsFragments;
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

    private FrameLayout contentFrame;
    private LinearLayout recentLocationsHolder;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_locations_activity);

        contentFrame = (FrameLayout) findViewById(R.id.recent_locations_content);
        progressBar = (ProgressBar) findViewById(R.id.recent_locations_progress);
        progressBar.setVisibility(View.VISIBLE);

        recentLocationsHolder = (LinearLayout) findViewById(R.id.recent_locations_holder);
        recentLocationsHolder.setVisibility(View.INVISIBLE);

        recyclerView = (RecyclerView) findViewById(R.id.recent_locations_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mapFragmentHolder = (FrameLayout) findViewById(R.id.recent_locations_map_holder);
        mapFragment = (PlaceLocateResultMapFragment) getSupportFragmentManager().findFragmentById(R.id.recent_locations_map);

        NavigationAdapter.setUpNavDrawerActivity(this,"Recent Places");

        getRecentLocations();
    }

    private void getRecentLocations(){
        Server.getInstance().getUserLocationGuesses(0, new Callback<List<PlaceLocateResult>>() {
            @Override
            public void success(List<PlaceLocateResult> placeLocateResults, Response response) {
                if(placeLocateResults == null || placeLocateResults.size() == 0){
                    getSupportFragmentManager().beginTransaction()
                            .add(contentFrame.getId(),new NoPlaceLocateResultsFragments(),"no_recent_locations_fragment")
                            .commit();
                }else {
                    recyclerView.setAdapter(new LocateResultsAdapter(placeLocateResults, RecentLocationsActivity.this));
                    recentLocationsHolder.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onLocateResultClicked(int index, PlaceLocateResult placeLocateResult) {
        mapFragment.showPlaceLocateResult(placeLocateResult);
    }
}
