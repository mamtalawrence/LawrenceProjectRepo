package com.lawrence.ditrp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lawrence.ditrp.R;

/**
 * Created by Anagha.Mahajan on 15-Nov-17.
 */
public class SettingsViewHolder extends RecyclerView.ViewHolder {

    ImageView mSettingsLogo;
    TextView mSettingsTitle;
    TextView mSettingsDescription;

    public SettingsViewHolder(View itemView) {
        super(itemView);
       // mSettingsLogo = (ImageView) itemView.findViewById(R.id.settings_image);
        mSettingsTitle = (TextView) itemView.findViewById(R.id.setting_text);
        mSettingsDescription = (TextView) itemView.findViewById(R.id.settings_description);
    }
}
