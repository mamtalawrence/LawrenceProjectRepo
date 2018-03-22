package com.lawrence.ditrp.listener;

import android.view.View;

public interface RecyclerViewItemClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}