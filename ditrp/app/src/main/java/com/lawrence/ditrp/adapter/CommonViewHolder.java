package com.lawrence.ditrp.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.lawrence.ditrp.R;

class CommonViewHolder extends RecyclerView.ViewHolder {

    CardView mCardView;
    TextView mTitle;
    TextView mDescription;
    ImageView mLogo;

    CommonViewHolder(View itemView) {
        super(itemView);
        mCardView = itemView.findViewById(R.id.cardView);
        mTitle = itemView.findViewById(R.id.title);
        mDescription = itemView.findViewById(R.id.description);
        mLogo = itemView.findViewById(R.id.imageView);
    }
}
