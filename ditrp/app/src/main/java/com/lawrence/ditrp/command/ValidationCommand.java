package com.lawrence.ditrp.command;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.lawrence.ditrp.Constants.CommandConstant;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.Enums.CommandType;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.activities.DashBoardActivity;
import com.lawrence.ditrp.dataModel.CustomSharedPreferences;

public class ValidationCommand implements Command {
    private Context mContext;
    private String mStudentId;
    private String mInstituteId;

    public ValidationCommand(Context context, String student_id, String institute_id) {
        mContext = context;
        mStudentId = student_id;
        mInstituteId = institute_id;
    }

    @Override

    public void execute(CommandType commandType) {
        APIRequestBuilder apiRequestBuilder;
        apiRequestBuilder = new APIRequestBuilder.Builder()
                .setContext(mContext)
                .setCommandName(CommandConstant.MODULE_VALIDATION)
                .setURL(CommandConstant.SERVER_URL)
                /*.setStudentId(mStudentId)
                .setInstituteId(mInstituteId)*/
                .setStudentId("8")
                .setInstituteId("5")
                .setResponseListener(mResponseListener).build();
        new NetworkTask(apiRequestBuilder, commandType).execute(CommandConstant.SERVER_URL);
    }

    /**
     * Response Listener to set the data into shared preference and database.
     */
    private ResponseListener mResponseListener = new ResponseListener() {
        @Override
        public void onSuccess(String response) {
            if (response.contains("3")) {
                showDashBoardActivity();
            } else {
                Utils.showToast(mContext, mContext.getString(R.string.invalid_user), false);
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
