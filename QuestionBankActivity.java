package com.lawrence.ditrp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.R;

public class QuestionBankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_bank);
        Utils.setCustomActionBar(this, getResources().getString(R.string.question_bank));
    }
}
