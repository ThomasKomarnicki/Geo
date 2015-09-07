package com.doglandia.geogame.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.doglandia.geogame.R;
import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.model.UserProfileStats;
import com.doglandia.geogame.server.GeoCodeTask;
import com.doglandia.geogame.server.Server;
import com.doglandia.geogame.util.Util;
import com.doglandia.geogame.view.ProfileStatsLocationView;

import org.parceler.Parcels;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Thomas on 9/6/2015.
 */
public class ProfileStatsFragment extends Fragment {

    public static ProfileStatsFragment getInstance(int userId, String authToken){
        ProfileStatsFragment profileStatsFragment = new ProfileStatsFragment();
        Bundle args = new Bundle();
        args.putInt("user_id", userId);
        args.putString("auth_token", authToken);

        profileStatsFragment.setArguments(args);

        return profileStatsFragment;
    }

    private TextView level;
    private TextView locationsGuessed;
    private TextView locationsDiscovered;

    private ProfileStatsLocationView hardestLocation;
    private ProfileStatsLocationView easiestLocation;
    private ProfileStatsLocationView closestLocationGuess;

    private ProgressBar progressBar;

    private UserProfileStats userProfileStats;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_stats_fragment,null);

        level = (TextView) view.findViewById(R.id.profile_stats_level);
        locationsGuessed = (TextView) view.findViewById(R.id.profile_stats_targetted);
        locationsDiscovered = (TextView) view.findViewById(R.id.profile_stats_discovered);

        hardestLocation = (ProfileStatsLocationView) view.findViewById(R.id.profile_stats_hardest_location);
        easiestLocation = (ProfileStatsLocationView) view.findViewById(R.id.profile_stats_easiest_location);
        closestLocationGuess = (ProfileStatsLocationView) view.findViewById(R.id.profile_stats_closest_location_guess);

        progressBar = (ProgressBar) view.findViewById(R.id.profile_stats_progress);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null && savedInstanceState.containsKey("user_profile_stats")){
            userProfileStats = Parcels.unwrap(savedInstanceState.getParcelable("user_profile_stats"));
        }

        if(userProfileStats != null){
            attachProfileStatsData(userProfileStats);
        }else{
            Server.getInstance().getProfileStats(getArguments().getInt("user_id"), getArguments().getString("auth_token"), new Callback<UserProfileStats>() {
                @Override
                public void success(final UserProfileStats userProfileStats, Response response) {
                    if(getActivity() != null) {
                        Place[] places = new Place[3];
                        places[0] = userProfileStats.getEasiestLocation();
                        places[1] = userProfileStats.getHardestLocation();
                        places[2] = userProfileStats.getBestGuessLocation();

                        new GeoCodeTask(getActivity()){
                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                attachProfileStatsData(userProfileStats);
                            }
                        }.execute(places);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        }


    }

    private void attachProfileStatsData(UserProfileStats userProfileStats){
        progressBar.setVisibility(View.GONE);

        level.setText(String.valueOf(userProfileStats.getLevel()));
        locationsGuessed.setText(String.valueOf(userProfileStats.getLocationGuessCount()));
        locationsDiscovered.setText(String.valueOf(userProfileStats.getLocationCount()));


        if(userProfileStats.getHardestLocation() != null) {
            hardestLocation.setCity(userProfileStats.getHardestLocation().getCity());
            hardestLocation.setCountry(userProfileStats.getHardestLocation().getCountry());
            hardestLocation.setDistanceText("Average Distance: " + Util.getDistanceDisplay(userProfileStats.getHardestLocationAverage()));
        }

        if(userProfileStats.getEasiestLocation() != null) {
            easiestLocation.setCity(userProfileStats.getEasiestLocation().getCity());
            easiestLocation.setCountry(userProfileStats.getEasiestLocation().getCountry());
            easiestLocation.setDistanceText("Average Distance: " + Util.getDistanceDisplay(userProfileStats.getEasiestLocationAverage()));
        }

        if(userProfileStats.getBestGuessLocation() != null) {
            hardestLocation.setCity(userProfileStats.getBestGuessLocation().getCity());
            hardestLocation.setCountry(userProfileStats.getBestGuessLocation().getCountry());
            hardestLocation.setDistanceText("Best Target Distance: " + Util.getDistanceDisplay(userProfileStats.getBestGuess()));
        }
    }
}
