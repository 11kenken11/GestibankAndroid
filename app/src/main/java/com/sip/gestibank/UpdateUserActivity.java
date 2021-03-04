package com.sip.gestibank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class UpdateUserActivity extends AppCompatActivity {

    EditText updateLastNameTextPlain, updateFirstNameTextPlain, updateEmailTextPlain, updateLoginTextPlain, updateTelTextPlain, updatePasswordTextPlain;
    Button updateAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        updateLastNameTextPlain = (EditText) findViewById(R.id.newPwdAgent);
        updateFirstNameTextPlain = (EditText) findViewById(R.id.confirmNewPwdAgent);
        updateEmailTextPlain = (EditText) findViewById(R.id.agentEmail);
        updateLoginTextPlain = (EditText) findViewById(R.id.agentMatricule);
        updateTelTextPlain = (EditText) findViewById(R.id.agentTel);
        updatePasswordTextPlain = (EditText) findViewById(R.id.agentPwd);
        updateAccountBtn = (Button) findViewById(R.id.validNewPwdAgentBtn);
    }
}