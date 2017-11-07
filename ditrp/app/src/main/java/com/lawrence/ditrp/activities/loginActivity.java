package com.lawrence.ditrp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.command.Command;
import com.lawrence.ditrp.command.LoginCommand;
import com.lawrence.ditrp.command.WebCallCommand;

public class loginActivity extends AppCompatActivity {

    EditText mUserNameEditText, mPasswordEditText;
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    /**
     * Initialize variables
     */
    private void init() {
        mUserNameEditText = (EditText) findViewById(R.id.input_uname);
        mPasswordEditText = (EditText) findViewById(R.id.input_password);
        mLoginButton = (Button) findViewById(R.id.btn_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginApp();
            }
        });
    }

    private void loginApp() {

        if (!mUserNameEditText.getText().toString().isEmpty() && !mPasswordEditText.getText().toString().isEmpty()) {

            WebCallCommand control = new WebCallCommand();
            //Command loginCommand = new LoginCommand(this, "17P6933", "YWDLTVNS");
            Command loginCommand = new LoginCommand(this, mUserNameEditText.getText().toString(), mPasswordEditText
                    .getText().toString());
            //login
            control.setCommand(loginCommand);
            control.executeCommand();
        } else {
            Toast.makeText(this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
        }
    }
}
