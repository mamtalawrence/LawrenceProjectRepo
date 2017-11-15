package com.lawrence.ditrp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.adapter.ViewPagerAdapter;
import com.lawrence.ditrp.dataModel.QuestionBank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anagha.Mahajan on 10-Nov-17.
 */
public class ViewPagerActivity extends AppCompatActivity implements View.OnClickListener {

    public ViewPager viewPager;
    private Button mNextButton;
    private Button mPreviousButton;
    private TextView countText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        Utils.setCustomActionBar(this, getResources().getString(R.string.exam_practice_name));
        View view = getSupportActionBar().getCustomView();
        countText = (TextView) view.findViewById(R.id.action_bar_right_text);
        countText.setVisibility(View.VISIBLE);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        mPreviousButton = (Button) findViewById(R.id.button_previous);
        mPreviousButton.setOnClickListener(this);
        mNextButton = (Button) findViewById(R.id.button_next);
        mNextButton.setOnClickListener(this);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, getPracticeSessionList());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                countText.setText((position + 1) + "/60");
                if (position > 0) {
                    mPreviousButton.setVisibility(View.VISIBLE);
                } else {
                    mPreviousButton.setVisibility(View.INVISIBLE);
                }

                if (position == 59) {
                    mNextButton.setVisibility(View.INVISIBLE);
                } else {
                    mNextButton.setVisibility(View.VISIBLE);
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

    private List<QuestionBank> getPracticeSessionList() {
        List<QuestionBank> questionBanksAllData = Utils.getQuestionIntoDB(this);
        ArrayList<Integer> dataPointers = Utils.getRandom60(0, questionBanksAllData.size() - 1);
        List<QuestionBank> questionBanks = new ArrayList<QuestionBank>();
        for (int i = 0; i < dataPointers.size(); i++) {
            questionBanks.add(questionBanksAllData.get(dataPointers.get(i)));
        }
        return questionBanks;
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_previous:
                viewPager.setCurrentItem(getItem(-1), true);
                if (viewPager.getCurrentItem() == 0) {
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
