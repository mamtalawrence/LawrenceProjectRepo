package com.lawrence.ditrp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.adapter.ScoreCardAdapter;
import com.lawrence.ditrp.dataModel.QuestionBank;

import java.util.ArrayList;

public class ScoreCardActivity extends AppCompatActivity implements View.OnClickListener {

    private int mExamTestNumber;
    private RelativeLayout mLayoutResult;
    private RelativeLayout mLayoutDisplayResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorecard_layout);
        Utils.setCustomActionBar(this, getResources().getString(R.string.score_card_label), false, false);
        // Get question list from bundle object
        boolean resultStatus = getIntent().getExtras().getBoolean("ResultStatus");
        mExamTestNumber = getIntent().getExtras().getInt("ExamTestPosition");
        mLayoutResult = (RelativeLayout) findViewById(R.id.layout_score_status);
        mLayoutResult.setVisibility(View.VISIBLE);
        mLayoutDisplayResult = (RelativeLayout) findViewById(R.id.layout_display_result);
        mLayoutDisplayResult.setVisibility(View.GONE);

        Button mButtonHome1 = (Button) findViewById(R.id.button_home1);
        mButtonHome1.setOnClickListener(this);
        Button mButtonHome2 = (Button) findViewById(R.id.button_home2);
        mButtonHome2.setOnClickListener(this);
        Button mButtonScorecard = (Button) findViewById(R.id.button_score_card);
        mButtonScorecard.setOnClickListener(this);
        Button mButtonTryAgain = (Button) findViewById(R.id.button_try_again);
        mButtonTryAgain.setOnClickListener(this);
        TextView mTextResultStatus = (TextView) findViewById(R.id.text_result_state);
        TextView mTextResultInstruction = (TextView) findViewById(R.id.text_result_instruction);
        ImageView mBoyImage = (ImageView)findViewById(R.id.img_boy);
        if (resultStatus) {
            mTextResultStatus.setText(getString(R.string.passed_label));
            mTextResultInstruction.setText(getString(R.string.result_passed_suggestion_label));
            mBoyImage.setImageResource(R.drawable.happy_boy);
        } else {
            mTextResultStatus.setText(getString(R.string.failed_label));
            mTextResultInstruction.setText(getString(R.string.result_failed_suggestion_label));
            mBoyImage.setImageResource(R.drawable.sad_boy);
        }

    }

    /**
     * Notify to start practice session tests
     */
    private void navigateToHome() {
       /* Intent intent = new Intent(ScoreCardActivity.this, DashBoardActivity.class);
        startActivity(intent);*/
        this.finish();
    }

    /**
     * Notify to start practice session tests
     *
     * @param testNumber number of practice test
     */
    private void notifyToStartExamTest(int testNumber) {
        Intent intent = new Intent(ScoreCardActivity.this, ExamPracticeTestActivity.class);
        intent.putExtra("ExamTestNumber", testNumber);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_home1:
            case R.id.button_home2:
                navigateToHome();
                break;
            case R.id.button_score_card:
                updateViewState();
                break;
            case R.id.button_try_again:
                notifyToStartExamTest(mExamTestNumber);
                break;
            default:
        }
    }

    private void updateViewState() {
        mLayoutResult.setVisibility(View.INVISIBLE);
        mLayoutDisplayResult.setVisibility(View.VISIBLE);
        ArrayList<QuestionBank> questionBankList = ((ArrayList<QuestionBank>) getIntent().getExtras().getSerializable
                ("ResultList"));
        RecyclerView scorecardRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ScoreCardAdapter adapter = new ScoreCardAdapter(questionBankList, this);
        scorecardRecyclerView.setItemAnimator(new DefaultItemAnimator());
        scorecardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // adding inbuilt divider line
        scorecardRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        scorecardRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateToHome();
    }
}
