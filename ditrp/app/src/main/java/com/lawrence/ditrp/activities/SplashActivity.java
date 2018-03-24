package com.lawrence.ditrp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.lawrence.ditrp.Constants.CommandConstant;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.Enums.CommandType;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.command.Command;
import com.lawrence.ditrp.command.ValidationCommand;
import com.lawrence.ditrp.command.WebCallCommand;
import com.lawrence.ditrp.dataModel.CustomSharedPreferences;

import static com.lawrence.ditrp.Constants.CommandConstant.INSTITUTE_ID;
import static com.lawrence.ditrp.Constants.CommandConstant.STD_ID;

public class SplashActivity extends AppCompatActivity {
    CustomSharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mSharedPreferences = CustomSharedPreferences.getInstance(SplashActivity.this);
        if (mSharedPreferences.getBoolean(CommandConstant.IS_LOGIN_DONE)) {
            validationCheck();
        } else {
            launchLoginScreen();
        }

    }

    private void validationCheck() {
        if (Utils.isNetworkAvailable(this) && !mSharedPreferences.getStringData(INSTITUTE_ID).isEmpty()
                && !mSharedPreferences.getStringData(STD_ID).isEmpty()) {
            WebCallCommand control = new WebCallCommand();

            Command validationCommand = new ValidationCommand(this, mSharedPreferences.getStringData(STD_ID),
                    mSharedPreferences.getStringData(INSTITUTE_ID));
            control.setCommand(validationCommand);
            control.executeCommand(CommandType.VALIDATION);
        } else {
            launchDashBoardScreen();
        }
    }

    /**
     * Lunch login screen
     */
    public void launchLoginScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 1000);
    }

    /**
     * Launch DashBoard screen
     */
    public void launchDashBoardScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, DashBoardActivity.class);
                startActivity(i);
                finish();
            }
        }, 1000);
    }
}
