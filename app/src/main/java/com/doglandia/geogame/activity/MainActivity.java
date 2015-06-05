package com.doglandia.geogame.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.doglandia.geogame.R;
import com.doglandia.geogame.adapter.MainPagerAdapter;
import com.doglandia.geogame.adapter.NavDrawerRecyclerAdapter;

public class MainActivity extends FragmentActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private RecyclerView mNavDrawerList;

    private MainPagerAdapter mMainPagerAdapter;

    private DrawerLayout mNavDrawer;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavDrawerList = (RecyclerView) findViewById(R.id.nav_drawer_recycler);
        mNavDrawerList.setLayoutManager(new LinearLayoutManager(this));
        mNavDrawerList.setAdapter(new NavDrawerRecyclerAdapter());

        mNavDrawer = (DrawerLayout) findViewById(R.id.main_nav_drawer);

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);

        mViewPager = (ViewPager) findViewById(R.id.main_pager);
        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);

        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMainPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addTab(mTabLayout.newTab().setText("Explore"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Locate"));
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mToolbar.setTitle("GeoGame");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNavDrawer.isDrawerOpen(GravityCompat.START)){
                    mNavDrawer.closeDrawers();
                }else{
                    mNavDrawer.openDrawer(GravityCompat.START);
                }
            }
        });

        mTabLayout.setOnTabSelectedListener(this);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition(),true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}