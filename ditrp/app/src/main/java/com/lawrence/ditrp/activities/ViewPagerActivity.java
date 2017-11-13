package com.lawrence.ditrp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.adapter.ViewPagerAdapter;
import com.lawrence.ditrp.dataModel.QuestionBank;

import java.util.List;

/**
 * Created by Anagha.Mahajan on 10-Nov-17.
 */
public class ViewPagerActivity extends AppCompatActivity {

    public ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_pager);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        List<QuestionBank> questionBanks = Utils.getQuestionIntoDB(getApplicationContext());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, questionBanks);
        viewPager.setAdapter(viewPagerAdapter);
    }
}
