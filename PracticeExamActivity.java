package com.lawrence.ditrp.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.adapter.ViewPagerAdapter;
import com.lawrence.ditrp.dataModel.QuestionBank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Anagha.Mahajan on 10-Nov-17.
 */
public class PracticeExamActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private Button mNextButton;
    private Button mPreviousButton;
    private TextView mCountText;
    private int mPosition;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        Utils.setCustomActionBar(this, getResources().getString(R.string.exam_practice_name));
        View view = getSupportActionBar().getCustomView();
        mCountText = (TextView) view.findViewById(R.id.action_bar_right_text);
        mCountText.setVisibility(View.VISIBLE);

        mPosition = getIntent().getIntExtra("position", 0);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mPreviousButton = (Button) findViewById(R.id.button_previous);
        mPreviousButton.setOnClickListener(this);
        mNextButton = (Button) findViewById(R.id.button_next);
        mNextButton.setOnClickListener(this);
        updatePreviousButtonStatus(false);
        mViewPagerAdapter = new ViewPagerAdapter(this, getPracticeSessionList());
        mViewPager.setAdapter(mViewPagerAdapter);
        //mViewPager.setCurrentItem(58, true);   //for testing only
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mCountText.setText(String.format("%d/60", position + 1));
                updatePreviousButtonStatus(position > 0);

                if (position == 59) {
                    mNextButton.setText("FINISHED");
                } else {
                    mNextButton.setText("Next");
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * Get question list from DB
     *
     * @return list of question
     */
    private List<QuestionBank> getPracticeSessionList() {
        HashMap<Integer, ArrayList<Integer>> questionList = Utils.getPracticeListKey(this);
        List<QuestionBank> questionBanksAllData = Utils.getQuestionIntoDB(this);
        ArrayList<Integer> dataPointers = questionList.get(mPosition);
        List<QuestionBank> questionBanks = new ArrayList<QuestionBank>();
        for (Integer questionIndex : dataPointers) {
            questionBanks.add(questionBanksAllData.get(questionIndex));
        }
        return questionBanks;
    }

    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_previous:
                mViewPager.setCurrentItem(getItem(-1), true);
                if (mViewPager.getCurrentItem() == 0) {
                    updatePreviousButtonStatus(false);
                }
                break;
            case R.id.button_next:
                if (mViewPager.getCurrentItem() == 59) {
                    Toast.makeText(PracticeExamActivity.this, "Ready for report? \n Total correct answer is: " +
                            mViewPagerAdapter.getTotalCorrectAnswer(), Toast.LENGTH_SHORT).show();
                    mViewPagerAdapter.resetCorrectAnswer();
                    //Get list
                    mViewPagerAdapter.getQuestionBanksObjectList();
                }
                mViewPager.setCurrentItem(getItem(+1), true);
                updatePreviousButtonStatus(true);
                break;
        }
    }

    /**
     * Set the visibility of the next/previous button views
     *
     * @param shouldVisible true, if need to show otherwise false
     */
    private void updatePreviousButtonStatus(boolean shouldVisible) {
        if (shouldVisible) {
            mPreviousButton.setVisibility(View.VISIBLE);
        } else {
            mPreviousButton.setVisibility(View.INVISIBLE);
        }
    }
}
