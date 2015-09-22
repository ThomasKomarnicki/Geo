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
import com.doglandia.geogame.util.ConnectionErrorHandler;
import com.doglandia.geogame.util.Util;
import com.doglandia.geogame.view.ProfileStatsLocationView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import org.parceler.Parcels;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Thomas on 9/6/2015.
 */
public class ProfileStatsFragment extends Fragment {

    private static final int HARDEST_LOCATION_MAP_FRAME_ID = 0X69;
    private static final int EASIEST_LOCATION_MAP_FRAME_ID = 0X70;
    private static final int BEST_GUESS_LOCATION_MAP_FRAME_ID = 0X71;

    private static final float MAP_FRAGMENT_ZOOM = 17f;

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

    private TextView hardestLocationTitle;
    private TextView easiestLocationTitle;
    private TextView closestLocationGuessTitle;

    private ProgressBar progressBar;

    private View contentView;

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

        hardestLocationTitle = (TextView) view.findViewById(R.id.profile_stats_hardest_location_title);
        easiestLocationTitle = (TextView) view.findViewById(R.id.profile_stats_easiest_location_title);
        closestLocationGuessTitle = (TextView) view.findViewById(R.id.profile_stats_closest_location_guess_title);

        progressBar = (ProgressBar) view.findViewById(R.id.profile_stats_progress);

        contentView = view.findViewById(R.id.profile_stats_content_view);
        if(contentView == null){
            contentView = view.findViewById(R.id.profile_stats_content_scroll_view);
        }
        contentView.setVisibility(View.GONE);

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
                    ProfileStatsFragment.this.userProfileStats = userProfileStats;
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
                    ((ConnectionErrorHandler)getActivity()).onConnectionFailed();
                }
            });
        }


    }

    private void attachProfileStatsData(final UserProfileStats userProfileStats){
        level.setText(String.valueOf(userProfileStats.getLevel()));
        locationsGuessed.setText(String.valueOf(userProfileStats.getLocationGuessCount()));
        locationsDiscovered.setText(String.valueOf(userProfileStats.getLocationCount()));


        if(userProfileStats.getHardestLocation() != null) {
            hardestLocation.setCity(userProfileStats.getHardestLocation().getCity());
            hardestLocation.setCountry(userProfileStats.getHardestLocation().getCountry());
            hardestLocation.setDistanceText("Average Distance: " + Util.getDistanceDisplay(userProfileStats.getHardestLocationAverage()));

            SupportMapFragment supportMapFragment = createLiteModeMapFragment(hardestLocation.setMapFrameId(HARDEST_LOCATION_MAP_FRAME_ID));
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    googleMap.getUiSettings().setMapToolbarEnabled(false);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userProfileStats.getHardestLocation().getLatLng(),MAP_FRAGMENT_ZOOM));
                }
            });
        }else{
            hardestLocation.setVisibility(View.GONE);
            hardestLocationTitle.setVisibility(View.GONE);
        }

        if(userProfileStats.getEasiestLocation() != null) {
            easiestLocation.setCity(userProfileStats.getEasiestLocation().getCity());
            easiestLocation.setCountry(userProfileStats.getEasiestLocation().getCountry());
            easiestLocation.setDistanceText("Average Distance: " + Util.getDistanceDisplay(userProfileStats.getEasiestLocationAverage()));

            SupportMapFragment supportMapFragment = createLiteModeMapFragment(easiestLocation.setMapFrameId(EASIEST_LOCATION_MAP_FRAME_ID));
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    googleMap.getUiSettings().setMapToolbarEnabled(false);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userProfileStats.getEasiestLocation().getLatLng(), MAP_FRAGMENT_ZOOM));
                }
            });
        }else{
            easiestLocation.setVisibility(View.GONE);
            easiestLocationTitle.setVisibility(View.GONE);
        }

        if(userProfileStats.getBestGuessLocation() != null) {
            closestLocationGuess.setCity(userProfileStats.getBestGuessLocation().getCity());
            closestLocationGuess.setCountry(userProfileStats.getBestGuessLocation().getCountry());
            closestLocationGuess.setDistanceText("Best Target Distance: " + Util.getDistanceDisplay(userProfileStats.getBestGuess()));

            SupportMapFragment supportMapFragment = createLiteModeMapFragment(closestLocationGuess.setMapFrameId(BEST_GUESS_LOCATION_MAP_FRAME_ID));
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    googleMap.getUiSettings().setMapToolbarEnabled(false);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userProfileStats.getBestGuessLocation().getLatLng(), MAP_FRAGMENT_ZOOM));
                }
            });
        }else{
            closestLocationGuess.setVisibility(View.GONE);
            closestLocationGuessTitle.setVisibility(View.GONE);
        }

        progressBar.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }


    private SupportMapFragment createLiteModeMapFragment(int containerId){
        GoogleMapOptions options = new GoogleMapOptions()
                .mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .liteMode(true);

        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance(options);
        getChildFragmentManager().beginTransaction().add(containerId,supportMapFragment).commit();

        return supportMapFragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(userProfileStats != null){
            outState.putParcelable("user_profile_stats",Parcels.wrap(userProfileStats));
        }
    }


}
