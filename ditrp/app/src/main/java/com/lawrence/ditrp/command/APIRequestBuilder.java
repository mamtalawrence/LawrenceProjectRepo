package com.lawrence.ditrp.command;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by mamta.lawrence on 11/6/2017.
 */

public class APIRequestBuilder {
    /**
     * Context of activity
     */
    public Context mContext;
    /**
     * This variable will be required for cancelling Asyntask.
     */
    public String mCommandName = null;
    /**
     * URL of API
     */
    public String mURL = null;
    /**
     * userName of user name
     */
    public String mUserName = null;
    /**
     * password of password
     */
    public String mPassword = null;

    /**
     * Responce listener
     *
     * @param builder
     */
    public ResponseListener mResponseListener;

    public APIRequestBuilder(Builder builder) {
        this.mContext = builder.mContext;
        this.mCommandName = builder.mCommandName;
        this.mURL = builder.mURL;
        this.mUserName = builder.mUserName;
        this.mPassword = builder.mPassword;
        this.mResponseListener = builder.mResponseListener;
    }

    public static class Builder {
        private String mCommandName = null;
        private String mURL = null;
        private String mUserName = null;
        private String mPassword = null;
        public Context mContext;
        public ResponseListener mResponseListener;

        public Builder() {

        }

        public Builder(APIRequestBuilder apiRequestBuilder) {
            this.mContext = apiRequestBuilder.mContext;
            this.mCommandName = apiRequestBuilder.mCommandName;
            this.mURL = apiRequestBuilder.mURL;
            this.mUserName = apiRequestBuilder.mUserName;
            this.mPassword = apiRequestBuilder.mPassword;
            this.mResponseListener = apiRequestBuilder.mResponseListener;
        }

        /**
         * App Context
         */
        public Builder setContext(Context context) {
            this.mContext = context;
            return this;
        }

        /**
         * ResponseListener
         */
        public Builder setResponseListener(ResponseListener responseListener) {
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
        public Builder setCommandName(String mCommandName) {
            this.mCommandName = mCommandName;
            return this;
        }

        /**
         * This method must be used to set url for the API.
         *
         * @param url API URL in form of {@link String}
         * @return {@link Builder} Object with URL set.
         */
        public Builder setURL(String url) {
            this.mURL = url;
            return this;
        }

        /**
         * This method must be used to set userName for API Request.
         *
         * @param userName API request userName in form of {@link String}
         * @return {@link Builder} Object with userName set.
         */
        public Builder setUserName(String userName) {
            this.mUserName = userName;
            return this;
        }

        /**
         * This method must be used to set password for API Request.
         *
         * @param password API request password in form of {@link String}
         * @return {@link Builder} Object with password set.
         */
        public Builder setPassword(String password) {
            this.mPassword = password;
            return this;
        }

        /**
         * This method will build the API Request based on inputs provided.
         * This method will throw IllegalArgumentException if mURL == NULL and  mNetworkClassType is not set and
         * mCommandName is NULL
         *
         * @return {@link APIRequestBuilder} with required fields set to perform network task.
         */
        public APIRequestBuilder build() {
            if (!TextUtils.isEmpty(mURL) && mCommandName != null) {
                return new APIRequestBuilder(this);
            } else {
                throw new IllegalArgumentException("unexpected request");
            }
        }
    }
}
