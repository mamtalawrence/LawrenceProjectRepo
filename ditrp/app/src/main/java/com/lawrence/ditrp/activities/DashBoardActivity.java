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
        dashBoardData.add(new DashBoardData(getResources().getString(R.string.question_bank),
                "List of questions & answers and meaning of road sign ", R.drawable.book_bank));
        dashBoardData.add(new DashBoardData(getResources().getString(R.string.practice_session_name),
                "Test your knowledge without worrying about time ", R.drawable.practics));
        dashBoardData.add(new DashBoardData(getResources().getString(R.string.exam_practice_name),
                "Time and question bound test exactly same as exam", R.drawable.exam));
        dashBoardData.add(new DashBoardData(getResources().getString(R.string.title_activity_settings),
                "Language selection, forms, information and more", R.drawable.setting));
        return dashBoardData;
    }
}
