package com.lawrence.ditrp.Constants;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.lawrence.ditrp.dataModel.CustomSharedPreferences;
import com.lawrence.ditrp.dataModel.QuestionBank;
import com.lawrence.ditrp.dataModel.StudentCourse;
import com.lawrence.ditrp.dataModel.StudentData;
import com.lawrence.ditrp.db.DatabaseHelper;

import java.util.List;

/**
 * Created by mamta.lawrence on 11/6/2017.
 */

public class Utils {

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

    public static void saveStudentData(Context mContext,CustomSharedPreferences sharedPreferences, StudentData studentData ){
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

    public static void saveStudentCourseData(Context context,List<StudentCourse> studentCourses,CustomSharedPreferences sharedPreferences){
        for (int i = 0; i < studentCourses.size(); i++) {
            insertCourse(context,sharedPreferences,studentCourses.get(i));
        }
    }

    public static void insertCourse(Context mContext,CustomSharedPreferences sharedPreferences,StudentCourse studentCourse){
        sharedPreferences = CustomSharedPreferences.getInstance(mContext);
        sharedPreferences.saveStringData(CommandConstant.COURSE_ID, studentCourse.getCourseId());
        sharedPreferences.saveStringData(CommandConstant.COURSE_NAME, studentCourse.getCourseName());
        sharedPreferences.saveStringData(CommandConstant.COURSE_AWARD, studentCourse.getCourseAward());
        sharedPreferences.saveStringData(CommandConstant.COURSE_CODE, studentCourse.getCourseCode());
        sharedPreferences.saveStringData(CommandConstant.COURSE_DURATION, studentCourse.getCourseDuration());
    }

}
