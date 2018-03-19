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
import android.widget.RelativeLayout;
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
    private RelativeLayout mLayoutPracticeTest;
    private View mScoreLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_session_layout);
        Utils.setCustomActionBar(this, getResources().getString(R.string.practice_session_name), true, false);
        View view = getSupportActionBar().getCustomView();
        mQuestionCountView = (TextView) view.findViewById(R.id.action_bar_question_count);
        mPosition = getIntent().getIntExtra("position", 0);
        mLayoutPracticeTest = (RelativeLayout) findViewById(R.id.practice_test_layout);
        mScoreLayout = findViewById(R.id.score_layout);
        mLayoutPracticeTest.setVisibility(View.VISIBLE);
        mScoreLayout.setVisibility(View.GONE);

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
                mQuestionCountView.setText(String.format("%d/50", position + 1));
                updatePreviousButtonStatus(position > 0);
                if (position == 49) {
                    mNextButton.setText(R.string.finish);
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
        Button mButtonHome1 = (Button) findViewById(R.id.button_home1);
        mButtonHome1.setOnClickListener(this);
        Button mButtonScorecard = (Button) findViewById(R.id.button_score_card);
        mButtonScorecard.setVisibility(View.GONE);
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
            case R.id.button_home1:
                navigateToHome();
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
        updateAnswerCount();
        if (mViewPager.getCurrentItem() == 49) {
            mLayoutPracticeTest.setVisibility(View.GONE);
            mQuestionCountView.setVisibility(View.GONE);
            mScoreLayout.setVisibility(View.VISIBLE);
            calculateResult();
            mPracticeTestQuestionAdapter.resetCorrectAnswer();
        } else {
            mViewPager.setCurrentItem(getItem(+1), true);
        }
    }

    /**
     * Notify to start practice session tests
     */
    private void navigateToHome() {
        Intent intent = new Intent(PracticeSessionTestActivity.this, DashBoardActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void updateAnswerCount() {
        mPracticeTestQuestionAdapter.handleItemSelection(mViewPager.getCurrentItem());
    }

    private void calculateResult() {
        int totalQuestions = mPracticeTestQuestionAdapter.getCount() * 2;
        int totalCorrectAnswers = mPracticeTestQuestionAdapter.getNumberOfCorrectAnswer() * 2;
        double percentage = (totalCorrectAnswers * 100) / totalQuestions;
        showResultStatus(!(percentage < 40));
    }

    private void showResultStatus(boolean resultStatus) {
        TextView mTextResultStatus = (TextView) findViewById(R.id.text_result_state);
        TextView mTextResultInstruction = (TextView) findViewById(R.id.text_result_instruction);
        if (resultStatus) {
            mTextResultStatus.setText(getString(R.string.passed_label));
            mTextResultInstruction.setText(getString(R.string.result_passed_suggestion_label));
        } else {
            mTextResultStatus.setText(getString(R.string.failed_label));
            mTextResultInstruction.setText(getString(R.string.result_failed_suggestion_label));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
