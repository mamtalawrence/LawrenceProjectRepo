package com.lawrence.ditrp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.dataModel.QuestionBank;

import java.util.List;

/**
 * Created by Anagha.Mahajan on 10-Nov-17.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<QuestionBank> mQuestionBanksObjectList;
    private ViewHolder viewHolder;
    private int totalCorrectAnswer;

    public ViewPagerAdapter(Context context, List<QuestionBank> questionBanks) {
        this.context = context;
        this.mQuestionBanksObjectList = questionBanks;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = inflater.inflate(R.layout.viewpager_item, container, false);
        viewHolder = new ViewHolder();

        viewHolder.view3 = itemView.findViewById(R.id.view3);
        viewHolder.view4 = itemView.findViewById(R.id.view4);
        viewHolder.questionTextView = (TextView) itemView.findViewById(R.id.text_question);
        viewHolder.questionTextView.setText(String.format("Q. %s", mQuestionBanksObjectList.get(position)
                .getQuestion()));

        String studentAns = mQuestionBanksObjectList.get(position).getStudentAns();

        viewHolder.answerATextView = (TextView) itemView.findViewById(R.id.text_answer_a);
        viewHolder.answerATextView.setText(String.format("A. %s", mQuestionBanksObjectList.get(position).getOptionA()));

        viewHolder.answerBTextView = (TextView) itemView.findViewById(R.id.text_answer_b);
        viewHolder.answerBTextView.setText(String.format("B. %s", mQuestionBanksObjectList.get(position).getOptionB()));

        viewHolder.answerCTextView = (TextView) itemView.findViewById(R.id.text_answer_c);
        if (!TextUtils.isEmpty(mQuestionBanksObjectList.get(position).getOptionC())) {
            viewHolder.answerCTextView.setText(String.format("C. %s", mQuestionBanksObjectList.get(position)
                    .getOptionC()));
            viewHolder.view3.setVisibility(View.VISIBLE);
            viewHolder.answerCTextView.setVisibility(View.VISIBLE);
        }

        viewHolder.answerDTextView = (TextView) itemView.findViewById(R.id.text_answer_d);
        if (!TextUtils.isEmpty(mQuestionBanksObjectList.get(position).getOptionD())) {
            viewHolder.answerDTextView.setText(String.format("D. %s", mQuestionBanksObjectList.get(position)
                    .getOptionD()));
            viewHolder.view4.setVisibility(View.VISIBLE);
            viewHolder.answerDTextView.setVisibility(View.VISIBLE);
        }

        //Check it attempted or not
        if (TextUtils.isEmpty(studentAns)) {
            viewHolder.answerATextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mQuestionBanksObjectList.get(position).setStudentAns("option_a");
                    notifyDataSetChanged();
                }
            });

            viewHolder.answerBTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mQuestionBanksObjectList.get(position).setStudentAns("option_b");
                    notifyDataSetChanged();
                }
            });

            viewHolder.answerCTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mQuestionBanksObjectList.get(position).setStudentAns("option_c");
                    notifyDataSetChanged();
                }
            });

            viewHolder.answerDTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mQuestionBanksObjectList.get(position).setStudentAns("option_d");
                    notifyDataSetChanged();
                }
            });
        }
        setSelected(position);
        container.addView(itemView);
        return new ViewHolderMain(itemView, position);

    }

    /**
     * Set selected item color
     *
     * @param position of item
     */
    private void setSelected(int position) {

        if (!TextUtils.isEmpty(mQuestionBanksObjectList.get(position).getStudentAns())) {

            String correctAns = mQuestionBanksObjectList.get(position).getCorrectAns();
            String studentAns = mQuestionBanksObjectList.get(position).getStudentAns();

            if (correctAns.equalsIgnoreCase(studentAns)) {
                totalCorrectAnswer++;
                if (mQuestionBanksObjectList.get(position).getStudentAns().equalsIgnoreCase("option_a")) {
                    viewHolder.answerATextView.setTextColor(Color.WHITE);
                    viewHolder.answerATextView.setBackgroundResource(android.R.color.holo_green_light);
                } else if (mQuestionBanksObjectList.get(position).getStudentAns().equalsIgnoreCase("option_b")) {
                    viewHolder.answerBTextView.setTextColor(Color.WHITE);
                    viewHolder.answerBTextView.setBackgroundResource(android.R.color.holo_green_light);
                } else if (mQuestionBanksObjectList.get(position).getStudentAns().equalsIgnoreCase("option_c")) {
                    viewHolder.answerCTextView.setTextColor(Color.WHITE);
                    viewHolder.answerCTextView.setBackgroundResource(android.R.color.holo_green_light);
                } else if (mQuestionBanksObjectList.get(position).getStudentAns().equalsIgnoreCase("option_d")) {
                    viewHolder.answerDTextView.setTextColor(Color.WHITE);
                    viewHolder.answerDTextView.setBackgroundResource(android.R.color.holo_green_light);
                }
            } else {
                //Student ans
                if (mQuestionBanksObjectList.get(position).getStudentAns().equalsIgnoreCase("option_a")) {
                    viewHolder.answerATextView.setTextColor(Color.WHITE);
                    viewHolder.answerATextView.setBackgroundResource(android.R.color.holo_red_light);
                } else if (mQuestionBanksObjectList.get(position).getStudentAns().equalsIgnoreCase("option_b")) {
                    viewHolder.answerBTextView.setTextColor(Color.WHITE);
                    viewHolder.answerBTextView.setBackgroundResource(android.R.color.holo_red_light);
                } else if (mQuestionBanksObjectList.get(position).getStudentAns().equalsIgnoreCase("option_c")) {
                    viewHolder.answerCTextView.setTextColor(Color.WHITE);
                    viewHolder.answerCTextView.setBackgroundResource(android.R.color.holo_red_light);
                } else if (mQuestionBanksObjectList.get(position).getStudentAns().equalsIgnoreCase("option_d")) {
                    viewHolder.answerDTextView.setTextColor(Color.WHITE);
                    viewHolder.answerDTextView.setBackgroundResource(android.R.color.holo_red_light);
                }

                // right ans
                if (mQuestionBanksObjectList.get(position).getCorrectAns().equalsIgnoreCase("option_a")) {
                    viewHolder.answerATextView.setTextColor(Color.WHITE);
                    viewHolder.answerATextView.setBackgroundResource(android.R.color.holo_green_light);
                } else if (mQuestionBanksObjectList.get(position).getCorrectAns().equalsIgnoreCase("option_b")) {
                    viewHolder.answerBTextView.setTextColor(Color.WHITE);
                    viewHolder.answerBTextView.setBackgroundResource(android.R.color.holo_green_light);
                } else if (mQuestionBanksObjectList.get(position).getCorrectAns().equalsIgnoreCase("option_c")) {
                    viewHolder.answerCTextView.setTextColor(Color.WHITE);
                    viewHolder.answerCTextView.setBackgroundResource(android.R.color.holo_green_light);
                } else if (mQuestionBanksObjectList.get(position).getCorrectAns().equalsIgnoreCase("option_d")) {
                    viewHolder.answerDTextView.setTextColor(Color.WHITE);
                    viewHolder.answerDTextView.setBackgroundResource(android.R.color.holo_green_light);
                }
            }
        }
    }


    @Override
    public int getCount() {
        return mQuestionBanksObjectList.size();
    }

    /**
     * View holder for data set
     */
    private class ViewHolder {
        View view3, view4;
        TextView questionTextView;
        TextView answerATextView;
        TextView answerBTextView;
        TextView answerCTextView;
        TextView answerDTextView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // The adapter is also responsible for removing the view.
        container.removeView(((ViewHolderMain) object).view);
    }

    @Override
    public int getItemPosition(Object object) {
        //int position = ((ViewHolderMain) object).position;
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((ViewHolderMain) object).view == view;
    }

    private static class ViewHolderMain {
        final View view;
        final int position;

        ViewHolderMain(View view, int position) {
            this.view = view;
            this.position = position;
        }
    }

    /**
     * Get Question object list with answer
     *
     * @return Question object list
     */
    public List<QuestionBank> getQuestionBanksObjectList() {
        return mQuestionBanksObjectList;
    }

    public int getTotalCorrectAnswer() {
        return totalCorrectAnswer;
    }

    public void resetCorrectAnswer(){
        totalCorrectAnswer = 0;
    }
}
