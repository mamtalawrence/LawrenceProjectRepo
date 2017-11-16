package com.lawrence.ditrp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.lawrence.ditrp.Constants.CommandConstant;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.dataModel.CustomSharedPreferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = null;
                CustomSharedPreferences customSharedPreferences = CustomSharedPreferences.getInstance(SplashActivity
                        .this);
                if (customSharedPreferences.getBoolean(CommandConstant.IS_LOGIN_DONE)) {
                    i = new Intent(SplashActivity.this, DashBoardActivity.class);
                } else {
                    i = new Intent(SplashActivity.this, loginActivity.class);
                }

                startActivity(i);
                finish();
            }
        }, 1000);
    }
}
