package com.doglandia.geogame.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.doglandia.geogame.Animation.AnimatorFrameLayout;
import com.doglandia.geogame.R;
import com.doglandia.geogame.activity.PlaceLocateActivityNewUi;
import com.doglandia.geogame.map.LocatingMapFragment;
import com.doglandia.geogame.map.StreetViewMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Thomas on 7/23/2015.
 */
public class PlaceLocateControllerFragment extends Fragment {

    private static final String TAG = "PlaceLocateController";

//    FrameLayout streetViewContainer;
//    AnimatorFrameLayout mapContainer;
    private FrameLayout mainContent;
    private FloatingActionButton toggle;

    private LocatingMapFragment locatingMapFragment;
    private StreetViewMapFragment streetViewMapFragment;

//    private LatLng currentLatLng;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.place_locate_controller,null);

//        streetViewContainer = (FrameLayout) view.findViewById(R.id.place_locate_controller_street_container);
//        mapContainer = (AnimatorFrameLayout) view.findViewById(R.id.place_locate_controller_map_container);

        mainContent = (FrameLayout) view.findViewById(R.id.place_locate_controller_content);
        toggle = (FloatingActionButton) view.findViewById(R.id.plcate_locate_controller_toggle);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFragments();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(streetViewMapFragment == null){
            streetViewMapFragment = new StreetViewMapFragment();
        }
        if(locatingMapFragment == null){
            locatingMapFragment = new LocatingMapFragment();
        }





        getChildFragmentManager()
                .beginTransaction()
                .add(mainContent.getId(), streetViewMapFragment, "street_map_fragment")
                .commit();

//        mapContainer.setContentFragment(locatingMapFragment);
//
//        getChildFragmentManager()
//                .beginTransaction()
//                .add(streetViewContainer.getId(),streetViewMapFragment, "street_view_map_fragment")
//                .commit();
//
//        mapContainer.setAnimationListener(new AnimatorFrameLayout.AnimationListener() {
//            @Override
//            public void onShrinkStart() {
//                ((PlaceLocateActivityNewUi) getActivity()).setMenuNavigation();
//            }
//
//            @Override
//            public void onShrinkEnd() {
//
//            }
//
//            @Override
//            public void onRestoreStart() {
//                ((PlaceLocateActivityNewUi) getActivity()).setBackNavigation();
//            }
//
//            @Override
//            public void onRestoreEnd() {
////                locatingMapFragment.restoreControls();
//            }
//        });
//
//        reset();

//        mapContainer.startShrinkAnimation();

    }

    /* returns true if consumed event */
//    public boolean onBackPressed(){
//        mapContainer.startShrinkAnimation();
////        locatSnapshot(new GoogleMap.SnapshotReadyCallback() {
////
////            shotReady(Bitmap bitmap) {
////            etOverlayImage(bitmap);
////
////            }
////        });
//        return
//    }

    private void toggleFragments(){
        if(locatingMapFragment.isAdded()){
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(mainContent.getId(), streetViewMapFragment, "street_map_fragment")
                    .commit();
        }else{
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(mainContent.getId(), locatingMapFragment, "locating_map_fragment")
                    .commit();
        }
    }

    public void setPosition(LatLng latLng) {
        streetViewMapFragment.setPosition(latLng);
    }

    public void clearMap() {
        locatingMapFragment.clearMap();
    }

//    public void reset() {
//        ((PlaceLocateActivityNewUi) getActivity()).setMenuNavigation();
//        mapContainer.post(new Runnable() {
//            @Override
//            public void run() {
//                mapContainer.setScale(.35f);
//                mapContainer.setDelta(.3f);
//                mapContainer.setImageCrop(.6f);
//                mapContainer.setRespondToClick();
//                mapContainer.setOverlayDrawable(new ColorDrawable(Color.TRANSPARENT));
//                mapContainer.invalidate();
//            }
//        });
//    }
}
