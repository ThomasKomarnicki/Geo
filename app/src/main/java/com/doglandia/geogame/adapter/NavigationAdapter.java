package com.doglandia.geogame.adapter;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import com.doglandia.geogame.activity.PlaceLocateActivityNewUi;
import com.doglandia.geogame.activity.RecentLocationsActivity;
import com.doglandia.geogame.activity.locate.PlaceLocateBaseActivity;

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
                boolean result = NavigationAdapter.this.onNavigationItemSelected(menuItem.getItemId(), activity);
                if(!result){
                    DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.main_nav_drawer);
                    drawerLayout.closeDrawers();
                }
                return true; // I think this is because we consumed it?
            }

        });
    }

    public boolean onNavigationItemSelected(int itemId, AppCompatActivity activity){
        switch (itemId){
            case R.id.navigation_sub_item_1:
                if(!activity.getClass().getSuperclass().equals(PlaceLocateBaseActivity.class)) {
                    startPlaceLocateActivity();
                    return true;
                }
                break;
            case R.id.navigation_sub_item_2:
                if(!(activity instanceof DiscoverActivity)){
                    startDiscoverActivity();
                    return true;
                }
                break;
            case R.id.navigation_sub_item_3:
                if(!(activity instanceof RecentLocationsActivity)){
                    startRecentLocationsActivity();
                    return true;
                }
                break;
            case R.id.navigation_sub_item_4:
                if(!(activity instanceof MyPlacesActivity)) {
                    startMyPlacesActivity();
                    return true;
                }
                break;
            case R.id.navigation_sub_item_5:
                if(!(activity instanceof MyPlacesActivity)) {
                    startMyPlacesActivity();
                    return true;
                }
        }
        return false;
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
        Drawable drawable = activity.getResources().getDrawable(R.drawable.ic_menu_black_24dp);
        drawable.setColorFilter(activity.getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        toolbar.setNavigationIcon(drawable);

        new NavigationAdapter(activity);
    }
}
