package com.doglandia.geogame.navigation;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

/**
 * Created by Thomas on 6/5/2015.
 */
public class NavigationItemManager implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    public NavigationItemManager(Context context) {
        this.context = context;
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }
}
