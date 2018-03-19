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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
    //Declare a variable to hold CountDownTimer milli seconds in future time
    private long MILLISECONDS_IN_FUTURE = 60000; //60 seconds
    //Declare a variable to hold CountDownTimer interval time
    private long COUNTDOWN_INTERVAL = 1000; //1 second
    //Declare a variable to hold CountDownTimer remaining time
    private long TIME_REMAINING = 0;
    private NonSwappableViewPager mViewPager;
    private Button mNextButton;
    private Button mPreviousButton;
    private int mExamTestNumber;
    private ExamPracticeQuestionAdapter mPracticeTestQuestionAdapter;
    private RelativeLayout mLayoutInstruction;
    private FrameLayout mLayoutQuestions;
    // Holds timer instance
    private CountDownTimer mTimer = null;
    private TextView mQuestionCountView;
    private View mTimerLayout;
    private TextView mTimerView;
    private TextView mCorrectAnswerCount;
    private TextView mInCorrectAnswerCount;
    //Declare a variable to hold count down timer's paused status
    private boolean isPaused = false;
    private boolean isStartExam = false;
    private Handler mTimerHandler = null;
    private Runnable mTimerRunnable = new Runnable() {
        @Override
        public void run() {
            mTimerView.setText("0 s");
        }
    };

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
        mLayoutQuestions = (FrameLayout) findViewById(R.id.layout_questions);
        mLayoutQuestions.setVisibility(View.INVISIBLE);
        Button startButton = (Button) findViewById(R.id.button_getStarted);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayoutInstruction.setVisibility(View.INVISIBLE);
                mLayoutQuestions.setVisibility(View.VISIBLE);
                isPaused = false;
                isStartExam = true;
                startTimer();
            }
        });
        mExamTestNumber = getIntent().getExtras().getInt("ExamTestNumber");
        mPreviousButton = (Button) findViewById(R.id.button_previous);
        mPreviousButton.setOnClickListener(this);
        mNextButton = (Button) findViewById(R.id.button_next);
        mNextButton.setOnClickListener(this);
        updatePreviousButtonStatus(false);
        LinearLayout layoutAnswerCount = (LinearLayout) findViewById(R.id.layout_answer_count);
        layoutAnswerCount.setVisibility(View.VISIBLE);
        mCorrectAnswerCount = (TextView) findViewById(R.id.correct_answer_count);
        mInCorrectAnswerCount = (TextView) findViewById(R.id.wrong_answer_count);

        mViewPager = (NonSwappableViewPager) findViewById(R.id.view_pager);
        mViewPager.setPagingEnabled(false);
        mPracticeTestQuestionAdapter = new ExamPracticeQuestionAdapter(this, getPracticeSessionList());
        mViewPager.setAdapter(mPracticeTestQuestionAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mQuestionCountView.setText(String.format("%d/50", position + 1));
                if (position == 49) {
                    mNextButton.setText(getString(R.string.finish));
                } else {
                    mNextButton.setText(getString(R.string.next));
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
                if (isAnswerSelected()) {
                    isPaused = false;
                    handleNavigationOnNext();
                } else {
                    Utils.showToast(this, getString(R.string.no_selection_error_message), false);
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
        mTimer = new CountDownTimer(MILLISECONDS_IN_FUTURE, COUNTDOWN_INTERVAL) {
            public void onTick(long MILLISECONDS_IN_FUTURE) {
                long sec = (TimeUnit.MILLISECONDS.toSeconds(MILLISECONDS_IN_FUTURE) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(MILLISECONDS_IN_FUTURE)));
                TIME_REMAINING = MILLISECONDS_IN_FUTURE;
                Log.e(TAG, "onTick: " + sec);
                mTimerView.setText(String.format(Locale.ENGLISH, "%02d s", sec));
                if (sec <= 5) {
                    mTimerView.setTextColor(Color.RED);
                    if (mTimerHandler == null) {
                        mTimerHandler = new Handler();
                        mTimerHandler.postDelayed(mTimerRunnable, 5000);
                    }
                } else {
                    mTimerView.setTextColor(Color.BLACK);
                }
            }

            public void onFinish() {
                if (mTimerHandler != null) {
                    mTimerHandler.removeCallbacks(mTimerRunnable);
                    mTimerHandler = null;
                }
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
        MILLISECONDS_IN_FUTURE = 60000;
        TIME_REMAINING = 0;
        updatePreviousButtonStatus(false);
        cancelTimer();
        updateAnswerCount();
        if (mViewPager.getCurrentItem() == 49) {
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
    protected void onResume() {
        super.onResume();
        //Specify the current state is not paused and canceled.
        if (isPaused && isStartExam) {
            isPaused = false;
            startTimer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //When user request to pause the CountDownTimer
        isPaused = true;
        //Initialize a new CountDownTimer instance
        MILLISECONDS_IN_FUTURE = TIME_REMAINING;
        COUNTDOWN_INTERVAL = 1000;
        cancelTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isPaused = false;
        isStartExam = false;
        TIME_REMAINING = 0;
        MILLISECONDS_IN_FUTURE = 60000;
        cancelTimer();
    }

    @Override
    public void onBackPressed() {
        if (isStartExam) {
            Utils.exitDialog(this, getString(R.string.exit_exam_title), getString(R.string.exit_exam_message));
        } else {
            super.onBackPressed();
        }
    }

    private void calculateResult() {
        int totalQuestions = mPracticeTestQuestionAdapter.getCount() * 2;
        int totalCorrectAnswers = mPracticeTestQuestionAdapter.getNumberOfCorrectAnswer() * 2;
        double percentage = (totalCorrectAnswers * 100) / totalQuestions;
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
