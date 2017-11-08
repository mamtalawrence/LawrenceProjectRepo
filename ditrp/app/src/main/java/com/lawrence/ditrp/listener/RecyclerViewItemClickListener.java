package com.lawrence.ditrp.listener;

import android.view.View;

/**
 * Created by mamta.lawrence on 11/8/2017.
 */
public interface RecyclerViewItemClickListener {
    public void onClick(View view, int position);

    public void onLongClick(View view, int position);
}