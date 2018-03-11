package com.lawrence.ditrp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.adapter.SettingsAdapter;

import java.util.LinkedHashMap;

public class SettingActivity extends AppCompatActivity {

    private ListView mSettingsListView;
    private LinkedHashMap<String, String> studentDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Utils.setCustomActionBar(this, getResources().getString(R.string.title_activity_settings));
        mSettingsListView = (ListView) findViewById(R.id.settings_listview);
        studentDataList = Utils.getStudentData(getApplicationContext());
        SettingsAdapter settingsAdapter = new SettingsAdapter(this, studentDataList);
        mSettingsListView.setAdapter(settingsAdapter);
    }
}
