package com.lawrence.ditrp.activities;

import android.annotation.SuppressLint;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.adapter.ExamPracticeQuestionAdapter;
import com.lawrence.ditrp.adapter.PracticeTestQuestionAdapter;
import com.lawrence.ditrp.dataModel.QuestionBank;
import com.lawrence.ditrp.interfaces.IExamSessionNavigationHandler;

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
    private ViewPager mViewPager;
    private Button mNextButton;
    private Button mPreviousButton;
    private int mPosition;
    private ExamPracticeQuestionAdapter mPracticeTestQuestionAdapter;
    private RelativeLayout mLayoutInstruction;
    private RelativeLayout mLayoutQuestions;
    private Button mStartButton;
    // Holds timer instance
    private CountDownTimer mTimer = null;
    private TextView mQuestionCountView;
    private View mTimerLayout;
    private TextView mTimerView;

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


        mPosition = getIntent().getIntExtra("position", 0);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mPreviousButton = (Button) findViewById(R.id.button_previous);
        mPreviousButton.setOnClickListener(this);
        mNextButton = (Button) findViewById(R.id.button_next);
        mNextButton.setOnClickListener(this);
        updatePreviousButtonStatus(false);
        mPracticeTestQuestionAdapter = new ExamPracticeQuestionAdapter(this, getPracticeSessionList());
        mViewPager.setAdapter(mPracticeTestQuestionAdapter);
        //mViewPager.setCurrentItem(58, true);   //for testing only
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
                if (sec == 5) {
                    mTimerView.setTextColor(Color.RED);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mTimerView.setText("0 s");
                        }
                    }, 5000);
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
        if (mViewPager.getCurrentItem() == 59) {
            mTimerLayout.setVisibility(View.GONE);
            Toast.makeText(ExamPracticeTestActivity.this, "Ready for report? \n Total correct answer is: " +
                    mPracticeTestQuestionAdapter.getTotalCorrectAnswer(), Toast.LENGTH_SHORT).show();
            mPracticeTestQuestionAdapter.resetCorrectAnswer();
            //Get list
            mPracticeTestQuestionAdapter.getQuestionBanksObjectList();
        } else {
            mViewPager.setCurrentItem(getItem(+1), true);
            startTimer();
        }
    }
}
