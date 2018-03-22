package com.lawrence.ditrp.command;

import android.content.Context;
import android.text.TextUtils;


class APIRequestBuilder {
    /**
     * Context of activity
     */
    Context mContext;
    /**
     * This variable will be required for cancelling Asyntask.
     */
    String mCommandName = null;
    /**
     * URL of API
     */
    String mURL;
    /**
     * userName of user name
     */
    String mUserName = null;
    /**
     * password of password
     */
    String mPassword = null;

    /**
     * Response Listener
     */
    ResponseListener mResponseListener;

    /**
     * Student Id
     */
    String mStudentId = null;

    /**
     * Institute id
     */
    String mInstituteId = null;

    private APIRequestBuilder(Builder builder) {
        this.mContext = builder.mContext;
        this.mCommandName = builder.mCommandName;
        this.mURL = builder.mURL;
        this.mUserName = builder.mUserName;
        this.mPassword = builder.mPassword;
        this.mResponseListener = builder.mResponseListener;
        this.mStudentId = builder.mStudentId;
        this.mInstituteId = builder.mInstituteId;
    }

    static class Builder {
        Context mContext;
        ResponseListener mResponseListener;
        private String mCommandName = null;
        private String mURL = null;
        private String mUserName = null;
        private String mPassword = null;
        private String mStudentId = null;
        private String mInstituteId = null;

        Builder() {

        }

        /*public Builder(APIRequestBuilder apiRequestBuilder) {
            this.mContext = apiRequestBuilder.mContext;
            this.mCommandName = apiRequestBuilder.mCommandName;
            this.mURL = apiRequestBuilder.mURL;
            this.mUserName = apiRequestBuilder.mUserName;
            this.mPassword = apiRequestBuilder.mPassword;
            this.mResponseListener = apiRequestBuilder.mResponseListener;
        }*/

        /**
         * App Context
         */
        Builder setContext(Context context) {
            this.mContext = context;
            return this;
        }

        /**
         * ResponseListener
         */
        Builder setResponseListener(ResponseListener responseListener) {
            this.mResponseListener = responseListener;
            return this;
        }

        /**
         * This method must be used to set the command name.
         * This method be compulsory set.
         *
         * @param mCommandName Command Name of API Command Class
         * @return {@link Builder} Object with CommandName set.
         */
        Builder setCommandName(String mCommandName) {
            this.mCommandName = mCommandName;
            return this;
        }

        /**
         * This method must be used to set url for the API.
         *
         * @param url API URL in form of {@link String}
         * @return {@link Builder} Object with URL set.
         */
        Builder setURL(String url) {
            this.mURL = url;
            return this;
        }

        /**
         * This method must be used to set userName for API Request.
         *
         * @param userName API request userName in form of {@link String}
         * @return {@link Builder} Object with userName set.
         */
        Builder setUserName(String userName) {
            this.mUserName = userName;
            return this;
        }

        /**
         * This method must be used to set password for API Request.
         *
         * @param password API request password in form of {@link String}
         * @return {@link Builder} Object with password set.
         */
        Builder setPassword(String password) {
            this.mPassword = password;
            return this;
        }

        Builder setStudentId(String studentId) {
            this.mStudentId = studentId;
            return this;
        }

        Builder setInstituteId(String instituteId) {
            this.mInstituteId = instituteId;
            return this;
        }

        /**
         * This method will build the API Request based on inputs provided.
         * This method will throw IllegalArgumentException if mURL == NULL and  mNetworkClassType is not set and
         * mCommandName is NULL
         *
         * @return {@link APIRequestBuilder} with required fields set to perform network task.
         */
        APIRequestBuilder build() {
            if (!TextUtils.isEmpty(mURL) && mCommandName != null) {
                return new APIRequestBuilder(this);
            } else {
                throw new IllegalArgumentException("unexpected request");
            }
        }


    }
}
