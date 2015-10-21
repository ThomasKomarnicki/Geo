package com.doglandia.unearthed.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doglandia.unearthed.R;
import com.doglandia.unearthed.activity.MyPlacesActivity;
import com.doglandia.unearthed.adapter.MyPlacesAdapter;
import com.doglandia.unearthed.model.Place;
import com.doglandia.unearthed.util.BottomScrollListener;

import java.util.List;

public class MyPlacesFragment extends Fragment implements MyPlacesAdapter.OnPlaceClickListener {

    private RecyclerView recyclerView;

    private BottomScrollListener bottomScrollListener;
    int page = 1;

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

        bottomScrollListener = new BottomScrollListener(recyclerView, new BottomScrollListener.OnBottomScrolledListener() {
            @Override
            public void onBottomScrolled() {
                page++;
                ((MyPlacesActivity)getActivity()).getPlacesFromServer(page);
            }
        });

//        int userId = getArguments().getInt("user_id",0);
    }

    public void showPlacesList(List<Place> places) {
        initAdapter(places);
    }

    private void initAdapter(List<Place> places) {
        if(getActivity() != null) {
            if(recyclerView.getAdapter() == null) {
                MyPlacesAdapter adapter = new MyPlacesAdapter(places, getActivity(), shouldHighlight());
                adapter.setListener(MyPlacesFragment.this);
                recyclerView.setAdapter(adapter);
            }else{
                MyPlacesAdapter myPlacesAdapter = (MyPlacesAdapter) recyclerView.getAdapter();
                myPlacesAdapter.notifyDataSetChanged();
                bottomScrollListener.onNewPageLoaded();
            }
        }
    }

    @Override
    public void onPlaceClick(Place place, int position) {
        if(getActivity() == null){
            return;
        }
//        if(getResources().getBoolean(R.bool.show_two_pane_layout)) {
//            MyPlacesAdapter myPlacesAdapter = (MyPlacesAdapter) recyclerView.getAdapter();
////            myPlacesAdapter.setSelectedIndex(position);
//        }

        ((MyPlacesActivity)getActivity()).onPlaceClick(place, position);
    }

    public void highlightSelected(){
        MyPlacesAdapter myPlacesAdapter = (MyPlacesAdapter) recyclerView.getAdapter();
        if(myPlacesAdapter != null) {
            myPlacesAdapter.notifyDataSetChanged();
        }
    }

    private boolean shouldHighlight(){
        return getResources().getBoolean(R.bool.my_places_highlight_row);
    }
}