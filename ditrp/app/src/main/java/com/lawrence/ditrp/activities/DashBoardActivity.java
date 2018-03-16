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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_recycler_view_layout);
        Utils.setCustomActionBar(this, getString(R.string.app_name), false, false);

        // Set View with adapter
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
                                intent = new Intent(DashBoardActivity.this, PracticeSessionActivity.class);
                                break;
                            case 2:
                                intent = new Intent(DashBoardActivity.this, ExamPracticeActivity.class);
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
                "List of questions & answers and meaning of logos ", R.drawable.book_bank));
        dashBoardData.add(new DashBoardData(getResources().getString(R.string.practice_session_name),
                "Test your knowledge without worrying about time ", R.drawable.practics));
        dashBoardData.add(new DashBoardData(getResources().getString(R.string.exam_practice_name),
                "Time and question bound test exactly same as exam", R.drawable.exam));
        dashBoardData.add(new DashBoardData(getResources().getString(R.string.title_activity_settings),
                "Information and more", R.drawable.setting));
        return dashBoardData;
    }
}
