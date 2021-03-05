package com.sip.gestibank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DashboardAdminActivity extends AppCompatActivity {

    private Button logOutDBAdminBtn;
    private TextView welcomeAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        logOutDBAdminBtn = (Button) findViewById(R.id.logOutDBAdminBtn);

        welcomeAdmin = (TextView) findViewById(R.id.welcomeAdmin);

        Bundle extras = getIntent().getExtras();


            /*String adminFirstName = extras.getString("adminFirstName");
            welcomeAdmin.append(" " + adminFirstName);*/

    }


    public void callCreateAgentActivity(View view){
        Intent i = new Intent(getApplicationContext(), CreateAgentActivity.class);

        startActivity(i);
    }

    public void callListAgentsActivity(View view){
        Intent i = new Intent(getApplicationContext(), ListAgentsActivity.class);

        startActivity(i);
    }

    public void callListDemandsActivity(View view){
        Intent i = new Intent(getApplicationContext(), ListDemandsActivity.class);

        startActivity(i);
    }

    public void callMainActivity(View view){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}