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

/**
 * Created by Thomas on 9/27/2015.
 */
public class SlideShowBackgroundFragment extends Fragment implements AuthSlideShowBinder.OnImageDownloadedListener {

    private ImageView imageView;
    private ServiceConnection serviceConnection;

    private SlideShowController slideShowController;

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
        slideShowController = new SlideShowController();
        attachToService();
    }

    private void attachToService(){
        Intent intent = new Intent(getActivity(), AuthSlideShowService.class);
        getActivity().startService(intent);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                AuthSlideShowBinder binder = (AuthSlideShowBinder) service;
                binder.setOnImageDownloadedListener(SlideShowBackgroundFragment.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        getActivity().bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        getActivity().unbindService(serviceConnection);
        super.onDestroy();
    }

    @Override
    public void onImageDownloaded(String url) {
        slideShowController.onNewImageDownloaded(url);
    }
}
