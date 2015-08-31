package com.doglandia.geogame.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by Thomas on 8/8/2015.
 */
public class BottomScrollListener {

    public interface OnBottomScrolledListener{
        void onBottomScrolled();
    }

    private int pastVisibleItems;
    private int visibleItemCount;
    private int totalItemCount;

    private LinearLayoutManager layoutManager;
    private boolean loading = true;

    private OnBottomScrolledListener onBottomScrolledListener;

    public BottomScrollListener(RecyclerView recyclerView, final OnBottomScrolledListener onBottomScrolledListener) {
        this.onBottomScrolledListener = onBottomScrolledListener;

        layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount >= 20) {
                        if ((visibleItemCount + pastVisibleItems) >= (totalItemCount - 8)) {
                            loading = false;
                            onBottomScrolledListener.onBottomScrolled();
                            Log.d("BottomScrollListener", "Last Item");
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void onNewPageLoaded(){
        loading = true;
    }

}
