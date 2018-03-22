package com.lawrence.ditrp.command;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.lawrence.ditrp.Constants.CommandConstant;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.Enums.CommandType;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.activities.DashBoardActivity;
import com.lawrence.ditrp.dataModel.CustomSharedPreferences;
import com.lawrence.ditrp.dataModel.ItemsLibrary;
import com.lawrence.ditrp.dataModel.StudentData;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoginCommand implements Command {
    private Context mContext = null;
    private String mUserName = null;
    private String mPassword = null;
    /**
     * Response Listener to set the item data into library i.e., database.
     */
    private ResponseListener mResponseItemLibraryListener = new ResponseListener() {
        @Override
        public void onSuccess(String response) {
            JSONObject jsonBodyObject;
            try {
                jsonBodyObject = new JSONObject(response);
                if (!TextUtils.isEmpty(jsonBodyObject.toString())) {
                    // json parsing
                    Gson gson = new Gson();
                    Iterator<String> size = jsonBodyObject.keys();
                    List<ItemsLibrary> itemsLibraryList = new ArrayList<>();
                    while (size.hasNext()) {
                        itemsLibraryList.add(gson.fromJson(jsonBodyObject.get(size.next()).toString(),
                                ItemsLibrary.class));
                    }
                    Utils.saveItemsDetailInLibrary(mContext, itemsLibraryList);
                    showDashBoardActivity();
                } else {
                    // fail handling
                    Utils.showToast(mContext, mContext.getString(R.string.login_fail), false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Exception e) {
            Utils.showToast(mContext, e.getMessage(), false);
        }

        @Override
        public void onComplete(boolean status) {

        }
    };

    /**
     * Response Listener to set the data into shared preference and database.
     */
    private ResponseListener mResponseListener = new ResponseListener() {
        @Override
        public void onSuccess(String response) {
            JSONObject jsonBodyObject;
            try {
                jsonBodyObject = new JSONObject(response);
                if (jsonBodyObject.get("success").toString().equalsIgnoreCase("true")) {
                    // json parsing
                    Utils.showToast(mContext, jsonBodyObject.get("message").toString(), true);
                    Gson gson = new Gson();
                    StudentData studentData = gson.fromJson(jsonBodyObject.get("data").toString(), StudentData.class);
                    Utils.saveStudentData(mContext, studentData);
                    Utils.saveQuestionIntoDB(mContext, studentData.getStudentCourses().get(0).getQuestionBank());
                    Utils.setPracticeListKey(mContext, studentData.getStudentCourses().get(0).getQuestionBank().size());
                    Utils.saveStudentCourseData(mContext, studentData.getStudentCourses());
                } else {
                    // fail handling
                    Utils.showToast(mContext, ((JSONObject) jsonBodyObject.get("errors")).get
                            ("message").toString(), false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Exception e) {
            Utils.showToast(mContext, e.getMessage(), false);
        }

        @Override
        public void onComplete(boolean status) {
            if (status) {
                execute(CommandType.DATA_REQUEST);
            }
        }
    };

    public LoginCommand(Context context, String username, String password) {
        this.mContext = context;
        this.mUserName = username;
        this.mPassword = password;
    }

    @Override
    public void execute(CommandType commandType) {
        APIRequestBuilder apiRequestBuilder;
        switch (commandType) {
            case LOGIN:
                apiRequestBuilder = new APIRequestBuilder.Builder()
                        .setContext(mContext)
                        .setCommandName(CommandConstant.MODULE_LOGIN)
                        .setURL(CommandConstant.SERVER_URL)
                        .setUserName(mUserName)
                        .setPassword(mPassword)
                        .setResponseListener(mResponseListener).build();
                new NetworkTask(apiRequestBuilder, commandType).execute(CommandConstant.SERVER_URL);
                break;
            case DATA_REQUEST:
                apiRequestBuilder = new APIRequestBuilder.Builder()
                        .setContext(mContext)
                        .setCommandName(CommandConstant.MODULE_QUESTION_BANK)
                        .setURL(CommandConstant.QUESTION_LIBRARY_URL)
                        .setResponseListener(mResponseItemLibraryListener).build();
                new NetworkTask(apiRequestBuilder, commandType).execute(CommandConstant.QUESTION_LIBRARY_URL);
                break;
            default:
        }
    }

    /**
     * Lunch Dashboard on login success
     */
    private void showDashBoardActivity() {
        CustomSharedPreferences customSharedPreferences = CustomSharedPreferences.getInstance(mContext);
        customSharedPreferences.saveBoolean(CommandConstant.IS_LOGIN_DONE, true);
        Intent dashBoardLunchIntent = new Intent(mContext, DashBoardActivity.class);
        mContext.startActivity(dashBoardLunchIntent);
        ((Activity) mContext).finish();
    }
}
