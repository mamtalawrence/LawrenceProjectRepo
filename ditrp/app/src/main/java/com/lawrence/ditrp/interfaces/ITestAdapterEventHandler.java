package com.lawrence.ditrp.interfaces;

import android.widget.TextView;
import com.lawrence.ditrp.dataModel.QuestionBank;

import java.util.List;

public interface ITestAdapterEventHandler {

    void updateViewState(TextView textView, int color);

    boolean isAnswerSelected(int questionNumber);

    void handleItemSelection(int questionIndex);

    int getNumberOfCorrectAnswer();

    int getNumberOfIncorrectAnswer();

    void resetCorrectAnswer();

    /**
     * Get Question object list with answer
     *
     * @return Question object list
     */
    List<QuestionBank> getResultList();

}
