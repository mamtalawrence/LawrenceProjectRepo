package com.lawrence.ditrp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
    private List<QuestionBank> questionBanks;

    public ViewPagerAdapter(Context context, List<QuestionBank> questionBanks) {
        this.context = context;
        this.questionBanks = questionBanks;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewHolder holder = new ViewHolder();
        View itemView = inflater.inflate(R.layout.viewpager_item, container, false);

        holder.view3 = (View) itemView.findViewById(R.id.view3);
        holder.view4 = (View) itemView.findViewById(R.id.view4);
        holder.questionTextView = (TextView) itemView.findViewById(R.id.text_question);
        holder.questionTextView.setText("Q. " + questionBanks.get(position).getQuestion());
        holder.answerATextView = (TextView) itemView.findViewById(R.id.text_answer_a);
        holder.answerATextView.setText(questionBanks.get(position).getOptionA());
        holder.answerBTextView = (TextView) itemView.findViewById(R.id.text_answer_b);
        holder.answerBTextView.setText(questionBanks.get(position).getOptionB());
        holder.answerCTextView = (TextView) itemView.findViewById(R.id.text_answer_c);
        if (!questionBanks.get(position).getOptionC().equals("")) {
            holder.answerCTextView.setText(questionBanks.get(position).getOptionC());
            holder.view3.setVisibility(View.VISIBLE);
        }
        holder.answerDTextView = (TextView) itemView.findViewById(R.id.text_answer_d);
        if (!questionBanks.get(position).getOptionD().equals("")) {
            holder.answerDTextView.setText(questionBanks.get(position).getOptionD());
            holder.view4.setVisibility(View.VISIBLE);
        }

        container.addView(itemView);
        return itemView;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return questionBanks.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public class ViewHolder {
        public View view3, view4;
        public TextView questionTextView;
        public TextView answerATextView;
        public TextView answerBTextView;
        public TextView answerCTextView;
        public TextView answerDTextView;
    }
}
