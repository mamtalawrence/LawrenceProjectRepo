package com.lawrence.ditrp.command;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import com.lawrence.ditrp.Constants.CommandConstant;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.activities.DashBoardActivity;
import com.lawrence.ditrp.dataModel.CustomSharedPreferences;
import com.lawrence.ditrp.dataModel.StudentData;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mamta.lawrence on 11/6/2017.
 */

public class LoginCommand implements Command {
    private Context mContext = null;
    private String mUserName = null;
    private String mPassword = null;
    CustomSharedPreferences sharedPreferences;

    public LoginCommand(Context context, String username, String password) {
        this.mContext = context;
        this.mUserName = username;
        this.mPassword = password;
    }

    @Override
    public void execute() {
        APIRequestBuilder apiRequestBuilder = new APIRequestBuilder.Builder().setContext(mContext).setCommandName
                (CommandConstant.MODULE_LOGIN).setURL(CommandConstant.SERVER_URL).setUserName(mUserName).setPassword
                (mPassword).setResponseListener(mResponseListener).build();
        new NetworkTask(apiRequestBuilder).execute();
    }

    /**
     * Responce
     */
    ResponseListener mResponseListener = new ResponseListener() {
        @Override
        public void onSuccess(String response) {
            JSONObject jsonBodyObject = null;
            try {
                jsonBodyObject = new JSONObject(response);
                if (jsonBodyObject.get("success").toString().equalsIgnoreCase("true")) {
                    // json parsing
                    Utils.showToast(mContext, jsonBodyObject.get("message").toString());
                    Gson gson = new Gson();
                    StudentData studentData = gson.fromJson(jsonBodyObject.get("data").toString(), StudentData.class);
                    Utils.saveStudentData(mContext, sharedPreferences, studentData);
                    Utils.saveQuestionIntoDB(mContext, studentData.getStudentCourses().get(0).getQuestionBank());
                    Utils.setPracticeListKey(mContext, studentData.getStudentCourses().get(0).getQuestionBank().size());
                    Utils.saveStudentCourseData(mContext, studentData.getStudentCourses(), sharedPreferences);
                    showDashBoardActivity();
                } else {
                    // fail handling
                    Utils.showToast(mContext, ((JSONObject) jsonBodyObject.get("errors")).get
                            ("message").toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Exception e) {
            Utils.showToast(mContext, e.getMessage());
        }
    };

    /**
     * Lunch Dashboard on login success
     */
    private void showDashBoardActivity() {
        CustomSharedPreferences customSharedPreferences = CustomSharedPreferences.getInstance(mContext);
        customSharedPreferences.saveBoolean(CommandConstant.IS_LOGIN_DONE, true);
        Intent dashBoardLunchIntent = new Intent((Activity) mContext, DashBoardActivity.class);
        mContext.startActivity(dashBoardLunchIntent);
        ((Activity) mContext).finish();
    }
}
