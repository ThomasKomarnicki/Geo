package com.doglandia.geogame.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.doglandia.geogame.R;

/**
 * Created by Thomas on 9/6/2015.
 */
public class ProfileStatsLocationView extends FrameLayout {

    private TextView cityText;
    private TextView countryText;
    private TextView averageDistanceText;
    private FrameLayout mapFrame;

    public ProfileStatsLocationView(Context context) {
        super(context);
        init();
    }

    public ProfileStatsLocationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProfileStatsLocationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.profile_stats_location_view,null);

        cityText = (TextView) view.findViewById(R.id.profile_stats_location_city);
        countryText = (TextView) view.findViewById(R.id.profile_stats_location_country);
        averageDistanceText = (TextView) view.findViewById(R.id.profile_stats_location_average_distance);
        mapFrame = (FrameLayout) view.findViewById(R.id.profile_stats_location_map_frame);

        addView(view);
    }

    public void setCity(String city){
        this.cityText.setText(city);
    }

    public void setCountry(String country){
        this.countryText.setText(country);
    }

    public void setDistanceText(String formattedDistanceText){
        this.averageDistanceText.setText(formattedDistanceText);
    }
}
