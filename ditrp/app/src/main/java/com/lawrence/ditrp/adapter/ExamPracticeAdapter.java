package com.lawrence.ditrp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.dataModel.DashBoardData;

/**
 * Created by mamta.lawrence on 11/8/2017.
 */

public class ExamPracticeAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    Context context;

    public ExamPracticeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_row_layout, parent, false);
        CommonViewHolder holder = new CommonViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.mTitle.setText(context.getString(R.string.exam_practice) + (position + 1));
        holder.mDescription.setText(R.string.exam_detail_decs);
        holder.mLogo.setImageResource(R.drawable.exam);

        //animation
        //holder.mCardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in));
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return 3;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, DashBoardData dashBoardData) {
        //list.add(position, dashBoardData);
        notifyItemInserted(position);
    }

}
