package com.lawrence.ditrp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.lawrence.ditrp.R;
import com.lawrence.ditrp.command.Command;
import com.lawrence.ditrp.command.LoginCommand;
import com.lawrence.ditrp.command.WebCallCommand;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginApp();

    }

    private void loginApp() {
        WebCallCommand control = new WebCallCommand();
        Command loginCommand = new LoginCommand(this, "17P6933", "YWDLTVNS");
        //login
        control.setCommand(loginCommand);
        control.executeCommand();
    }
}
