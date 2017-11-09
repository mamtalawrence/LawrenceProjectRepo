package com.lawrence.ditrp.Constants;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import com.lawrence.ditrp.dataModel.QuestionBank;
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

}
