package com.doglandia.geogame.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doglandia.geogame.R;
import com.doglandia.geogame.model.PlaceLocateResult;

import java.util.List;

/**
 * Created by Thomas on 6/10/2015.
 */
public class LocateResultsAdapter extends RecyclerView.Adapter<LocateResultsAdapter.ViewHolder> {

    public interface LocateResultClickListener {
        void onLocateResultClicked(int index, PlaceLocateResult placeLocateResult);
    }

    private LocateResultClickListener listener;

    private List<PlaceLocateResult> placeLocateResults;
    public LocateResultsAdapter(List<PlaceLocateResult> placeLocateResultList, LocateResultClickListener locateResultClickListener) {
        this.listener = locateResultClickListener;
        this.placeLocateResults = placeLocateResultList;
    }

    @Override
    public LocateResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.locate_result_row,parent,false));
    }

    @Override
    public void onBindViewHolder(LocateResultsAdapter.ViewHolder holder, final int position) {
        final PlaceLocateResult placeLocateResult = placeLocateResults.get(position);

        holder.locationCityTv.setText(placeLocateResult.getActualLocation().getCity());
        holder.locationCountryTv.setText(placeLocateResult.getActualLocation().getCountry());
        holder.distanceTv.setText(placeLocateResult.getDistanceString());
        holder.scoreTv.setText("Score: "+String.valueOf(placeLocateResult.getScore()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLocateResultClicked(position,placeLocateResult);
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeLocateResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView locationCityTv;
        TextView locationCountryTv;
        TextView distanceTv;
        TextView scoreTv;

        public ViewHolder(View row) {
            super(row);
            locationCityTv = (TextView) row.findViewById(R.id.locate_result_city);
            locationCountryTv = (TextView) row.findViewById(R.id.locate_result_country);
            distanceTv = (TextView) row.findViewById(R.id.locate_result_distance);
            scoreTv = (TextView) row.findViewById(R.id.locate_result_score);

        }
    }
}