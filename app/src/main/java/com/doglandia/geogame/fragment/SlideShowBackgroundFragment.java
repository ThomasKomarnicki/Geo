package com.doglandia.geogame.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.doglandia.geogame.R;
import com.doglandia.geogame.model.SlideShowController;
import com.doglandia.geogame.service.AuthSlideShowBinder;
import com.doglandia.geogame.service.AuthSlideShowService;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Thomas on 9/27/2015.
 */
public class SlideShowBackgroundFragment extends Fragment implements AuthSlideShowBinder.OnImageDownloadedListener, Callback {

    private static final String TAG = "SlideShowFragment";
    private ImageView imageView;
    private ServiceConnection serviceConnection;

    private SlideShowController slideShowController;

    private boolean imageHasSource = false;

    private long activityCreatedAt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.slide_show_background_fragment,null);
        imageView = (ImageView) view.findViewById(R.id.slide_show_image_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityCreatedAt = System.currentTimeMillis();
        Picasso.with(getActivity()).load(R.drawable.earth_view_2116).into(imageView);
        slideShowController = new SlideShowController();
        attachToService();

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
                        Log.d(TAG, "slide show controller next url == " + url);
                        if (url != null) {
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
                Log.d(TAG,"service created and connected at "+(System.currentTimeMillis() - activityCreatedAt));

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
        getActivity().unbindService(serviceConnection);
        super.onDestroy();
    }

    @Override
    public void onImageDownloaded(final String url) {
//        if(!imageHasSource) {
//            imageView.post(new Runnable() {
//                @Override
//                public void run() {
//                    Picasso.with(getActivity()).load(url).into(imageView,SlideShowBackgroundFragment.this);
//                }
//            });
//        }
        slideShowController.onNewImageDownloaded(url);

    }

    @Override
    public void onSuccess() {
        Log.d(TAG,"image downloaded into imageView "+(System.currentTimeMillis() - activityCreatedAt));
        slideShowController.imageSuccessfullyDownloaded();
        imageHasSource = true;
    }

    @Override
    public void onError() {

    }
}
