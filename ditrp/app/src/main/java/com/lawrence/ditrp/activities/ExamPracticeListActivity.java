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
import com.lawrence.ditrp.adapter.ExamPracticeListAdapter;
import com.lawrence.ditrp.listener.RecyclerViewItemClickListener;

public class ExamPracticeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_recycler_view_layout);
        Utils.setCustomActionBar(this, getResources().getString(R.string.exam_practice_name), false, false);

        initializeView();
    }

    /**
     * Set View with adapter
     */
    private void initializeView() {
        RecyclerView examPracticeRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ExamPracticeListAdapter adapter = new ExamPracticeListAdapter(this);
        examPracticeRecyclerView.setAdapter(adapter);
        examPracticeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        examPracticeRecyclerView.addOnItemTouchListener(new CustomRVItemTouchListener(this,
                examPracticeRecyclerView, new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                notifyToStartPracticeTest(position);
            }

            @Override
            public void onLongClick(View view, int position) {
                notifyToStartPracticeTest(position);
            }
        }));
    }

    /**
     * Notify to start practice session tests
     *
     * @param testNumber number of practice test
     */
    private void notifyToStartPracticeTest(int testNumber) {
        Intent intent = new Intent(ExamPracticeListActivity.this, ExamPracticeTestActivity.class);
        intent.putExtra("ExamTestNumber", testNumber);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
