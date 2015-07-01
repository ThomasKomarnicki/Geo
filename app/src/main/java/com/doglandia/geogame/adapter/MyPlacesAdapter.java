package com.doglandia.geogame.adapter;

import android.content.Context;
import android.content.res.TypedArray;
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

    public interface OnPlaceClickListener{
        void onPlaceClick(Place place, int position);
    }

    private List<Place> places;

    private OnPlaceClickListener listener;

    private View selectedView;

    private int backgroundResourceId;

    public MyPlacesAdapter(List<Place> places,Context context){
        this.places = places;

        TypedArray a = context.getTheme().obtainStyledAttributes(R.style.AppTheme, new int[] {R.attr.selectableItemBackground});
        backgroundResourceId = a.getResourceId(0, 0);
    }

    public void setListener(OnPlaceClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_places_row,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Place place = places.get(position);
        if(place.getState() != null){
            holder.cityTv.setText(place.getCity()+", "+place.getState());
        }else {
            holder.cityTv.setText(place.getCity());
        }
        holder.countryTv.setText(place.getCountry());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == selectedView){
                    return; // do nothing
                }

                if(selectedView != null) { // first runm
                    selectedView.setBackgroundResource(backgroundResourceId);
                }
                selectedView = view;
                selectedView.setBackgroundColor(view.getContext().getResources().getColor(android.R.color.holo_green_dark));

                if(listener != null){
                    listener.onPlaceClick(place, position);
                }

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
