package com.doglandia.geogame.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doglandia.geogame.R;
import com.doglandia.geogame.model.Place;

import java.util.List;

/**
 * Created by Thomas on 6/13/2015.
 */
public class MyPlacesAdapter extends RecyclerView.Adapter<MyPlacesAdapter.ViewHolder> {

    private List<Place> places;

    public MyPlacesAdapter(List<Place> places){
        this.places = places;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_places_row,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Place place = places.get(position);
        if(place.getState() != null){
            holder.cityTv.setText(place.getCity()+", "+place.getState());
        }else {
            holder.cityTv.setText(place.getCity());
        }
        holder.countryTv.setText(place.getCountry());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO start place details activity
            }
        });
    }


    @Override
    public int getItemCount() {
        return places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView countryTv;
        TextView cityTv;

        public ViewHolder(View itemView) {
            super(itemView);
            countryTv = (TextView) itemView.findViewById(R.id.my_places_country);
            cityTv = (TextView) itemView.findViewById(R.id.my_places_city);
        }
    }
}
