package com.lawrence.ditrp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.lawrence.ditrp.dataModel.ItemsLibrary;
import com.lawrence.ditrp.dataModel.QuestionBank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mamta.lawrence on 11/9/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "questionManager";

    // Table Names
    private static final String QUESTION_BANK = "question_bank";
    private static final String PRACTICE_QUESTION = "practice_question";
    private static final String QUESTION_LIBRARY = "question_library";

    // Common column names
    private static final String KEY_ID = "id";

    // column names
    private static final String QUESTION_ID = "question_id";
    private static final String COURSE_ID = "course_id";
    private static final String QUESTION = "question";
    private static final String IMAGE = "image";
    private static final String OPTION_A = "option_a";
    private static final String OPTION_B = "option_b";
    private static final String OPTION_C = "option_c";
    private static final String OPTION_D = "option_d";
    private static final String CORRECT_ANS = "correct_ans";
    private static final String STUDENT_ANS = "student_ans";
    // column names
    private static final String QUESTION_TITLE = "title";
    private static final String QUESTION_DESC = "description";

    // Table Create Statements
    // QUESTION_BANK table create statement
    private static final String CREATE_TABLE_QUESTION_BANK = "CREATE TABLE IF NOT EXISTS "
            + QUESTION_BANK + "(" + KEY_ID + " INTEGER PRIMARY KEY," + QUESTION_ID + " INTEGER," + COURSE_ID + " " +
            "INTEGER," + QUESTION + " TEXT," + IMAGE + " TEXT," + OPTION_A + " TEXT," + OPTION_B + " TEXT," +
            OPTION_C + " TEXT," + OPTION_D + " TEXT," + CORRECT_ANS + " TEXT" + ")";

    // PRACTICE_QUESTION table create statement
    private static final String CREATE_TABLE_PRACTICE_QUESTION = "CREATE TABLE IF NOT EXISTS "
            + PRACTICE_QUESTION + "(" + KEY_ID + " INTEGER PRIMARY KEY," + QUESTION_ID + " INTEGER," + COURSE_ID + " " +
            "INTEGER," + QUESTION + " TEXT," + IMAGE + " TEXT," + OPTION_A + " TEXT," + OPTION_B + " TEXT," +
            OPTION_C + " TEXT," + OPTION_D + " TEXT," + CORRECT_ANS + " TEXT," + STUDENT_ANS + " TEXT" + ")";

    // PRACTICE_QUESTION table create statement
    private static final String CREATE_TABLE_QUESTION_LIBRARY = "CREATE TABLE IF NOT EXISTS "
            + QUESTION_LIBRARY + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + IMAGE + " TEXT," +
            QUESTION_TITLE + " TEXT," + QUESTION_DESC + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_QUESTION_BANK);
        db.execSQL(CREATE_TABLE_PRACTICE_QUESTION);
        db.execSQL(CREATE_TABLE_QUESTION_LIBRARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + QUESTION_BANK);
        db.execSQL("DROP TABLE IF EXISTS " + PRACTICE_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + QUESTION_LIBRARY);

        // create new tables
        onCreate(db);
    }

    /**
     * Insert data into question bank
     *
     * @param questionBank data object
     */
    private void insertQuestionBank(QuestionBank questionBank) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QUESTION_ID, questionBank.getQuestionID());
        values.put(COURSE_ID, questionBank.getCourseID());
        values.put(QUESTION, questionBank.getQuestion());
        values.put(IMAGE, questionBank.getImage());
        values.put(OPTION_A, questionBank.getOptionA());
        values.put(OPTION_B, questionBank.getOptionB());
        values.put(OPTION_C, questionBank.getOptionC());
        values.put(OPTION_D, questionBank.getOptionD());
        values.put(CORRECT_ANS, questionBank.getCorrectAns());

        // insert row
        db.insert(QUESTION_BANK, null, values);
    }

    /**
     * Insert data into question bank
     *
     * @param
     */
    public void insertQuestionBank(List<QuestionBank> questionBank) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + QUESTION_BANK);
        for (QuestionBank aQuestionBank : questionBank) {
            insertQuestionBank(aQuestionBank);
        }
    }

    /**
     * Get QuestionBank single object by key
     *
     * @param id by key
     * @return QuestionBank object
     */
    public QuestionBank getQuestionBank(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + QUESTION_BANK + " WHERE " + QUESTION_ID + " = " + id;
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        QuestionBank questionBank = new QuestionBank();

        if (c.moveToFirst()) {
            do {
                //questionBank. setQuestionID(c.getInt(c.getColumnIndex(KEY_ID)));
                questionBank.setQuestionID(c.getInt(c.getColumnIndex(QUESTION_ID)) + "");
                questionBank.setCourseID(c.getInt(c.getColumnIndex(COURSE_ID)) + "");
                questionBank.setQuestion(c.getString(c.getColumnIndex(QUESTION)));
                questionBank.setImage(c.getString(c.getColumnIndex(IMAGE)));
                questionBank.setOptionA(c.getString(c.getColumnIndex(OPTION_A)));
                questionBank.setOptionB(c.getString(c.getColumnIndex(OPTION_B)));
                questionBank.setOptionC(c.getString(c.getColumnIndex(OPTION_C)));
                questionBank.setOptionD(c.getString(c.getColumnIndex(OPTION_D)));
                questionBank.setCorrectAns(c.getString(c.getColumnIndex(CORRECT_ANS)));

                return questionBank;
            } while (c.moveToNext());
        }

        return questionBank;
    }

    /**
     * Get QuestionBank list
     *
     * @return QuestionBank object list
     */
    public List<QuestionBank> getAllQuestionBank() {
        List<QuestionBank> questionBankArrayList = new ArrayList<QuestionBank>();
        String selectQuery = "SELECT  * FROM " + QUESTION_BANK;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                QuestionBank questionBank = new QuestionBank();
                //questionBank. setQuestionID(c.getInt(c.getColumnIndex(KEY_ID)));
                questionBank.setQuestionID(c.getInt(c.getColumnIndex(QUESTION_ID)) + "");
                questionBank.setCourseID(c.getInt(c.getColumnIndex(COURSE_ID)) + "");
                questionBank.setQuestion(c.getString(c.getColumnIndex(QUESTION)));
                questionBank.setImage(c.getString(c.getColumnIndex(IMAGE)));
                questionBank.setOptionA(c.getString(c.getColumnIndex(OPTION_A)));
                questionBank.setOptionB(c.getString(c.getColumnIndex(OPTION_B)));
                questionBank.setOptionC(c.getString(c.getColumnIndex(OPTION_C)));
                questionBank.setOptionD(c.getString(c.getColumnIndex(OPTION_D)));
                questionBank.setCorrectAns(c.getString(c.getColumnIndex(CORRECT_ANS)));

                // adding to todo list
                questionBankArrayList.add(questionBank);
            } while (c.moveToNext());
        }
        c.close();
        return questionBankArrayList;
    }

    /**
     * Update    questionBank
     *
     * @param questionBank
     * @return success or failure
     */
    public int updateQuestionBank(QuestionBank questionBank) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QUESTION_ID, questionBank.getQuestionID());

        // updating row
        return db.update(QUESTION_BANK, values, KEY_ID + " = ?",
                new String[]{String.valueOf(questionBank.getQuestionID())});
    }

    /**
     * Delete by id
     *
     * @param id
     */
    public void deleteQuestionBank(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(QUESTION_BANK, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    /**
     * Insert data into question Library
     *
     * @param itemsLibrary list of questions to insert into library
     */
    public void insertQuestionsInLibrary(List<ItemsLibrary> itemsLibrary) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + QUESTION_LIBRARY);
        for (ItemsLibrary aItemsLibrary : itemsLibrary) {
            fillQuestionLibrary(aItemsLibrary);
        }
    }

    /**
     * Insert data into question bank
     *
     * @param itemsLibrary data object
     */
    private void fillQuestionLibrary(ItemsLibrary itemsLibrary) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(IMAGE, itemsLibrary.getImage());
        values.put(QUESTION_TITLE, itemsLibrary.getQuestionTitle());
        values.put(QUESTION_DESC, itemsLibrary.getDescription());
        // insert row
        db.insert(QUESTION_LIBRARY, null, values);
    }

    /**
     * Get Question list from the ItemsLibrary
     *
     * @return ItemsLibrary object list
     */
    public List<ItemsLibrary> getAllQuestionsFromLibrary() {
        List<ItemsLibrary> itemsLibraryList = new ArrayList<ItemsLibrary>();
        String selectQuery = "SELECT  * FROM " + QUESTION_LIBRARY;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItemsLibrary itemsLibrary = new ItemsLibrary();
                //questionBank. setQuestionID(c.getInt(c.getColumnIndex(KEY_ID)));
                itemsLibrary.setImage(cursor.getString(cursor.getColumnIndex(IMAGE)));
                itemsLibrary.setQuestionTitle(cursor.getString(cursor.getColumnIndex(QUESTION_TITLE)));
                itemsLibrary.setDescription(cursor.getString(cursor.getColumnIndex(QUESTION_DESC)));

                // adding all the questions from library into the question list
                itemsLibraryList.add(itemsLibrary);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemsLibraryList;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public void deleteTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + QUESTION_BANK);
        db.execSQL("DROP TABLE IF EXISTS " + PRACTICE_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + QUESTION_LIBRARY);
    }
}


