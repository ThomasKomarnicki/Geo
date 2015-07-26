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
import com.doglandia.geogame.activity.DiscoverActivity;
import com.doglandia.geogame.activity.MyPlacesActivity;
import com.doglandia.geogame.activity.PlaceLocateActivity;
import com.doglandia.geogame.activity.PlaceLocateActivityNewUi;
import com.doglandia.geogame.activity.RecentLocationsActivity;

/**
 * Created by Thomas on 6/10/2015.
 */
public class NavigationAdapter {

    private NavigationView navigationView;
    private AppCompatActivity activity;

    public NavigationAdapter(final AppCompatActivity activity){
        this.activity = activity;
        initNavigation();
    }

    public void initNavigation(){
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
                if(!activityName.equals(PlaceLocateActivityNewUi.class.getSimpleName())){
                    startPlaceLocateActivity();
                }
                break;
            case R.id.navigation_sub_item_2:
                if(!activityName.equals(DiscoverActivity.class.getSimpleName())){
                    startDiscoverActivity();
                }
                break;
            case R.id.navigation_sub_item_3:
                if(!activityName.equals(RecentLocationsActivity.class.getSimpleName())){
                    startRecentLocationsActivity();
                }
                break;
            case R.id.navigation_sub_item_4:
                if(!activityName.equals(MyPlacesActivity.class.getSimpleName())) {
                    startMyPlacesActivity();
                }
                break;
        }
    }

    private void startPlaceLocateActivity(){
        Intent intent = new Intent(activity,PlaceLocateActivityNewUi.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void startRecentLocationsActivity(){
        Intent intent = new Intent(activity,RecentLocationsActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void startMyPlacesActivity(){
        Intent intent = new Intent(activity, MyPlacesActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void startDiscoverActivity(){
        Intent intent = new Intent(activity, DiscoverActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void setUpNavDrawerActivity(AppCompatActivity activity, String title){
        final DrawerLayout navDrawer = (DrawerLayout) activity.findViewById(R.id.main_nav_drawer);

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.recent_locations_toolbar);

        toolbar.setTitle(title);

        activity.setSupportActionBar(toolbar);

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
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);

        new NavigationAdapter(activity);
    }
}
