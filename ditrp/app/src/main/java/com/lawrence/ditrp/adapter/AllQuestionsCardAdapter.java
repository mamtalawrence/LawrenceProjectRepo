package com.lawrence.ditrp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.dataModel.QuestionBank;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class AllQuestionsCardAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    private List<QuestionBank> list = Collections.emptyList();
    private Context context;

    public AllQuestionsCardAdapter(List<QuestionBank> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_row_layout, parent, false);
        return new CommonViewHolder(v);

    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        String expectedAnswer = getCorrectAnswer(position);
        holder.mLogo.setVisibility(View.GONE);
        holder.mTitle.setText(String.format(Locale.ENGLISH, "Q%d. %s", position + 1, list.get(position)
                .getQuestion()));
        holder.mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        holder.mDescription.setText(String.format("A: %s", expectedAnswer));
        holder.mDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
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
    private String getCorrectAnswer(int questionIndex) {
        String expectedAnswer = list.get(questionIndex).getCorrectAns();
        String description = null;
        if (!TextUtils.isEmpty(expectedAnswer)) {
            if (expectedAnswer.equalsIgnoreCase("option_a")) {
                description = list.get(questionIndex).getOptionA();
            } else if (expectedAnswer.equalsIgnoreCase("option_b")) {
                description = list.get(questionIndex).getOptionB();
            } else if (expectedAnswer.equalsIgnoreCase("option_c")) {
                description = list.get(questionIndex).getOptionC();
            } else if (expectedAnswer.equalsIgnoreCase("option_d")) {
                description = list.get(questionIndex).getOptionD();
            }
        }
        return description;
    }
}
