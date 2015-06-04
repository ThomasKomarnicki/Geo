package com.doglandia.geogame.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.doglandia.geogame.map.LocatingMapFragment;
import com.doglandia.geogame.map.StreetViewMapFragment;

/**
 * Created by Thomas on 6/3/2015.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new StreetViewMapFragment();
                break;
            case 1:
                fragment = new LocatingMapFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
