package com.doglandia.geogame.adapter;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.doglandia.geogame.R;
import com.doglandia.geogame.activity.PlaceLocateActivity;
import com.doglandia.geogame.activity.RecentLocationsActivity;

/**
 * Created by Thomas on 6/10/2015.
 */
public class NavigationAdapter {

    private NavigationView navigationView;
    private AppCompatActivity activity;

    public NavigationAdapter(final AppCompatActivity activity){
        this.activity = activity;
        navigationView = (NavigationView) activity.findViewById(R.id.main_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                NavigationAdapter.this.onNavigationItemSelected(menuItem.getItemId(), activity.getLocalClassName());

                return true; // I think this is because we consumed it?
            }

        });
    }

    public void onNavigationItemSelected(int itemId, String activityName){
        switch (itemId){
            case R.id.navigation_sub_item_1:
                if(activityName != PlaceLocateActivity.class.getSimpleName()){
                    startPlaceLocateActivity();
                }
                break;
            case R.id.navigation_sub_item_2:
                startRecentLocationsActivity();
                break;
            case R.id.navigation_sub_item_3:

                break;
            case R.id.navigation_sub_item_4:

                break;
        }
    }

    private void startPlaceLocateActivity(){
        Intent intent = new Intent(activity,PlaceLocateActivity.class);
        activity.startActivity(intent);
    }

    private void startRecentLocationsActivity(){
        Intent intent = new Intent(activity,RecentLocationsActivity.class);
        activity.startActivity(intent);
    }

    public static void setUpNavDrawerActivity(AppCompatActivity activity){
        final DrawerLayout navDrawer = (DrawerLayout) activity.findViewById(R.id.main_nav_drawer);

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.recent_locations_toolbar);

        toolbar.setTitle("GeoGame");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navDrawer.isDrawerOpen(GravityCompat.START)) {
                    navDrawer.closeDrawers();
                } else {
                    navDrawer.openDrawer(GravityCompat.START);
                }
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);

        activity.setSupportActionBar(toolbar);

        new NavigationAdapter(activity);
    }
}
