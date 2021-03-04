package com.sip.gestibank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardAgentActivity extends AppCompatActivity {

    private String agentMatricule;
    Button logOutDBAgentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_agent);

        logOutDBAgentBtn = (Button) findViewById(R.id.logOutDBAgentBtn);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            agentMatricule = null;
        } else {
            agentMatricule = extras.getString("matricule");
        }

    }

    public void callValidCheckActivity(View view){
        Intent i = new Intent(getApplicationContext(), ValidCheckActivity.class);
        startActivity(i);
    }

    public void callValidDemandsActivity(View view){
        Intent i = new Intent(getApplicationContext(), ValidDemandsActivity.class);
        i.putExtra("agentMatricule", agentMatricule);
        startActivity(i);
    }

    public void callChangePwdAgentActivity(View view){
        Intent i = new Intent(getApplicationContext(), ChangePwdAgentActivity.class);
        i.putExtra("agentMatricule", agentMatricule);
        startActivity(i);
    }

    public void callMainActivity(View view){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}