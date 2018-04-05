package com.lawrence.ditrp.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.lawrence.ditrp.R;

class ScoreCardViewHolder extends RecyclerView.ViewHolder {

    CardView mCardView;
    ImageView mImageViewAnswerState;
    TextView mViewQuestion;
    TextView mViewActualAnswer;
    TextView mViewExpectedAnswer;

    ScoreCardViewHolder(View itemView) {
        super(itemView);
        mCardView = itemView.findViewById(R.id.cardView);
        mImageViewAnswerState = itemView.findViewById(R.id.imageView_answer_state);
        mViewQuestion = itemView.findViewById(R.id.text_view_question);
        mViewActualAnswer = itemView.findViewById(R.id.text_view_actual_answer);
        mViewExpectedAnswer = itemView.findViewById(R.id.text_view_expected_answer);
    }
}
