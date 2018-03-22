package com.lawrence.ditrp.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.lawrence.ditrp.R;

class ItemDetailsViewHolder extends RecyclerView.ViewHolder {

    CardView mCardView;
    ImageView mImageViewItem;
    TextView mViewTitle;
    TextView mViewDescription;

    ItemDetailsViewHolder(View itemView) {
        super(itemView);
        mCardView = (CardView) itemView.findViewById(R.id.cardView);
        mImageViewItem = (ImageView) itemView.findViewById(R.id.imageView_item);
        mViewTitle = (TextView) itemView.findViewById(R.id.text_view_Title);
        mViewDescription = (TextView) itemView.findViewById(R.id.text_view_description);
    }
}
