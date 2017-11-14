package com.lawrence.ditrp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.adapter.ViewPagerAdapter;
import com.lawrence.ditrp.dataModel.QuestionBank;

import java.util.List;

/**
 * Created by Anagha.Mahajan on 10-Nov-17.
 */
public class ViewPagerActivity extends AppCompatActivity implements View.OnClickListener{

    public ViewPager viewPager;
    private Button mNextButton;
    private Button mPreviousButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_pager);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        mPreviousButton = (Button)findViewById(R.id.button_previous);
        mPreviousButton.setOnClickListener(this);
        mNextButton = (Button)findViewById(R.id.button_next);
        mNextButton.setOnClickListener(this);

        List<QuestionBank> questionBanks = Utils.getQuestionIntoDB(getApplicationContext());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, questionBanks);
        viewPager.setAdapter(viewPagerAdapter);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_previous:
                viewPager.setCurrentItem(getItem(-1), true);
                if (viewPager.getCurrentItem() == 0){
                    mPreviousButton.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.button_next:
                viewPager.setCurrentItem(getItem(+1), true);
                mPreviousButton.setVisibility(View.VISIBLE);
                break;
        }
    }
}
