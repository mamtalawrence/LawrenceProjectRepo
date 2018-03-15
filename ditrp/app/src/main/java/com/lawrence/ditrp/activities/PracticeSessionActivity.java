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
import com.lawrence.ditrp.adapter.CustomRVItemTouchListener;
import com.lawrence.ditrp.adapter.PracticeSessionAdapter;
import com.lawrence.ditrp.listener.RecyclerViewItemClickListener;

/**
 * Created by Anagha.Mahajan on 10-Nov-17.
 */
public class PracticeSessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_recycler_view_layout);
        Utils.setCustomActionBar(this, getResources().getString(R.string.practice_session_name),false, false);

        RecyclerView practiceSessionRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        PracticeSessionAdapter adapter = new PracticeSessionAdapter(this);
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
        Intent intent = new Intent(PracticeSessionActivity.this,
                PracticeSessionTestActivity.class);
        intent.putExtra("position", testNumber);
        startActivity(intent);
    }
}