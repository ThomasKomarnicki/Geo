package com.doglandia.geogame.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.doglandia.geogame.R;
import com.doglandia.geogame.UserAuth;
import com.doglandia.geogame.adapter.NavigationAdapter;
import com.doglandia.geogame.fragment.ProfileStatsFragment;

/**
 * Created by Thomas on 9/6/2015.
 */
public class ProfileStatsActivity extends CalligraphyActivity {

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
}
