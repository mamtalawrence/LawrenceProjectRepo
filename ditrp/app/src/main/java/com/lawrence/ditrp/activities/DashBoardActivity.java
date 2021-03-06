package com.lawrence.ditrp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.listener.CustomRVItemTouchListener;
import com.lawrence.ditrp.adapter.DashBoardAdapter;
import com.lawrence.ditrp.dataModel.DashBoardData;
import com.lawrence.ditrp.listener.RecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class DashBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_recycler_view_layout);
        Utils.setCustomActionBar(this, getString(R.string.app_name), false, false);
        initializeView();
    }

    /**
     * Set View with adapter
     */
    private void initializeView() {
        RecyclerView dashBoardRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        DashBoardAdapter adapter = new DashBoardAdapter(setData(), getApplication());
        dashBoardRecyclerView.setAdapter(adapter);
        dashBoardRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        dashBoardRecyclerView.addOnItemTouchListener(new CustomRVItemTouchListener(this, dashBoardRecyclerView,
                new RecyclerViewItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent intent = null;
                        switch (position) {
                            case 0:
                                intent = new Intent(DashBoardActivity.this, QuestionBankActivity.class);
                                break;
                            case 1:
                                intent = new Intent(DashBoardActivity.this, PracticeSessionListActivity.class);
                                break;
                            case 2:
                                intent = new Intent(DashBoardActivity.this, ExamPracticeListActivity.class);
                                break;
                            case 3:
                                intent = new Intent(DashBoardActivity.this, SettingActivity.class);
                                break;
                        }
                        startActivity(intent);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
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
        dashBoardData.add(new DashBoardData(getResources().getString(R.string.question_bank),
                getString(R.string.que_bank_desc), R.drawable.book_bank));
        dashBoardData.add(new DashBoardData(getResources().getString(R.string.practice_session_name),
                getString(R.string.practice_desc), R.drawable.practics));
        dashBoardData.add(new DashBoardData(getResources().getString(R.string.exam_practice_name),
                getString(R.string.exam_desc), R.drawable.exam));
        dashBoardData.add(new DashBoardData(getResources().getString(R.string.title_activity_settings),
                getString(R.string.info_more), R.drawable.profile));
        return dashBoardData;
    }
}
