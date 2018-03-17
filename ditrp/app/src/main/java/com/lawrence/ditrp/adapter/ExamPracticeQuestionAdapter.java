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
import java.util.Locale;

/**
 * Created by Anagha.Mahajan on 10-Nov-17.
 */
public class ExamPracticeQuestionAdapter extends PagerAdapter {

    private static final String EMPTY_STRING = "";
    private Context context;
    private LayoutInflater inflater;
    private List<QuestionBank> mQuestionBanksObjectList;
    private ViewHolder viewHolder;
    private int totalCorrectAnswer = 0;
    private int totalIncorrectAnswer = 0;

    public ExamPracticeQuestionAdapter(Context context, List<QuestionBank> questionBanks) {
        this.context = context;
        this.mQuestionBanksObjectList = questionBanks;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = inflater.inflate(R.layout.viewpager_item, container, false);
        viewHolder = new ViewHolder();

        viewHolder.mOptionView3 = itemView.findViewById(R.id.view3);
        viewHolder.mOptionView4 = itemView.findViewById(R.id.view4);
        viewHolder.mQuestionTextView = (TextView) itemView.findViewById(R.id.text_question);
        viewHolder.mQuestionTextView.setText(String.format(Locale.ENGLISH, "Q%d. %s", position + 1,
                mQuestionBanksObjectList.get(position).getQuestion()));

        viewHolder.mOptionTextViewA = (TextView) itemView.findViewById(R.id.text_answer_a);
        viewHolder.mOptionTextViewA.setText(String.format("A. %s", mQuestionBanksObjectList.get(position).getOptionA
                ()));

        viewHolder.mOptionTextViewB = (TextView) itemView.findViewById(R.id.text_answer_b);
        viewHolder.mOptionTextViewB.setText(String.format("B. %s", mQuestionBanksObjectList.get(position).getOptionB
                ()));

        viewHolder.mOptionTextViewC = (TextView) itemView.findViewById(R.id.text_answer_c);
        if (!TextUtils.isEmpty(mQuestionBanksObjectList.get(position).getOptionC())) {
            viewHolder.mOptionTextViewC.setText(String.format("C. %s", mQuestionBanksObjectList.get(position)
                    .getOptionC()));
            viewHolder.mOptionView3.setVisibility(View.VISIBLE);
            viewHolder.mOptionTextViewC.setVisibility(View.VISIBLE);
        }

        viewHolder.mOptionTextViewD = (TextView) itemView.findViewById(R.id.text_answer_d);
        if (!TextUtils.isEmpty(mQuestionBanksObjectList.get(position).getOptionD())) {
            viewHolder.mOptionTextViewD.setText(String.format("D. %s", mQuestionBanksObjectList.get(position)
                    .getOptionD()));
            viewHolder.mOptionView4.setVisibility(View.VISIBLE);
            viewHolder.mOptionTextViewD.setVisibility(View.VISIBLE);
        }

        //Check it attempted or not
        viewHolder.mOptionTextViewA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuestionBanksObjectList.get(position).setStudentAns("option_a");
                notifyDataSetChanged();
            }
        });

        viewHolder.mOptionTextViewB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuestionBanksObjectList.get(position).setStudentAns("option_b");
                notifyDataSetChanged();
            }
        });

        viewHolder.mOptionTextViewC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuestionBanksObjectList.get(position).setStudentAns("option_c");
                notifyDataSetChanged();
            }
        });

        viewHolder.mOptionTextViewD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuestionBanksObjectList.get(position).setStudentAns("option_d");
                notifyDataSetChanged();
            }
        });

        updateSelectedView(position);
        //handleItemSelection(position);
        //setSelected(position);
        container.addView(itemView);
        return new ViewHolderMain(itemView, position);

    }

    /**
     * Set highlighter fot the selected item
     *
     * @param questionIndex question index
     */
    private void updateSelectedView(int questionIndex) {
        String selectedAnswer = mQuestionBanksObjectList.get(questionIndex).getStudentAns();
        if (!TextUtils.isEmpty(selectedAnswer)) {
            if (selectedAnswer.equalsIgnoreCase("option_a")) {
                updateViewState(viewHolder.mOptionTextViewA);
            } else if (selectedAnswer.equalsIgnoreCase("option_b")) {
                updateViewState(viewHolder.mOptionTextViewB);
            } else if (selectedAnswer.equalsIgnoreCase("option_c")) {
                updateViewState(viewHolder.mOptionTextViewC);
            } else if (selectedAnswer.equalsIgnoreCase("option_d")) {
                updateViewState(viewHolder.mOptionTextViewD);
            }
        }
    }


    @Override
    public int getCount() {
        return mQuestionBanksObjectList.size();
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

    /**
     * Get Question object list with answer
     *
     * @return Question object list
     */
    public List<QuestionBank> getResultList() {
        return mQuestionBanksObjectList;
    }

    public void resetCorrectAnswer() {
        totalCorrectAnswer = 0;
    }

    private void updateViewState(TextView textView) {
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundResource(R.color.color_orange_light);
    }

    public boolean isAnswerSelected(int questionNumber) {
        return !TextUtils.isEmpty(mQuestionBanksObjectList.get(questionNumber).getStudentAns());
    }

    public void handleItemSelection(int questionIndex) {
        if (!isAnswerSelected(questionIndex)) {
            mQuestionBanksObjectList.get(questionIndex).setStudentAns(EMPTY_STRING);
            notifyDataSetChanged();
        }
        String selectedAnswerByStudent = mQuestionBanksObjectList.get(questionIndex).getStudentAns();
        String correctAns = mQuestionBanksObjectList.get(questionIndex).getCorrectAns();
        if (!TextUtils.isEmpty(selectedAnswerByStudent) && correctAns.equalsIgnoreCase(selectedAnswerByStudent)) {
            totalCorrectAnswer++;
        } else {
            totalIncorrectAnswer++;
        }
    }

    public int getNumberOfCorrectAnswer() {
        return totalCorrectAnswer;
    }

    public int getNumberOfIncorrectAnswer() {
        return totalIncorrectAnswer;
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
     * View holder for data set
     */
    private class ViewHolder {
        View mOptionView3, mOptionView4;
        TextView mQuestionTextView;
        TextView mOptionTextViewA;
        TextView mOptionTextViewB;
        TextView mOptionTextViewC;
        TextView mOptionTextViewD;
    }
}
