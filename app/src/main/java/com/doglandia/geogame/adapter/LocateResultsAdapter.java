package com.doglandia.geogame.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doglandia.geogame.R;
import com.doglandia.server.model.PlaceLocateResult;

import java.util.List;

/**
 * Created by Thomas on 6/10/2015.
 */
public class LocateResultsAdapter extends RecyclerView.Adapter<LocateResultsAdapter.ViewHolder> {

    private List<PlaceLocateResult> placeLocateResults;
    public LocateResultsAdapter(List<PlaceLocateResult> placeLocateResultList) {
        this.placeLocateResults = placeLocateResultList;
    }

    @Override
    public LocateResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.locate_result_row,parent,false));
    }

    @Override
    public void onBindViewHolder(LocateResultsAdapter.ViewHolder holder, int position) {
        PlaceLocateResult placeLocateResult = placeLocateResults.get(position);
    }

    @Override
    public int getItemCount() {
        return placeLocateResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView distanceTv;
        TextView locationCityTv;
        TextView locationCountryTv;
        TextView scoreTv;


        public ViewHolder(View row) {
            super(row);

        }
    }
}
