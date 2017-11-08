package com.lawrence.ditrp.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.lawrence.ditrp.R;

/**
 * Created by mamta.lawrence on 11/8/2017.
 */
public class DashBoardViewHolder extends RecyclerView.ViewHolder {

    CardView mCardView;
    TextView mTitle;
    TextView mDescription;
    ImageView mLogo;

    DashBoardViewHolder(View itemView) {
        super(itemView);
        mCardView = (CardView) itemView.findViewById(R.id.cardView);
        mTitle = (TextView) itemView.findViewById(R.id.title);
        mDescription = (TextView) itemView.findViewById(R.id.description);
        mLogo = (ImageView) itemView.findViewById(R.id.imageView);
    }
}
