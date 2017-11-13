package com.lawrence.ditrp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lawrence.ditrp.R;

/**
 * Created by Anagha.Mahajan on 10-Nov-17.
 */
public class PracticeSessionActivity extends AppCompatActivity {

    private Button mSessionButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_session);
        mSessionButton = (Button)findViewById(R.id.button_session);
        mSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PracticeSessionActivity.this, ViewPagerActivity.class);
                startActivity(intent);
            }
        });
    }
}
