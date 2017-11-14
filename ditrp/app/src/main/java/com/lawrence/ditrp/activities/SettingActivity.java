package com.lawrence.ditrp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Utils.setCustomActionBar(this, getResources().getString(R.string.title_activity_settings));
    }
}
