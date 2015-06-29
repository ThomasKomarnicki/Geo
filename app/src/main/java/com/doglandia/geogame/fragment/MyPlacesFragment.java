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
import com.doglandia.geogame.activity.MyPlacesActivity;
import com.doglandia.geogame.adapter.MyPlacesAdapter;
import com.doglandia.geogame.model.Place;

import java.util.List;

/**
 * Created by Thomas on 6/13/2015.
 */
public class MyPlacesFragment extends Fragment implements MyPlacesAdapter.OnPlaceClickListener {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_places_fragment,null);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_places_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        int userId = getArguments().getInt("user_id",0);
    }

    public void showPlacesList(List<Place> places) {
        initAdapter(places);
    }

    private void initAdapter(List<Place> places) {
        MyPlacesAdapter adapter = new MyPlacesAdapter(places,getActivity());
        adapter.setListener(MyPlacesFragment.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onPlaceClick(Place place, int position) {
        ((MyPlacesActivity)getActivity()).onPlaceClick(place,position);
    }
}
