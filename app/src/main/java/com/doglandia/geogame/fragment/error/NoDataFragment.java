package com.doglandia.geogame.fragment.error;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.doglandia.geogame.R;

/**
 * Created by Thomas on 6/25/2015.
 */
public abstract class NoDataFragment extends Fragment implements View.OnClickListener{

    private Button startNowButton;

    private TextView text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.no_data_fragment,null);

        text = (TextView) view.findViewById(R.id.no_data_text);

        startNowButton = (Button) view.findViewById(R.id.no_data_action_button);

        text.setText(getMessageText());
        startNowButton.setText(getButtonText());

        startNowButton.setOnClickListener(this);

        return view;

    }

    public abstract String getMessageText();
    public abstract String getButtonText();
}
