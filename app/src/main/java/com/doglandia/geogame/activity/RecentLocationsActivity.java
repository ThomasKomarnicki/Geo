package com.doglandia.geogame.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.doglandia.geogame.R;
import com.doglandia.geogame.UserAuth;
import com.doglandia.geogame.fragment.error.ButtonLessNoDataFragment;
import com.doglandia.geogame.server.GeoCodeRecentLocationTask;
import com.doglandia.geogame.util.BottomScrollListener;
import com.doglandia.geogame.adapter.LocateResultsAdapter;
import com.doglandia.geogame.adapter.NavigationAdapter;
import com.doglandia.geogame.fragment.error.NoPlaceLocateResultsFragments;
import com.doglandia.geogame.model.PlaceLocateResult;
import com.doglandia.geogame.server.Server;

import org.parceler.Parcels;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Thomas on 6/10/2015.
 */
public class RecentLocationsActivity extends CalligraphyActivity implements LocateResultsAdapter.LocateResultClickListener {

    private static final String PLACE_LOCATE_RESULTS = "place_locate_results";

    private RecyclerView recyclerView;
//    private Toolbar toolbar;
//    private FrameLayout mapFragmentHolder;
//    private PlaceLocateResultMapFragment mapFragment;

    private FrameLayout contentFrame;
    private LinearLayout recentLocationsHolder;
    private ProgressBar progressBar;

    private int page = 1;

    private BottomScrollListener bottomScrollListener;

    private List<PlaceLocateResult> placeLocateResults;

    private NavigationAdapter navigationAdapter;

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
//        mapFragmentHolder = (FrameLayout) findViewById(R.id.recent_locations_map_holder);

        navigationAdapter = NavigationAdapter.setUpNavDrawerActivity(this, "Recent Places");

        if(savedInstanceState != null && savedInstanceState.containsKey(PLACE_LOCATE_RESULTS)){
            placeLocateResults = Parcels.unwrap(savedInstanceState.getParcelable(PLACE_LOCATE_RESULTS));
            showContent();
        }else {
            getRecentLocations(1);
        }

        bottomScrollListener = new BottomScrollListener(recyclerView, new BottomScrollListener.OnBottomScrolledListener() {
            @Override
            public void onBottomScrolled() {
                page++;
                getRecentLocations(page);
            }
        });


    }

    private void showContent(){
        progressBar.setVisibility(View.GONE);
        recentLocationsHolder.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(new LocateResultsAdapter(placeLocateResults, RecentLocationsActivity.this, true));
    }

    private void getRecentLocations(final int page){
        Server.getInstance().getUserLocationGuesses(UserAuth.getAuthUserId(),page, new Callback<List<PlaceLocateResult>>() {
            @Override
            public void success(final List<PlaceLocateResult> placeLocateResults, Response response) {
                if(page == 1 && (placeLocateResults == null || placeLocateResults.size() == 0)){
                    getSupportFragmentManager().beginTransaction()
                            .add(contentFrame.getId(),new NoPlaceLocateResultsFragments(),"no_recent_locations_fragment")
                            .commit();
                    progressBar.setVisibility(View.GONE);
                }else {
                    if(RecentLocationsActivity.this.placeLocateResults == null){
                        RecentLocationsActivity.this.placeLocateResults = placeLocateResults;
                    }else{
                        RecentLocationsActivity.this.placeLocateResults.addAll(placeLocateResults);
                    }

                    GeoCodeRecentLocationTask geoCodeTask = new GeoCodeRecentLocationTask(RecentLocationsActivity.this) {
                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            progressBar.setVisibility(View.GONE);

                            if (recyclerView.getAdapter() == null) {
//                                recyclerView.setAdapter(new LocateResultsAdapter(placeLocateResults, RecentLocationsActivity.this, true));
                                showContent();
                            } else {
                                LocateResultsAdapter locateResultsAdapter = (LocateResultsAdapter) recyclerView.getAdapter();
                                locateResultsAdapter.notifyDataSetChanged();
//                                locateResultsAdapter.addPage(placeLocateResults);
                            }
                            recentLocationsHolder.setVisibility(View.VISIBLE);

                        }
                    };
                    geoCodeTask.execute(placeLocateResults);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.GONE);
                // TODO show could not connect to server, with retry button maybe?
                recentLocationsHolder.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.error_content, new ButtonLessNoDataFragment())
                        .commit();
            }
        });
    }

    @Override
    public void onLocateResultClicked(int index, PlaceLocateResult placeLocateResult) {
//        mapFragment.showPlaceLocateResult(placeLocateResult);
        Intent intent = new Intent(this,LocatePlaceResultsActivity.class);
        intent.putExtra("locate_result",Parcels.wrap(placeLocateResult));
        intent.putExtra(LocatePlaceResultsActivity.FROM_RECENT_LOCATIONS,false);
        startActivity(intent);
        // todo
    }

    private boolean shouldHighlight(){
        return getResources().getBoolean(R.bool.my_places_highlight_row);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(placeLocateResults != null) {
            outState.putParcelable(PLACE_LOCATE_RESULTS, Parcels.wrap(placeLocateResults));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if(!navigationAdapter.onBackPressed()){
            super.onBackPressed();
        }
    }
}
