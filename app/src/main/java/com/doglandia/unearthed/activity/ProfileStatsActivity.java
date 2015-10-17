package com.doglandia.unearthed.activity;

import android.os.Bundle;

import com.doglandia.unearthed.R;
import com.doglandia.unearthed.UserAuth;
import com.doglandia.unearthed.adapter.NavigationAdapter;
import com.doglandia.unearthed.fragment.ProfileStatsFragment;
import com.doglandia.unearthed.fragment.error.ButtonLessNoDataFragment;
import com.doglandia.unearthed.util.ConnectionErrorHandler;

/**
 * Created by Thomas on 9/6/2015.
 */
public class ProfileStatsActivity extends CalligraphyActivity implements ConnectionErrorHandler {

    private NavigationAdapter navigationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_stats);
        navigationAdapter = NavigationAdapter.setUpNavDrawerActivity(this, "Agent Statistics");

        int userId = UserAuth.getAuthUserId();
        String authToken = UserAuth.getAuthUserToken();

        if(getSupportFragmentManager().findFragmentByTag("profile_stats_fragment") == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.profile_stats_fragment_container, ProfileStatsFragment.getInstance(userId, authToken),"profile_stats_fragment")
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if(!navigationAdapter.onBackPressed()){
            super.onBackPressed();
        }
    }

    @Override
    public void onConnectionFailed() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.error_content, new ButtonLessNoDataFragment())
                .commit();
    }
}
