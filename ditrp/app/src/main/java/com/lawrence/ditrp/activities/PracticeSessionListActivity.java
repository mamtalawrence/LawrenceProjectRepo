package com.lawrence.ditrp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.listener.CustomRVItemTouchListener;
import com.lawrence.ditrp.adapter.PracticeSessionListAdapter;
import com.lawrence.ditrp.listener.RecyclerViewItemClickListener;

public class PracticeSessionListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_recycler_view_layout);
        Utils.setCustomActionBar(this, getResources().getString(R.string.practice_session_name),false, false);

        initializeView();
    }

    /**
     * Initialize variables
     */
    private void initializeView() {
        RecyclerView practiceSessionRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        PracticeSessionListAdapter adapter = new PracticeSessionListAdapter(this);
        practiceSessionRecyclerView.setAdapter(adapter);
        practiceSessionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        practiceSessionRecyclerView.addOnItemTouchListener(new CustomRVItemTouchListener(this,
                practiceSessionRecyclerView,
                new RecyclerViewItemClickListener() {
                    @Override
                    public void onClick(View view, int testNumber) {
                        notifyToStartPracticeTest(testNumber);
                    }

                    @Override
                    public void onLongClick(View view, int testNumber) {
                        notifyToStartPracticeTest(testNumber);
                    }
                }));
    }

    /**
     * Notify to start practice session tests
     * @param testNumber number of practice test
     */
    private void notifyToStartPracticeTest(int testNumber){
        Intent intent = new Intent(PracticeSessionListActivity.this, PracticeSessionTestActivity.class);
        intent.putExtra("position", testNumber);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
