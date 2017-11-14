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

    RecyclerView mPracticeSessionRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_session);
        Utils.setCustomActionBar(this, getResources().getString(R.string.practice_session_name));

        mPracticeSessionRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        PracticeSessionAdapter adapter = new PracticeSessionAdapter(this);
        mPracticeSessionRecyclerView.setAdapter(adapter);
        mPracticeSessionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPracticeSessionRecyclerView.addOnItemTouchListener(new CustomRVItemTouchListener(this,
                mPracticeSessionRecyclerView,
                new RecyclerViewItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent intent = new Intent(PracticeSessionActivity.this, ViewPagerActivity.class);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        Intent intent = new Intent(PracticeSessionActivity.this, ViewPagerActivity.class);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }
                }));
    }
}
