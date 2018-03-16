package com.lawrence.ditrp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.adapter.ExamPracticeQuestionAdapter;
import com.lawrence.ditrp.dataModel.QuestionBank;
import com.lawrence.ditrp.interfaces.IExamSessionNavigationHandler;
import com.lawrence.ditrp.view.NonSwappableViewPager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Anagha.Mahajan on 10-Nov-17.
 */
public class ExamPracticeTestActivity extends AppCompatActivity implements View.OnClickListener,
        IExamSessionNavigationHandler {

    private static final String TAG = ExamPracticeTestActivity.class.getSimpleName();
    private NonSwappableViewPager mViewPager;
    private Button mNextButton;
    private Button mPreviousButton;
    private int mExamTestNumber;
    private ExamPracticeQuestionAdapter mPracticeTestQuestionAdapter;
    private RelativeLayout mLayoutInstruction;
    private RelativeLayout mLayoutQuestions;
    private Button mStartButton;
    // Holds timer instance
    private CountDownTimer mTimer = null;
    private TextView mQuestionCountView;
    private View mTimerLayout;
    private TextView mTimerView;
    private LinearLayout mLayoutAnswerCount;
    private TextView mCorrectAnswerCount;
    private TextView mInCorrectAnswerCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_practice_layout);
        Utils.setCustomActionBar(this, getResources().getString(R.string.exam_practice_name), false, false);
        View view = getSupportActionBar().getCustomView();
        mQuestionCountView = (TextView) view.findViewById(R.id.action_bar_question_count);
        mTimerLayout = view.findViewById(R.id.action_bar_timer_layout);
        mTimerView = (TextView) view.findViewById(R.id.action_bar_timer);
        mLayoutInstruction = (RelativeLayout) findViewById(R.id.layout_instructions);
        mLayoutInstruction.setVisibility(View.VISIBLE);
        mLayoutQuestions = (RelativeLayout) findViewById(R.id.layout_questions);
        mLayoutQuestions.setVisibility(View.INVISIBLE);
        mStartButton = (Button) findViewById(R.id.button_getStarted);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayoutInstruction.setVisibility(View.INVISIBLE);
                mLayoutQuestions.setVisibility(View.VISIBLE);
                startTimer();
            }
        });
        mExamTestNumber = getIntent().getExtras().getInt("ExamTestNumber");
        mPreviousButton = (Button) findViewById(R.id.button_previous);
        mPreviousButton.setOnClickListener(this);
        mNextButton = (Button) findViewById(R.id.button_next);
        mNextButton.setOnClickListener(this);
        updatePreviousButtonStatus(false);
        mLayoutAnswerCount = (LinearLayout) findViewById(R.id.layout_answer_count);
        mLayoutAnswerCount.setVisibility(View.VISIBLE);
        mCorrectAnswerCount = (TextView) findViewById(R.id.correct_answer_count);
        mInCorrectAnswerCount = (TextView) findViewById(R.id.wrong_answer_count);

        mViewPager = (NonSwappableViewPager) findViewById(R.id.view_pager);
        mViewPager.setPagingEnabled(false);
        mPracticeTestQuestionAdapter = new ExamPracticeQuestionAdapter(this, getPracticeSessionList());
        mViewPager.setAdapter(mPracticeTestQuestionAdapter);
        mViewPager.setCurrentItem(58, true);   //for testing only
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mQuestionCountView.setText(String.format("%d/60", position + 1));
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

        mCorrectAnswerCount.setText(String.valueOf(mPracticeTestQuestionAdapter.getNumberOfCorrectAnswer()));
        mInCorrectAnswerCount.setText(String.valueOf(mPracticeTestQuestionAdapter.getNumberOfIncorrectAnswer()));
    }

    /**
     * Get question list from DB
     *
     * @return list of question
     */
    private List<QuestionBank> getPracticeSessionList() {
        HashMap<Integer, ArrayList<Integer>> questionList = Utils.getPracticeListKey(this);
        List<QuestionBank> questionBanksAllData = Utils.getQuestionIntoDB(this);
        ArrayList<Integer> dataPointers = questionList.get(mExamTestNumber);
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
                if (isAnswerSelected()) {
                    handleNavigationOnNext();
                }
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

    //start timer function
    private void startTimer() {
        mTimerLayout.setVisibility(View.VISIBLE);
        mTimerView.setVisibility(View.VISIBLE);
        mQuestionCountView.setVisibility(View.VISIBLE);
        mTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                long sec = (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                Log.e(TAG, "onTick: " + sec);
                mTimerView.setText(String.format(Locale.ENGLISH, "%02d s", sec));
                if (sec <= 5) {
                    mTimerView.setTextColor(Color.RED);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mTimerView.setText("0 s");
                        }
                    }, 5000);
                } else {
                    mTimerView.setTextColor(Color.BLACK);
                }
            }

            public void onFinish() {
                handleNavigationOnNext();
            }
        };
        mTimer.start();
    }

    //cancel timer
    private void cancelTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void handleNavigationOnNext() {
        updatePreviousButtonStatus(false);
        cancelTimer();
        updateAnswerCount();
        if (mViewPager.getCurrentItem() == 59) {
            calculateResult();
            mPracticeTestQuestionAdapter.resetCorrectAnswer();
        } else {
            mViewPager.setCurrentItem(getItem(+1), true);
            startTimer();
        }
    }

    /**
     * Check whether answer selected or not.
     *
     * @return true if answer selected otherwise false.
     */
    private boolean isAnswerSelected() {
        return mPracticeTestQuestionAdapter.isAnswerSelected(mViewPager.getCurrentItem());
    }

    private void updateAnswerCount() {
        mPracticeTestQuestionAdapter.handleItemSelection(mViewPager.getCurrentItem());
        mCorrectAnswerCount.setText(String.valueOf(mPracticeTestQuestionAdapter.getNumberOfCorrectAnswer()));
        mInCorrectAnswerCount.setText(String.valueOf(mPracticeTestQuestionAdapter.getNumberOfIncorrectAnswer()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        cancelTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void calculateResult(){
        int totalQuestions = mPracticeTestQuestionAdapter.getCount();
        int totalCorrectAnswers = mPracticeTestQuestionAdapter.getNumberOfCorrectAnswer();
        double percentage = (totalCorrectAnswers * 100)/totalQuestions;
        notifyToShowScoreCard(!(percentage < 40));
    }
    /**
     * Notify to start practice session tests
     *
     * @param resultStatus true if pass otherwise false
     */
    private void notifyToShowScoreCard(boolean resultStatus) {
        Intent intent = new Intent(this, ScoreCardActivity.class);
        ArrayList<QuestionBank> arrayList = (ArrayList<QuestionBank>) mPracticeTestQuestionAdapter.getResultList();
        Bundle bundle = new Bundle();
        bundle.putInt("ExamTestPosition", mExamTestNumber);
        bundle.putBoolean("ResultStatus", resultStatus);
        bundle.putSerializable("ResultList", (Serializable) arrayList);
        intent.putExtras(bundle);
        startActivity(intent);
        this.finish();
    }
}
