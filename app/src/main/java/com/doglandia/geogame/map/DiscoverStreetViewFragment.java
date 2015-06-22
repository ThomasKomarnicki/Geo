package com.doglandia.geogame.map;

import android.os.Bundle;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

/**
 * Created by Thomas on 6/21/2015.
 */
public class DiscoverStreetViewFragment extends SupportStreetViewPanoramaFragment implements
        OnStreetViewPanoramaReadyCallback, StreetViewPanorama.OnStreetViewPanoramaChangeListener {

    private StreetViewPanorama streetViewPanorama;
    private LatLng currentLocation;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getStreetViewPanoramaAsync(this);
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        this.streetViewPanorama = streetViewPanorama;
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

        streetViewPanorama.setPosition(latLng,1000);
    }

    @Override
    public void onStreetViewPanoramaChange(StreetViewPanoramaLocation streetViewPanoramaLocation) {

    }
}
