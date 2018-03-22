package com.lawrence.ditrp.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.adapter.QuestionBankItemAdapter;
import com.lawrence.ditrp.dataModel.ItemsLibrary;

import java.util.List;

public class QuestionBankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_recycler_view_layout);
        Utils.setCustomActionBar(this, getResources().getString(R.string.question_bank), false, false);
        initializeView();
    }

    /**
     * Set View with adapter
     */
    private void initializeView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        List<ItemsLibrary> questionBanksAllData = Utils.getItemDetailsFromLibrary(this);
        QuestionBankItemAdapter adapter = new QuestionBankItemAdapter(questionBanksAllData, this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}


