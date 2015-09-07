package com.doglandia.geogame.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.doglandia.geogame.R;
import com.doglandia.geogame.UserAuth;
import com.doglandia.geogame.adapter.NavigationAdapter;
import com.doglandia.geogame.fragment.ProfileStatsFragment;

/**
 * Created by Thomas on 9/6/2015.
 */
public class ProfileStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_stats);
        NavigationAdapter.setUpNavDrawerActivity(this, "Agent Statistics");

        int userId = UserAuth.getAuthUserId();
        String authToken = UserAuth.getAuthUserToken();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.profile_Stats_fragment_container, ProfileStatsFragment.getInstance(userId,authToken))
                .commit();
    }
}
