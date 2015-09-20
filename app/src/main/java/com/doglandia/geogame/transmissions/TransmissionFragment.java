package com.doglandia.geogame.transmissions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.doglandia.geogame.R;
import com.doglandia.geogame.transmissions.view.TransmissionConsole;

/**
 * Extend this fragment to create series of transmissions, each transmission is started with
 * animateText
 */
public class TransmissionFragment extends Fragment implements View.OnClickListener, TransmissionTextListener {

    private static final String TRANSMISSION_TEXTS = "transmission_texts";

    public static TransmissionFragment createInstance(String... texts){
        TransmissionFragment transmissionFragment = new TransmissionFragment();
        Bundle args = new Bundle();
        args.putStringArray(TRANSMISSION_TEXTS, texts);
        transmissionFragment.setArguments(args);
        return transmissionFragment;
    }


    private TransmissionConsole transmissionConsole;
    private TransmissionTextListener transmissionEndedTextListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transmission_content,null);

        transmissionConsole = (TransmissionConsole) view.findViewById(R.id.transmission_console);


        view.setClickable(true);
        view.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getResources().getBoolean(R.bool.custom_transmission_size)){
            transmissionConsole.setLayoutParams(new FrameLayout.LayoutParams(
                    (int)getResources().getDimension(R.dimen.transmission_console_width),
                    (int)getResources().getDimension(R.dimen.transmission_console_height),
                    Gravity.CENTER));
        }

        String[] text = getArguments().getStringArray(TRANSMISSION_TEXTS);
        animateText(text);
    }

    private void animateText(String... text){
        transmissionConsole.animateText(text, this);
    }

    @Override
    public void onClick(View v) {
        // continue / end current animation
        transmissionConsole.onScreenClick();

    }

    @Override
    public void onTransmissionEnd() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
        if(transmissionEndedTextListener != null){
            transmissionEndedTextListener.onTransmissionEnd();
        }
    }

    @Override
    public void onNewTextShown(int textIndex) {
        if(transmissionEndedTextListener != null){
            transmissionEndedTextListener.onNewTextShown(textIndex);
        }
    }

    public void setTransmissionEndedTextListener(TransmissionTextListener transmissionEndedTextListener) {
        this.transmissionEndedTextListener = transmissionEndedTextListener;
    }
}
