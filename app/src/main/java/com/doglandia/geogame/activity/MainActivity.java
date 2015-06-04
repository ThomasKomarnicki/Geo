package com.doglandia.geogame.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.doglandia.geogame.R;
import com.doglandia.geogame.adapter.MainPagerAdapter;
import com.doglandia.geogame.adapter.NavDrawerRecyclerAdapter;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private RecyclerView mNavDrawerList;

    private MainPagerAdapter mMainPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavDrawerList = (RecyclerView) findViewById(R.id.nav_drawer_recycler);
        mNavDrawerList.setAdapter(new NavDrawerRecyclerAdapter());

        mViewPager = (ViewPager) findViewById(R.id.main_pager);
        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        mMainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMainPagerAdapter);

        TabLayout.Tab tab = mTabLayout.newTab().setText("Explore");
        mTabLayout.addTab(tab);
        tab = mTabLayout.newTab().setText("Locate");
        mTabLayout.addTab(tab);

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