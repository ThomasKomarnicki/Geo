package com.doglandia.geogame.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doglandia.geogame.R;
import com.doglandia.geogame.model.PlaceLocateResult;

import java.util.List;

public class LocateResultsAdapter extends RecyclerView.Adapter<LocateResultsAdapter.ViewHolder> {

    public interface LocateResultClickListener {
        void onLocateResultClicked(int index, PlaceLocateResult placeLocateResult);
    }

    private LocateResultClickListener listener;

    private ViewHolder highLightedView;

    private boolean highlight;

    private List<PlaceLocateResult> placeLocateResults;
    public LocateResultsAdapter(List<PlaceLocateResult> placeLocateResultList,
                                LocateResultClickListener locateResultClickListener, boolean highlight) {
        this.listener = locateResultClickListener;
        this.placeLocateResults = placeLocateResultList;
        this.highlight = highlight;
    }

    @Override
    public LocateResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.locate_result_row,parent,false));
    }

    @Override
    public void onBindViewHolder(final LocateResultsAdapter.ViewHolder holder, final int position) {
        final PlaceLocateResult placeLocateResult = placeLocateResults.get(position);

        holder.locationCityTv.setText(placeLocateResult.getActualLocation().getCity());
        holder.locationCountryTv.setText(placeLocateResult.getActualLocation().getCountry());
        holder.distanceTv.setText(placeLocateResult.getDistanceString());
        holder.scoreTv.setText("Score: "+String.valueOf(placeLocateResult.getScore()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(highlight) {
                    highLightNewView(holder);
                    listener.onLocateResultClicked(position, placeLocateResult);
                }
            }
        });
    }

    private void highLightNewView(ViewHolder newView){
        if(highLightedView != null){
//            highLightedView.locationCountryTv.setSelected(false);
//            highLightedView.locationCityTv.setSelected(false);
//            highLightedView.itemView.setBackgroundDrawable(null);
//            highLightedView.itemView.setBackground(null);
            //noinspection deprecation
            highLightedView.itemView.setBackgroundDrawable(null);
        }

        highLightedView = newView;
//        newView.locationCityTv.setSelected(true);
//        newView.locationCountryTv.setSelected(true);
        highLightedView.itemView.setBackgroundColor(newView.itemView.getResources().getColor(R.color.primary_dark));
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

    public void addPage(List<PlaceLocateResult> placeLocateResults) {
        this.placeLocateResults.addAll(placeLocateResults);
        notifyDataSetChanged();
    }

}