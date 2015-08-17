package com.doglandia.geogame.transmissions.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.doglandia.geogame.R;

/**
 * Created by Thomas on 8/16/2015.
 */
public class TransmissionConsole extends LinearLayout {

    private TransmissionText transmissionText;
    private ContinueText continueText;

    public TransmissionConsole(Context context) {
        super(context);
        init();
    }

    public TransmissionConsole(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TransmissionConsole(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
//        setBackground(getResources().getDrawable(R.drawable.transmission_console_background));
//        transmissionText = new TransmissionText(getContext());
//        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.setMargins(0,0,0, (int) (getResources().getDisplayMetrics().density * 20));
//        addView(transmissionText, params);
//
//        continueText = new ContinueText(getContext());
//        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        addView();


    }

}
