package com.lawrence.ditrp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.adapter.DashBoardAdapter;
import com.lawrence.ditrp.dataModel.DashBoardData;

import java.util.ArrayList;
import java.util.List;

public class DashBoardActivity extends AppCompatActivity {

    RecyclerView mDashBoardRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        // Set View with adapter
        mDashBoardRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        DashBoardAdapter adapter = new DashBoardAdapter(setData(), getApplication());
        mDashBoardRecyclerView.setAdapter(adapter);
        mDashBoardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Animation
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        mDashBoardRecyclerView.setItemAnimator(itemAnimator);
    }

    /**
     * Set Data in array
     *
     * @return data array
     */
    private List<DashBoardData> setData() {
        List<DashBoardData> dashBoardData = new ArrayList<>();
        dashBoardData.add(new DashBoardData("Question Bank", "List of questions & answers and meaning of road sign ",
                R.drawable.app_logo));
        dashBoardData.add(new DashBoardData("Practice", "Test your knowledge without worrying about time ", R
                .drawable.app_logo));
        dashBoardData.add(new DashBoardData("Exam", "Time and question bound test exactly same as exam", R.drawable
                .app_logo));
        dashBoardData.add(new DashBoardData("Setting & Help", "Language selection, forms, information and more", R
                .drawable.app_logo));
        return dashBoardData;
    }
}
