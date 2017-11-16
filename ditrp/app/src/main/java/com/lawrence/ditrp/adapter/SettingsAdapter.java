package com.lawrence.ditrp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lawrence.ditrp.R;
import com.lawrence.ditrp.dataModel.StudentData;

import java.util.ArrayList;
import java.util.Iterator;

public class SettingsAdapter extends BaseAdapter {

    public Context mContext;
    private ArrayList<StudentData> studentDataList;
    LayoutInflater mLayoutInflater;

    public SettingsAdapter(Context mContext, ArrayList<StudentData> studentDataList) {
        this.mContext = mContext;
        this.studentDataList = studentDataList;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        settingsViewHolder.mSettingsTitle.setText("Name");
        Iterator iterator = studentDataList.iterator();
        while (iterator.hasNext()){
            StudentData studentData = (StudentData) iterator.next();
            System.out.print("Value....................."+studentData.getStudentDOB());
            settingsViewHolder.mSettingsDescription.setText(studentData.getStudentFirstName()+" "+studentData.getStudentMiddleName()+" "+studentData.getStudentLastName());
        }
        return convertView;
    }
}
