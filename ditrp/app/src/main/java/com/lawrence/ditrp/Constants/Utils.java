package com.lawrence.ditrp.Constants;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by mamta.lawrence on 11/6/2017.
 */

public class Utils {

    /**
     * Method to show toast using UI thread
     *
     * @param msgString message which needs to be display
     */
    public static void showToast(final Context context, final String msgString) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msgString, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
