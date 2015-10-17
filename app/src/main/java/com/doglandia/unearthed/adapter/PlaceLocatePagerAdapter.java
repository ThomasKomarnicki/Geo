package com.doglandia.unearthed.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.doglandia.unearthed.map.LocatingMapFragment;
import com.doglandia.unearthed.map.StreetViewMapFragment;

/**
 * Created by Thomas on 6/3/2015.
 */
public class PlaceLocatePagerAdapter extends FragmentPagerAdapter {

    private StreetViewMapFragment streetViewMapFragment;
    private LocatingMapFragment locatingMapFragment;

    public PlaceLocatePagerAdapter(FragmentManager fm) {
        super(fm);

    }



    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = streetViewMapFragment;
                break;
            case 1:
                fragment = locatingMapFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public void setStreetViewMapFragment(StreetViewMapFragment streetViewMapFragment) {
        this.streetViewMapFragment = streetViewMapFragment;
    }

    public void setLocatingMapFragment(LocatingMapFragment locatingMapFragment) {
        this.locatingMapFragment = locatingMapFragment;
    }
}
