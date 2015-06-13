package com.doglandia.geogame.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doglandia.geogame.R;
import com.doglandia.geogame.adapter.MyPlacesAdapter;
import com.doglandia.geogame.model.Place;
import com.doglandia.geogame.server.Server;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Thomas on 6/13/2015.
 */
public class MyPlacesFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_places_fragment,null);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_places_recycler_view);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int userId = getArguments().getInt("user_id",0);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getPlaces(userId);
    }

    private void getPlaces(int userId) {
        Server.getInstance().getUserLocations(userId, new Callback<List<Place>>() {
            @Override
            public void success(List<Place> places, Response response) {
                recyclerView.setAdapter(new MyPlacesAdapter(places));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
