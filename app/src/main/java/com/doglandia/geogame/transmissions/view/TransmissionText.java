package com.doglandia.geogame.transmissions.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Thomas on 8/16/2015.
 */
public class TransmissionText extends TextView {

    public interface OnTransmissionTextAnimationEnd {
        void onTransmissionEnded();
    }


    public TransmissionText(Context context) {
        super(context);
        init();
    }

    public TransmissionText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TransmissionText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setText("");
    }
}
