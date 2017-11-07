package com.lawrence.ditrp.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Anagha.Mahajan on 07-Nov-17.
 */
public class StudentCourse {

    @SerializedName("COURSE_ID")
    @Expose
    private String mCourseId;

    @SerializedName("COURSE_NAME")
    @Expose
    private String mCourseName;

    @SerializedName("COURSE_CODE")
    @Expose
    private String mCourseCode;


    @SerializedName("COURSE_AWARD")
    @Expose
    private String mCourseAward;

    @SerializedName("COURSE_DURATION")
    @Expose
    private String mCourseDuration;

    @SerializedName("QUESTION_BANK")
    @Expose
    private List<QuestionBank> mQuestionBank = null;

    public String getCourseId() {
        return mCourseId;
    }

    public void setCourseId(String CourseId) {
        this.mCourseId = CourseId;
    }

    public String getCourseName() {
        return mCourseName;
    }

    public void setCourseName(String mCourseName) {
        this.mCourseName = mCourseName;
    }

    public String getCourseCode() {
        return mCourseCode;
    }

    public void setCourseCode(String mCourseCode) {
        this.mCourseCode = mCourseCode;
    }

    public String getCourseAward() {
        return mCourseAward;
    }

    public void setCourseAward(String mCourseAward) {
        this.mCourseAward = mCourseAward;
    }

    public String getCourseDuration() {
        return mCourseDuration;
    }

    public void setCourseDuration(String mCourseDuration) {
        this.mCourseDuration = mCourseDuration;
    }

    public List<QuestionBank> getQuestionBank() {
        return mQuestionBank;
    }

    public void setQuestionBank(List<QuestionBank> questionBank) {
        this.mQuestionBank = questionBank;
    }
}
