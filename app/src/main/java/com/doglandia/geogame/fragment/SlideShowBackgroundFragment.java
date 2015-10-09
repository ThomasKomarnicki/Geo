package com.doglandia.geogame.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.doglandia.geogame.R;
import com.doglandia.geogame.model.SlideShowController;
import com.doglandia.geogame.service.AuthSlideShowBinder;
import com.doglandia.geogame.service.AuthSlideShowService;
import com.doglandia.geogame.view.FadingImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Thomas on 9/27/2015.
 */
public class SlideShowBackgroundFragment extends Fragment implements AuthSlideShowBinder.OnImageDownloadedListener, Callback {

    private static final String TAG = "SlideShowFragment";
    private static final String CURRENT_URL = "current_url";
//    private static final String SLIDE_SHOW_CONTROLLER_INDEX = "slide_show_controller_index";
//    private ImageView imageView;
    private FadingImageView fadingImageView;
    private ServiceConnection serviceConnection;

    private SlideShowController slideShowController;

    private boolean imageLoopStarted = false;

    private long activityCreatedAt;

    private String currentUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.slide_show_background_fragment,null);
//        imageView = (ImageView) view.findViewById(R.id.slide_show_image_view);
        fadingImageView = (FadingImageView) view.findViewById(R.id.slide_show_fading_image_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityCreatedAt = System.currentTimeMillis();
        if(savedInstanceState != null && savedInstanceState.containsKey(CURRENT_URL)){
            currentUrl = savedInstanceState.getString(CURRENT_URL);
            Picasso.with(getActivity()).load(currentUrl).into(fadingImageView.getVisibleImageView());
        }else {
            Picasso.with(getActivity()).load(R.drawable.earth_view_2116).into(fadingImageView.getVisibleImageView());
        }
        slideShowController = new SlideShowController();
        slideShowController.restoreState(savedInstanceState);
        if(slideShowController.shouldDownloadImages(getActivity())) {
            attachToService();
        }


    }

    private void startImageLooping(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(slideShowController.isRunning()){
                    try {
                        Thread.sleep(slideShowController.getChangeDelay());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }

                    if(!slideShowController.isAwaitingImage()) {
                        final String url = slideShowController.getNextImageUrl();
//                        Log.d(TAG, "slide show controller next url == " + url);
                        if (url != null) {
                            currentUrl = url;
                            final ImageView imageView = fadingImageView.getHiddenImageView();
                            if(imageView != null) {
                                imageView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Picasso.with(getActivity()).load(url).into(imageView, SlideShowBackgroundFragment.this);
                                    }
                                });
                            }

                        }
                    }
                }
            }
        }).start();
    }

    private void attachToService(){
        Intent intent = new Intent(getActivity(), AuthSlideShowService.class);
        getActivity().startService(intent);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                AuthSlideShowBinder binder = (AuthSlideShowBinder) service;
                binder.setOnImageDownloadedListener(SlideShowBackgroundFragment.this);
//                Log.d(TAG,"service created and connected at "+(System.currentTimeMillis() - activityCreatedAt));

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        getActivity().bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        slideShowController.finish();
        if(serviceConnection != null) {
            getActivity().unbindService(serviceConnection);
        }
        super.onDestroy();
    }

    @Override
    public void onImageDownloaded(final String url) {
//        if(!imageLoopStarted) {
//            imageView.post(new Runnable() {
//                @Override
//                public void run() {
//                    Picasso.with(getActivity()).load(url).into(imageView,SlideShowBackgroundFragment.this);
//                }
//            });
//        }
        slideShowController.onNewImageDownloaded(url);
        if(!imageLoopStarted){
            imageLoopStarted = true;
            startImageLooping();
        }

    }

    @Override
    public void onSuccess() {
//        Log.d(TAG, "image downloaded into imageView " + (System.currentTimeMillis() - activityCreatedAt));
        slideShowController.imageSuccessfullyDownloaded();
        fadingImageView.fadeInNewImage();

    }

    @Override
    public void onError() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(currentUrl != null){
            slideShowController.saveState(outState);
            outState.putString(CURRENT_URL,currentUrl);
        }
        super.onSaveInstanceState(outState);
    }
}
