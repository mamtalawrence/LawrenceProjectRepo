package com.lawrence.ditrp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.lawrence.ditrp.R;

class SettingsViewHolder extends RecyclerView.ViewHolder {

    TextView mSettingsTitle;
    TextView mSettingsDescription;

    SettingsViewHolder(View itemView) {
        super(itemView);
        mSettingsTitle = itemView.findViewById(R.id.setting_text);
        mSettingsDescription = itemView.findViewById(R.id.settings_description);
    }
}
