package com.lawrence.ditrp.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.dataModel.CustomSharedPreferences;
import com.lawrence.ditrp.dataModel.ItemsLibrary;
import com.lawrence.ditrp.dataModel.QuestionBank;
import com.lawrence.ditrp.dataModel.StudentCourse;
import com.lawrence.ditrp.dataModel.StudentData;
import com.lawrence.ditrp.db.DatabaseHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by mamta.lawrence on 11/6/2017.
 */

public class Utils {

    private View mActionBarView;

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo()
                .isConnected();
    }

    /**
     * Method to show toast using UI thread
     *
     * @param msgString message which needs to be display
     */
    public static void showToast(final Context context, final String msgString, final boolean isSuccess) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(R.id.coordinatorLayout)
                        , msgString, Snackbar.LENGTH_SHORT);
                // Changing action button text color
                View sbView = snackbar.getView();
                if (isSuccess) {
                    sbView.setBackgroundColor(Color.GREEN);
                } else {
                    sbView.setBackgroundColor(Color.RED);
                }
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
            }
        });
    }

    /**
     * Save Questions into DB
     *
     * @param context
     * @param mQuestionBank
     */
    public static void saveQuestionIntoDB(Context context, List<QuestionBank> mQuestionBank) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.insertQuestionBank(mQuestionBank);

        // Don't forget to close database connection
        databaseHelper.closeDB();
    }


    public static List<QuestionBank> getQuestionIntoDB(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        List<QuestionBank> qb = databaseHelper.getAllQuestionBank();

        // Don't forget to close database connection
        databaseHelper.closeDB();

        return qb;
    }

    /**
     * Save Questions library into question library DB
     *
     * @param context       context
     * @param mItemsLibrary list object of ItemsLibrary
     */
    public static void saveItemsDetailInLibrary(Context context, List<ItemsLibrary> mItemsLibrary) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        databaseHelper.insertQuestionsInLibrary(mItemsLibrary);

        // Don't forget to close database connection
        databaseHelper.closeDB();
    }

    /**
     * Getting questions from the Question Library from the database.
     *
     * @param context context
     * @return question library list object
     */
    public static List<ItemsLibrary> getItemDetailsFromLibrary(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        List<ItemsLibrary> itemsLibraryList = databaseHelper.getAllQuestionsFromLibrary();

        // Don't forget to close database connection
        databaseHelper.closeDB();

        return itemsLibraryList;
    }

    public static void saveStudentData(Context mContext, CustomSharedPreferences sharedPreferences, StudentData
            studentData) {
        sharedPreferences = CustomSharedPreferences.getInstance(mContext);
        sharedPreferences.saveStringData(CommandConstant.STD_ID, studentData.getSTUDENTID());
        sharedPreferences.saveStringData(CommandConstant.STD_FIRST_NAME, studentData.getSTUDENTFNAME());
        sharedPreferences.saveStringData(CommandConstant.STD_MIDDLE_NAME, studentData.getSTUDENTMNAME());
        sharedPreferences.saveStringData(CommandConstant.STD_LAST_NAME, studentData.getSTUDENTLNAME());
        sharedPreferences.saveStringData(CommandConstant.STD_GENDER, studentData.getSTUDENTGENDER());
        sharedPreferences.saveStringData(CommandConstant.STD_EMAIL, studentData.getSTUDENTEMAIL());
        sharedPreferences.saveStringData(CommandConstant.STD_CODE, studentData.getSTUDENTCODE());
        sharedPreferences.saveStringData(CommandConstant.STD_DOB, studentData.getSTUDENTDOB());
        sharedPreferences.saveStringData(CommandConstant.STD_INSTITUTE_NAME, studentData.getINSTITUTENAME());
        sharedPreferences.saveStringData(CommandConstant.INSTITUTE_MOBILE_NO, studentData.getINSTITUTEMOBILE());
        sharedPreferences.saveStringData(CommandConstant.INSTITUTE_ADDRESS, getFullAddress(studentData));
    }

    private static String getFullAddress(StudentData studentData) {
        String address = "";
        if (!studentData.getINSTITUTEADDRESS().isEmpty()) {
            address = studentData.getINSTITUTEADDRESS();
        }
        if (!studentData.getINSTITUTECITY().isEmpty()) {
            address = address + ", " + studentData.getINSTITUTECITY();
        }
        if (!studentData.getINSTITUTESTATE().isEmpty()) {
            address = address + ", " + studentData.getINSTITUTESTATE();
        }
        if (!studentData.getINSTITUTEPOSTCODE().isEmpty()) {
            address = address + ", " + studentData.getINSTITUTEPOSTCODE();
        }
        return address;
    }

    public static void saveStudentCourseData(Context context, List<StudentCourse> studentCourses,
                                             CustomSharedPreferences sharedPreferences) {
        for (StudentCourse studentCourse : studentCourses) {
            insertCourse(context, sharedPreferences, studentCourse);
        }
    }

    private static void insertCourse(Context mContext, CustomSharedPreferences sharedPreferences, StudentCourse
            studentCourse) {
        sharedPreferences = CustomSharedPreferences.getInstance(mContext);
        sharedPreferences.saveStringData(CommandConstant.COURSE_ID, studentCourse.getCourseId());
        sharedPreferences.saveStringData(CommandConstant.COURSE_NAME, studentCourse.getCourseName());
        sharedPreferences.saveStringData(CommandConstant.COURSE_AWARD, studentCourse.getCourseAward());
        sharedPreferences.saveStringData(CommandConstant.COURSE_CODE, studentCourse.getCourseCode());
        sharedPreferences.saveStringData(CommandConstant.COURSE_DURATION, studentCourse.getCourseDuration());
    }

    /**
     * Get random 50 number between 0 and max number provided by user
     *
     * @param minRange
     * @param maxRange
     * @return list of random numbers
     */
    private static ArrayList<Integer> getRandom50(int minRange, int maxRange) {

        ArrayList<Integer> random = new ArrayList<Integer>();
        for (int i = 0; i < 1000; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(minRange, maxRange + 1);
            if (!random.contains(randomNum)) {
                random.add(randomNum);
                if (random.size() == 50) {
                    break;
                }
            }
        }
        return random;
    }

    /**
     * Added custom action bar to view
     *
     * @param context
     */
    public static void setCustomActionBar(Context context, @NonNull String titleText, boolean
            shouldVisibleQuestionCount, boolean shouldVisibleTimer) {
        ((AppCompatActivity) context).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity) context).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity) context).getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);

        ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
        Toolbar toolbar = (Toolbar) actionBar.getCustomView().getParent();
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.getContentInsetEnd();
        toolbar.setPadding(0, 0, 0, 0);

        View actionBarView;
        actionBarView = ((AppCompatActivity) context).getSupportActionBar().getCustomView();
        ((TextView) actionBarView.findViewById(R.id.action_bar_title)).setText(titleText);
        if (shouldVisibleQuestionCount) {
            actionBarView.findViewById(R.id.action_bar_question_count).setVisibility(View.VISIBLE);
        } else {
            actionBarView.findViewById(R.id.action_bar_question_count).setVisibility(View.GONE);
        }
        if (shouldVisibleTimer) {
            actionBarView.findViewById(R.id.action_bar_timer_layout).setVisibility(View.VISIBLE);
        } else {
            actionBarView.findViewById(R.id.action_bar_timer_layout).setVisibility(View.GONE);
        }
    }

    /**
     * Save practice and exam session question id list
     *
     * @param context
     * @param size
     */
    public static void setPracticeListKey(Context context, int size) {
        LinkedHashMap<Integer, ArrayList<Integer>> questionListKey = new LinkedHashMap<>();
        for (int i = 0; i < 33; i++) {
            questionListKey.put(i, getRandom50(0, size - 1));
        }
        Gson gson = new Gson();
        String jsonQuestionListKey = gson.toJson(questionListKey);
        Log.d("TAG", "jsonQuestionListKey = " + jsonQuestionListKey);
        CustomSharedPreferences.getInstance(context).saveStringData(CommandConstant.QUESTION_SESSION_LIST,
                jsonQuestionListKey);
    }

    /**
     * Get practice and exam session question id list
     *
     * @param context
     */
    public static LinkedHashMap<Integer, ArrayList<Integer>> getPracticeListKey(Context context) {

        LinkedHashMap<Integer, ArrayList<Integer>> questionListKey = new LinkedHashMap<Integer, ArrayList<Integer>>();
        Gson gson = new Gson();
        Type type = new TypeToken<LinkedHashMap<Integer, ArrayList<Integer>>>() {
        }.getType();
        questionListKey = gson.fromJson(CustomSharedPreferences.getInstance(context).getStringData(CommandConstant
                .QUESTION_SESSION_LIST), type);
        return questionListKey;
    }

    public static LinkedHashMap<String, String> getStudentData(Context context) {
        CustomSharedPreferences.getInstance(context);
        LinkedHashMap<String, String> studentDataList = new LinkedHashMap<>();
        CustomSharedPreferences sharedPreferences = CustomSharedPreferences.getInstance(context);
        studentDataList.put(context.getString(R.string.student_code), sharedPreferences.getStringData(CommandConstant
                .STD_CODE));
        studentDataList.put(context.getString(R.string.name), sharedPreferences.getStringData(CommandConstant
                .STD_FIRST_NAME));
        studentDataList.put(context.getString(R.string.middle_name), sharedPreferences.getStringData(CommandConstant
                .STD_MIDDLE_NAME));
        studentDataList.put(context.getString(R.string.last_name), sharedPreferences.getStringData(CommandConstant
                .STD_LAST_NAME));
        studentDataList.put(context.getString(R.string.gender), sharedPreferences.getStringData(CommandConstant
                .STD_GENDER));
        studentDataList.put(context.getString(R.string.email_id), sharedPreferences.getStringData(CommandConstant
                .STD_EMAIL));
        studentDataList.put(context.getString(R.string.dob), sharedPreferences.getStringData(CommandConstant.STD_DOB));
        studentDataList.put(context.getString(R.string.institute_name), sharedPreferences.getStringData(CommandConstant
                .STD_INSTITUTE_NAME));
        studentDataList.put(context.getString(R.string.address), sharedPreferences.getStringData(CommandConstant
                .INSTITUTE_ADDRESS));
        studentDataList.put(context.getString(R.string.mob_num), sharedPreferences.getStringData(CommandConstant
                .INSTITUTE_MOBILE_NO));

        return studentDataList;
    }

    public static void exitDialog(final Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((Activity) context).finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
