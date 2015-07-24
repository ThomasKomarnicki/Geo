package com.doglandia.geogame.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.doglandia.geogame.R;
import com.doglandia.geogame.UserAuth;
import com.doglandia.geogame.adapter.NavigationAdapter;

/**
 * Created by Thomas on 7/23/2015.
 */
public class PlaceLocateActivityNewUi extends AppCompatActivity {

    private NavigationView mNavigationView;
    private DrawerLayout mNavDrawer;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserAuth.spinUp(this);

        mNavDrawer = (DrawerLayout) findViewById(R.id.main_nav_drawer);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);

        setSupportActionBar(mToolbar);

        mToolbar.setTitle("GeoGame");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNavDrawer.isDrawerOpen(GravityCompat.START)) {
                    mNavDrawer.closeDrawers();
                } else {
                    mNavDrawer.openDrawer(GravityCompat.START);
                }
            }
        });
        mToolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);

        new NavigationAdapter(this);
    }
}
