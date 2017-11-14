package com.lawrence.ditrp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.adapter.CustomRVItemTouchListener;
import com.lawrence.ditrp.adapter.ExamPracticeAdapter;
import com.lawrence.ditrp.listener.RecyclerViewItemClickListener;

public class ExamPracticeActivity extends AppCompatActivity {

    RecyclerView mExamPracticeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        Utils.setCustomActionBar(this, getResources().getString(R.string.exam_practice_name));

        mExamPracticeRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ExamPracticeAdapter adapter = new ExamPracticeAdapter(this);
        mExamPracticeRecyclerView.setAdapter(adapter);
        mExamPracticeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExamPracticeRecyclerView.addOnItemTouchListener(new CustomRVItemTouchListener(this,
                mExamPracticeRecyclerView,
                new RecyclerViewItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent intent = new Intent(ExamPracticeActivity.this, ViewPagerActivity.class);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        Intent intent = new Intent(ExamPracticeActivity.this, ViewPagerActivity.class);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }
                }));
    }
}
