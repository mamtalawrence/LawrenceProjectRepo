package com.lawrence.ditrp.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.lawrence.ditrp.Constants.Utils;
import com.lawrence.ditrp.Enums.CommandType;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.command.Command;
import com.lawrence.ditrp.command.LoginCommand;
import com.lawrence.ditrp.command.WebCallCommand;

public class LoginActivity extends AppCompatActivity {

    private EditText mUserNameEditText, mPasswordEditText;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Utils.setCustomActionBar(this, getString(R.string.app_name), false, false);
        initializeView();
    }

    /**
     * Initialize variables
     */
    private void initializeView() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mUserNameEditText = (EditText) findViewById(R.id.input_uname);
        mPasswordEditText = (EditText) findViewById(R.id.input_password);
        Button loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptToLogin();
            }
        });
    }

    /**
     * login command execution
     */
    private void attemptToLogin() {
        String userName = mUserNameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            showToastWithRetry(this, getString(R.string.error_field_required), mCoordinatorLayout);
        } else if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            if (Utils.isNetworkAvailable(this)) {
                WebCallCommand control = new WebCallCommand();
                //Command loginCommand = new LoginCommand(this, "17P6933", "YWDLTVNS");
                Command loginCommand = new LoginCommand(this, userName, password);
                //login
                control.setCommand(loginCommand);
                control.executeCommand(CommandType.LOGIN);
            } else {
                showToastWithRetry(this, getString(R.string.no_network), mCoordinatorLayout);
            }
        }
    }

    /**
     * Method to show toast using UI thread
     *
     * @param msgString message which needs to be display
     */
    private void showToastWithRetry(final Context context, final String msgString, final View view) {
        Snackbar snackbar = Snackbar
                .make(view, msgString, Snackbar.LENGTH_LONG)
                .setAction(context.getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        attemptToLogin();
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
    }
}
