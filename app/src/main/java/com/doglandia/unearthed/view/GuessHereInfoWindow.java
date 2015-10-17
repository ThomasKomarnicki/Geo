package com.doglandia.unearthed.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.doglandia.unearthed.R;

/**
 * Created by Thomas on 8/16/2015.
 */
public class GuessHereInfoWindow extends FrameLayout {
    public GuessHereInfoWindow(Context context) {
        super(context);
        init();
    }

    public GuessHereInfoWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GuessHereInfoWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        addView(LayoutInflater.from(getContext()).inflate(R.layout.guess_here_info_window,null));
    }
}
