package com.lawrence.ditrp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.dataModel.ItemsLibrary;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class ItemDetailsAdapter extends RecyclerView.Adapter<ItemDetailsViewHolder> {

    List<ItemsLibrary> list = Collections.emptyList();
    Context context;

    public ItemDetailsAdapter(List<ItemsLibrary> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ItemDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_bank_row_layout, parent, false);
        return new ItemDetailsViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ItemDetailsViewHolder holder, int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        String imageUrl = list.get(position).getImage();
        if(!TextUtils.isEmpty(imageUrl)) {
            Picasso.with(context)
                    .load(imageUrl)
                    .into(holder.mImageViewItem);
        }else{
            holder.mImageViewItem.setVisibility(View.GONE);
            Picasso.with(context)
                    .load(android.R.drawable.ic_menu_gallery)
                    .into(holder.mImageViewItem);
        }
        String title = list.get(position).getQuestionTitle();
        String description = list.get(position).getDescription();
        holder.mViewTitle.setText(String.format("Title: %s", title));
        holder.mViewDescription.setText(String.format("Description: %s", description));
        //animation
        holder.mCardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in));
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
