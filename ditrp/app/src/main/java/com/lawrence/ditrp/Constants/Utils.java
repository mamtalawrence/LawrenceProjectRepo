package com.lawrence.ditrp.Constants;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.dataModel.CustomSharedPreferences;
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
    /**
     * Method to show toast using UI thread
     *
     * @param msgString message which needs to be display
     */
    public static void showToast(final Context context, final String msgString) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msgString, Toast.LENGTH_SHORT).show();
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

    public static void saveStudentData(Context mContext, CustomSharedPreferences sharedPreferences, StudentData
            studentData) {
        sharedPreferences = CustomSharedPreferences.getInstance(mContext);
        sharedPreferences.saveStringData(CommandConstant.STD_ID, studentData.getStudentID());
        sharedPreferences.saveStringData(CommandConstant.STD_FIRST_NAME, studentData.getStudentFirstName());
        sharedPreferences.saveStringData(CommandConstant.STD_MIDDLE_NAME, studentData.getStudentMiddleName());
        sharedPreferences.saveStringData(CommandConstant.STD_LAST_NAME, studentData.getStudentLastName());
        sharedPreferences.saveStringData(CommandConstant.STD_GENDER, studentData.getStudentGender());
        sharedPreferences.saveStringData(CommandConstant.STD_EMAIL, studentData.getStudentEmail());
        sharedPreferences.saveStringData(CommandConstant.STD_CODE, studentData.getStudentCode());
        sharedPreferences.saveStringData(CommandConstant.STD_DOB, studentData.getStudentDOB());
        sharedPreferences.saveStringData(CommandConstant.STD_MOBILE_NO, studentData.getStudentMobileNo());
        sharedPreferences.saveStringData(CommandConstant.STD_INSTITUTE_NAME, studentData.getInstituteName());
    }

    public static void saveStudentCourseData(Context context, List<StudentCourse> studentCourses,
                                             CustomSharedPreferences sharedPreferences) {
        for (int i = 0; i < studentCourses.size(); i++) {
            insertCourse(context, sharedPreferences, studentCourses.get(i));
        }
    }

    public static void insertCourse(Context mContext, CustomSharedPreferences sharedPreferences, StudentCourse
            studentCourse) {
        sharedPreferences = CustomSharedPreferences.getInstance(mContext);
        sharedPreferences.saveStringData(CommandConstant.COURSE_ID, studentCourse.getCourseId());
        sharedPreferences.saveStringData(CommandConstant.COURSE_NAME, studentCourse.getCourseName());
        sharedPreferences.saveStringData(CommandConstant.COURSE_AWARD, studentCourse.getCourseAward());
        sharedPreferences.saveStringData(CommandConstant.COURSE_CODE, studentCourse.getCourseCode());
        sharedPreferences.saveStringData(CommandConstant.COURSE_DURATION, studentCourse.getCourseDuration());
    }

    /**
     * Get random 60 number between 0 and max number provided by user
     *
     * @param minRange
     * @param maxRange
     * @return list of random numbers
     */
    public static ArrayList<Integer> getRandom60(int minRange, int maxRange) {

        ArrayList<Integer> random = new ArrayList();
        for (int i = 0; i < 1000; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(minRange, maxRange + 1);
            if (!random.contains(randomNum)) {
                random.add(randomNum);
                if (random.size() == 60) {
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
    public static void setCustomActionBar(Context context, @NonNull String titletext, boolean
            shouldVisibleQuestionCount, boolean shouldVisibleTimer) {
        ((AppCompatActivity) context).getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ((AppCompatActivity) context).getSupportActionBar().setDisplayShowCustomEnabled(true);
        ((AppCompatActivity) context).getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View actionBarView;
        actionBarView = ((AppCompatActivity) context).getSupportActionBar().getCustomView();
        ((TextView) actionBarView.findViewById(R.id.action_bar_title)).setText(titletext);
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
            questionListKey.put(i, getRandom60(0, size - 1));
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
        studentDataList.put("Student ID", sharedPreferences.getStringData(CommandConstant.STD_ID));
        studentDataList.put("Name", sharedPreferences.getStringData(CommandConstant.STD_FIRST_NAME));
        studentDataList.put("Middle Name", sharedPreferences.getStringData(CommandConstant.STD_MIDDLE_NAME));
        studentDataList.put("Last Name", sharedPreferences.getStringData(CommandConstant.STD_LAST_NAME));
        studentDataList.put("Gender", sharedPreferences.getStringData(CommandConstant.STD_GENDER));
        studentDataList.put("Email ID", sharedPreferences.getStringData(CommandConstant.STD_EMAIL));
        studentDataList.put("Student Code", sharedPreferences.getStringData(CommandConstant.STD_CODE));
        studentDataList.put("Date OF Birth", sharedPreferences.getStringData(CommandConstant.STD_DOB));
        studentDataList.put("Mobile Number", sharedPreferences.getStringData(CommandConstant.STD_MOBILE_NO));
        studentDataList.put("Institute Name", sharedPreferences.getStringData(CommandConstant
                .STD_INSTITUTE_NAME));

        return studentDataList;
    }
}
