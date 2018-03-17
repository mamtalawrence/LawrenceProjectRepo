package com.lawrence.ditrp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.dataModel.QuestionBank;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ScoreCardAdapter extends RecyclerView.Adapter<ScoreCardViewHolder> {

    private List<QuestionBank> list = Collections.emptyList();
    private Context context;

    public ScoreCardAdapter(List<QuestionBank> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ScoreCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.scorecard_row_layout, parent, false);
        return new ScoreCardViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ScoreCardViewHolder holder, int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        String expectedAnswer = getExpectedAndActualAnswer(position, true);
        String actualAnswer = getExpectedAndActualAnswer(position, false);;
        holder.mViewQuestion.setText(String.format(Locale.ENGLISH, "Q%d. %s", position + 1, list.get(position)
                .getQuestion()));
        if (!TextUtils.isEmpty(actualAnswer)) {
            holder.mViewActualAnswer.setText(String.format("Your answer: %s", actualAnswer));
        } else {
            holder.mViewActualAnswer.setText(String.format("Your answer: %s", "No answer selected"));
        }
        holder.mViewExpectedAnswer.setText(String.format("Right answer: %s", expectedAnswer));
        if (!TextUtils.isEmpty(actualAnswer) && actualAnswer.equalsIgnoreCase(expectedAnswer)) {
            holder.mImageViewAnswerState.setImageResource(R.drawable.tick);
            holder.mViewActualAnswer.setTextColor(Color.parseColor("#badc52"));
            holder.mViewActualAnswer.setTextColor(R.color.color_green_light);
            holder.mViewExpectedAnswer.setVisibility(View.INVISIBLE);
        } else {
            holder.mImageViewAnswerState.setImageResource(R.drawable.wrong);
            holder.mViewActualAnswer.setTextColor(Color.BLACK);
            holder.mViewExpectedAnswer.setTextColor(Color.parseColor("#badc52"));
            holder.mViewExpectedAnswer.setTextColor(R.color.color_green_light);
            holder.mViewExpectedAnswer.setVisibility(View.VISIBLE);
        }

        //animation
        holder.mCardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in));
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * Getting the right answer details
     *
     * @param questionIndex index of question
     * @return answer
     */
    private String getExpectedAndActualAnswer(int questionIndex, boolean shouldGetExpectedAnswer) {
        String answers;
        if (shouldGetExpectedAnswer) {
            answers = list.get(questionIndex).getCorrectAns();
        } else {
            answers = list.get(questionIndex).getStudentAns();
        }
        String description = null;
        if (!TextUtils.isEmpty(answers)) {
            if (answers.equalsIgnoreCase("option_a")) {
                description = list.get(questionIndex).getOptionA();
            } else if (answers.equalsIgnoreCase("option_b")) {
                description = list.get(questionIndex).getOptionB();
            } else if (answers.equalsIgnoreCase("option_c")) {
                description = list.get(questionIndex).getOptionC();
            } else if (answers.equalsIgnoreCase("option_d")) {
                description = list.get(questionIndex).getOptionD();
            }
        }
        return description;
    }
}
