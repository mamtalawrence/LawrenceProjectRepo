package com.lawrence.ditrp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.dataModel.DashBoardData;

import java.util.Collections;
import java.util.List;

public class DashBoardAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    private List<DashBoardData> list = Collections.emptyList();
    private Context context;

    public DashBoardAdapter(List<DashBoardData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_row_layout, parent, false);
        return new CommonViewHolder(v);

    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.mTitle.setText(list.get(position).title);
        holder.mDescription.setText(list.get(position).description);
        holder.mLogo.setImageResource(list.get(position).imageId);

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
