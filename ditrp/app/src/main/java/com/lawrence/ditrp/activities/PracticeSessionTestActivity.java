package com.lawrence.ditrp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.adapter.PracticeTestQuestionAdapter;
import com.lawrence.ditrp.dataModel.QuestionBank;
import com.lawrence.ditrp.interfaces.IExamSessionNavigationHandler;
import com.lawrence.ditrp.view.NonSwappableViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PracticeSessionTestActivity extends AppCompatActivity implements View.OnClickListener,
        IExamSessionNavigationHandler {
    private NonSwappableViewPager mViewPager;
    private Button mNextButton;
    private Button mPreviousButton;
    private int mPosition;
    private PracticeTestQuestionAdapter mPracticeTestQuestionAdapter;
    // Holds timer instance
    private TextView mQuestionCountView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_session_layout);
        Utils.setCustomActionBar(this, getResources().getString(R.string.practice_session_name), true, false);
        View view = getSupportActionBar().getCustomView();
        mQuestionCountView = (TextView) view.findViewById(R.id.action_bar_question_count);
        mPosition = getIntent().getIntExtra("position", 0);
        LinearLayout layoutAnswerCount = (LinearLayout) findViewById(R.id.layout_answer_count);
        layoutAnswerCount.setVisibility(View.GONE);
        mPreviousButton = (Button) findViewById(R.id.button_previous);
        mPreviousButton.setOnClickListener(this);
        mNextButton = (Button) findViewById(R.id.button_next);
        mNextButton.setOnClickListener(this);
        updatePreviousButtonStatus(false);
        mViewPager = (NonSwappableViewPager) findViewById(R.id.view_pager);
        mViewPager.setPagingEnabled(true);
        mPracticeTestQuestionAdapter = new PracticeTestQuestionAdapter(this, getPracticeSessionList());
        mViewPager.setAdapter(mPracticeTestQuestionAdapter);
        //mViewPager.setCurrentItem(58, true);   //for testing only
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mQuestionCountView.setText(String.format("%d/60", position + 1));
                updatePreviousButtonStatus(position > 0);
                if (position == 59) {
                    mNextButton.setText(R.string.home);
                    //updatePreviousButtonStatus(false);
                } else {
                    mNextButton.setText(R.string.next);
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
        List<QuestionBank> questionBanks = new ArrayList<>();
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
                handleNavigationOnNext();
                break;
        }
    }

    @Override
    public void updatePreviousButtonStatus(boolean shouldVisible) {
        if (shouldVisible) {
            mPreviousButton.setVisibility(View.VISIBLE);
        } else {
            mPreviousButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void handleNavigationOnNext() {
        if (mViewPager.getCurrentItem() == 59) {
            /*Toast.makeText(PracticeSessionTestActivity.this, "Total correct answer is: " +
                    mPracticeTestQuestionAdapter.getTotalCorrectAnswer(), Toast.LENGTH_SHORT).show();*/
            mPracticeTestQuestionAdapter.resetCorrectAnswer();
            navigateToHome();
        }
        mViewPager.setCurrentItem(getItem(+1), true);
    }

    /**
     * Notify to start practice session tests
     */
    private void navigateToHome() {
        Intent intent = new Intent(PracticeSessionTestActivity.this, DashBoardActivity.class);
        startActivity(intent);
        this.finish();
    }
}
