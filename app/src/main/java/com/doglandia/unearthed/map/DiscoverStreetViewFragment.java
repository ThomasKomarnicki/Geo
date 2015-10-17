package com.doglandia.unearthed.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.doglandia.unearthed.R;
import com.doglandia.unearthed.activity.DiscoverActivity;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

public class DiscoverStreetViewFragment extends SupportStreetViewPanoramaFragment implements
        OnStreetViewPanoramaReadyCallback, StreetViewPanorama.OnStreetViewPanoramaChangeListener {

    private StreetViewPanorama streetViewPanorama;
    private LatLng currentLocation;

    private Button addLocationButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discover_street_view_fragment,null);
        addLocationButton = (Button) view.findViewById(R.id.discover_street_view_add_button);
        ((FrameLayout)view.findViewById(R.id.discover_street_view_map_container)).addView(super.onCreateView(inflater, container, savedInstanceState));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getStreetViewPanoramaAsync(this);
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DiscoverActivity)getActivity()).onLocationAdded(currentLocation);
            }
        });
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        this.streetViewPanorama = streetViewPanorama;
        streetViewPanorama.setStreetNamesEnabled(false);
        setLocation(currentLocation);
        streetViewPanorama.setOnStreetViewPanoramaChangeListener(this);
    }

    public void setLocation(LatLng latLng){
        if(latLng != null){
            this.currentLocation = latLng;
        }

        if(currentLocation == null || streetViewPanorama == null){
            return;
        }

        Log.d(getClass().getSimpleName(),"set streetViewPanorama Position");
        streetViewPanorama.setPosition(latLng,1000);
    }

    @Override
    public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {
        Log.d(getClass().getSimpleName(),"onStreetViewPanoramaChange "+streetViewPanoramaLocation);
        if(streetViewPanoramaLocation == null){
            Log.d(getClass().getSimpleName(),"no location found");
        }else{
            currentLocation = streetViewPanoramaLocation.position;
        }
        ((DiscoverActivity)getActivity()).onLocationChangeResult(streetViewPanoramaLocation);

    }

    public void setVisible(boolean visible){
        if(visible){
            addLocationButton.setVisibility(View.VISIBLE);
        }else{
            addLocationButton.setVisibility(View.GONE);
        }


    }


}
