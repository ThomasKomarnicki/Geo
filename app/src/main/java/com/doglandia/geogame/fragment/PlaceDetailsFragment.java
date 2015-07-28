package com.doglandia.geogame.fragment;

import android.content.res.Configuration;
import android.graphics.Point;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.doglandia.geogame.R;
import com.doglandia.geogame.util.Util;
import com.doglandia.geogame.map.PlaceHeatMapFragment;
import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.PlaceDetails;
import com.doglandia.geogame.server.Server;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Thomas on 6/13/2015.
 */
public class PlaceDetailsFragment extends Fragment {

    private TextView cityTv;
    private TextView countryTv;
    private TextView avgTv;
    private TextView bestTv;

    private FrameLayout heatMapContainer;
    private RelativeLayout heroContainer;

    private ProgressBar progressBar;
    private ScrollView scrollView;


    private SupportMapFragment headerLiteMapFragment;

    private GoogleMap headerGoogleMap;

    private PlaceDetails placeDetails;

    private PlaceHeatMapFragment heatMapFragment;

    public interface OnPlaceDetailsLoadedListener{
        void onPlaceDetailsLoaded(PlaceDetails placeDetails);
    }

    private OnPlaceDetailsLoadedListener onPlaceDetailsLoadedListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_details_fragment,null);
        scrollView = (ScrollView) view.findViewById(R.id.place_details_fragment_scroll_view);
        progressBar = (ProgressBar) view.findViewById(R.id.place_details_fragment_progress);
        hideView();
        Log.d("PlaceDetailsFragment","onCreateView");

        cityTv = (TextView) view.findViewById(R.id.place_details_city);
        countryTv = (TextView) view.findViewById(R.id.place_details_country);
        avgTv = (TextView) view.findViewById(R.id.place_details_average);
        bestTv = (TextView) view.findViewById(R.id.place_details_best_guess);

        heatMapContainer = (FrameLayout) view.findViewById(R.id.place_details_heat_map_container);
        heroContainer = (RelativeLayout) view.findViewById(R.id.place_details_hero_container);


        heatMapFragment = (PlaceHeatMapFragment) getChildFragmentManager().findFragmentById(R.id.place_details_heat_map);

        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x;

        int height = (int) (size.x*(9f/16f));

        if(getResources().getBoolean(R.bool.show_two_pane_layout)){
            height = height/2;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width,height);
        heroContainer.setLayoutParams(params);
        LinearLayout.LayoutParams squareParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,width);
        heatMapContainer.setLayoutParams(squareParams);

        return view;
    }

    public void setOnPlaceDetailsLoadedListener(OnPlaceDetailsLoadedListener onPlaceDetailsLoadedListener){
        this.onPlaceDetailsLoadedListener = onPlaceDetailsLoadedListener;
        if(placeDetails != null){
            onPlaceDetailsLoadedListener.onPlaceDetailsLoaded(placeDetails);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("PlaceDetailsFragment","onActivityCreated");

        headerLiteMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.place_details_lite_map);
        headerLiteMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                headerGoogleMap = googleMap;
                headerGoogleMap.getUiSettings().setMapToolbarEnabled(false);
                initHeaderMap();
            }
        });

        Log.d("PlaceDetailsFragment","Activity finished create");


    }

    public void showPlaceDetails(PlaceDetails placeDetails){
        // TODO
        showView();
        this.placeDetails = placeDetails;
        initHeaderMap();

        if(placeDetails.getPlace().getState() != null){
            cityTv.setText(placeDetails.getPlace().getCity()+", "+placeDetails.getPlace().getState());
        }else {
            cityTv.setText(placeDetails.getPlace().getCity());
        }

        countryTv.setText(placeDetails.getPlace().getCountry());

        avgTv.setText(Util.getDistanceDisplay(placeDetails.getAverageDistance()));
        bestTv.setText(Util.getDistanceDisplay(placeDetails.getBestDistance()));

        initHeatMap(placeDetails);

        if(onPlaceDetailsLoadedListener != null){
            onPlaceDetailsLoadedListener.onPlaceDetailsLoaded(placeDetails);
        }
    }

    private void initHeatMap(PlaceDetails placeDetails){
        heatMapFragment.showHeat(placeDetails);
    }
    private void initHeaderMap(){
        if(placeDetails == null || headerGoogleMap == null){
            return;
        }

        Log.d("PlaceDetailsFrag","succesfully initiated header google map");

        headerGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(placeDetails.getPlace().getLatLng()));
        headerGoogleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Log.d("PlaceDetailsFrag","camera changed");
                showView();
            }
        });

    }

    public void getPlaceDetails(Place place) {
        hideView();
        Server.getInstance().getLocationDetails(place.getId(), new Callback<PlaceDetails>() {
            @Override
            public void success(PlaceDetails placeDetails, Response response) {
                if(getActivity() != null) {
                    PlaceDetailsFragment.this.placeDetails = placeDetails;
                    placeDetails.getPlace().geocode(new Geocoder(getActivity()));
                    showPlaceDetails(placeDetails);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void showView(){
        progressBar.setVisibility(View.INVISIBLE);
        scrollView.setVisibility(View.VISIBLE);
    }

    private void hideView(){
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.INVISIBLE);
    }

}
