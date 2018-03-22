package com.lawrence.ditrp.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemsLibrary implements Serializable {


    @SerializedName("image")
    @Expose
    private String mImage;

    @SerializedName("title")
    @Expose
    private String mQuestionTitle;

    @SerializedName("description")
    @Expose
    private String mDescription;

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }

    public String getQuestionTitle() {
        return mQuestionTitle;
    }

    public void setQuestionTitle(String mQuestionTitle) {
        this.mQuestionTitle = mQuestionTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
