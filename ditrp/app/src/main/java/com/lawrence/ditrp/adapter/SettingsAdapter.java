package com.lawrence.ditrp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.lawrence.ditrp.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

public class SettingsAdapter extends BaseAdapter {

    private LinkedHashMap<String, String> studentDataList;
    private LayoutInflater mLayoutInflater;
    private ArrayList mDataKey = new ArrayList();

    public SettingsAdapter(Context context, LinkedHashMap<String, String> studentDataList) {
        this.studentDataList = studentDataList;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        getAllKeys();
    }

    private void getAllKeys() {
        Set<String> keys = studentDataList.keySet();
        for (String key : keys) {
            mDataKey.add(key);
        }

    }

    @Override
    public int getCount() {
        return studentDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentDataList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SettingsViewHolder settingsViewHolder;
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.settings_item, null);
            settingsViewHolder = new SettingsViewHolder(convertView);
            convertView.setTag(settingsViewHolder);
        }
        settingsViewHolder = (SettingsViewHolder) convertView.getTag();

        settingsViewHolder.mSettingsTitle.setText(mDataKey.get(position).toString());
        settingsViewHolder.mSettingsDescription.setText(studentDataList.get(mDataKey.get(position)));
        return convertView;
    }

}
