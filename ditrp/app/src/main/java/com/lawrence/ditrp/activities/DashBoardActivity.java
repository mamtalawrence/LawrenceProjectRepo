package com.lawrence.ditrp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.adapter.CustomRVItemTouchListener;
import com.lawrence.ditrp.adapter.DashBoardAdapter;
import com.lawrence.ditrp.dataModel.DashBoardData;
import com.lawrence.ditrp.listener.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class DashBoardActivity extends AppCompatActivity {

    RecyclerView mDashBoardRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Utils.setCustomActionBar(this, null);

        // Set View with adapter
        mDashBoardRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        DashBoardAdapter adapter = new DashBoardAdapter(setData(), getApplication());
        mDashBoardRecyclerView.setAdapter(adapter);
        mDashBoardRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDashBoardRecyclerView.addOnItemTouchListener(new CustomRVItemTouchListener(this, mDashBoardRecyclerView,
                new RecyclerViewItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        switch (position) {
                            case 0:
                                break;
                            case 1:
                                Intent intent = new Intent(DashBoardActivity.this, PracticeSessionActivity.class);
                                startActivity(intent);
                                break;
                            case 2:
                                Intent intentExam = new Intent(DashBoardActivity.this, ExamPracticeActivity.class);
                                startActivity(intentExam);
                                break;
                            case 3:
                                Intent intentSetting = new Intent(DashBoardActivity.this, SettingActivity.class);
                                startActivity(intentSetting);
                                break;
                        }
                        //Add click action
                        Toast.makeText(DashBoardActivity.this, "click " + position, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        Toast.makeText(DashBoardActivity.this, "long click " + position, Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    /**
     * Set Data in array
     *
     * @return data array
     */
    private List<DashBoardData> setData() {
        List<DashBoardData> dashBoardData = new ArrayList<>();
        dashBoardData.add(new DashBoardData("Question Bank", "List of questions & answers and meaning of road sign ",
                R.drawable.book_bank));
        dashBoardData.add(new DashBoardData("Practice", "Test your knowledge without worrying about time ", R
                .drawable.practics));
        dashBoardData.add(new DashBoardData("Exam", "Time and question bound test exactly same as exam", R.drawable
                .exam));
        dashBoardData.add(new DashBoardData("Setting & Help", "Language selection, forms, information and more", R
                .drawable.setting));
        return dashBoardData;
    }
}
