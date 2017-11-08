package com.lawrence.ditrp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.dataModel.DashBoardData;

import java.util.Collections;
import java.util.List;

/**
 * Created by mamta.lawrence on 11/8/2017.
 */

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardViewHolder> {

    List<DashBoardData> list = Collections.emptyList();
    Context context;

    public DashBoardAdapter(List<DashBoardData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public DashBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        DashBoardViewHolder holder = new DashBoardViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(DashBoardViewHolder holder, int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.mTitle.setText(list.get(position).title);
        holder.mDescription.setText(list.get(position).description);
        holder.mLogo.setImageResource(list.get(position).imageId);

        //animate(holder);

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
    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, DashBoardData dashBoardData) {
        list.add(position, dashBoardData);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified DashBoardData object
    public void remove(DashBoardData dashBoardData) {
        int position = list.indexOf(dashBoardData);
        list.remove(position);
        notifyItemRemoved(position);
    }

}
