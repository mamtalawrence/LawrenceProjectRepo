package com.lawrence.ditrp.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QuestionBank implements Serializable {
    @SerializedName("QUESTION_ID")
    @Expose
    private String mQuestionId;

    @SerializedName("COURSE_ID")
    @Expose
    private String mCourseId;

    @SerializedName("QUESTION")
    @Expose
    public String mQuestion;

    @SerializedName("IMAGE")
    @Expose
    private String mImage;

    @SerializedName("OPTION_A")
    @Expose
    private String mOptionA;

    @SerializedName("OPTION_B")
    @Expose
    private String mOptionB;

    @SerializedName("OPTION_C")
    @Expose
    private String mOptionC;

    @SerializedName("OPTION_D")
    @Expose
    private String mOptionD;

    @SerializedName("CORRECT_ANS")
    @Expose
    private String mCorrectAns;

    private String mStudentAns;

    public String getStudentAns() {
        return mStudentAns;
    }

    public void setStudentAns(String mStudentAns) {
        this.mStudentAns = mStudentAns;
    }

    public String getQuestionID() {
        return mQuestionId;
    }

    public void setQuestionID(String mQuestionId) {
        this.mQuestionId = mQuestionId;
    }

    public String getCourseID() {
        return mCourseId;
    }

    public void setCourseID(String mCourseId) {
        this.mCourseId = mCourseId;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }

    public String getOptionA() {
        return mOptionA;
    }

    public void setOptionA(String mOptionA) {
        this.mOptionA = mOptionA;
    }

    public String getOptionB() {
        return mOptionB;
    }

    public void setOptionB(String mOptionB) {
        this.mOptionB = mOptionB;
    }

    public String getOptionC() {
        return mOptionC;
    }

    public void setOptionC(String mOptionC) {
        this.mOptionC = mOptionC;
    }

    public String getOptionD() {
        return mOptionD;
    }

    public void setOptionD(String mOptionD) {
        this.mOptionD = mOptionD;
    }

    public String getCorrectAns() {
        return mCorrectAns;
    }

    public void setCorrectAns(String mCorrectAns) {
        this.mCorrectAns = mCorrectAns;
    }

}
