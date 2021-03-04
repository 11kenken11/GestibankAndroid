package com.sip.gestibank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DashboardAgentActivity extends AppCompatActivity {

    private String agentMatricule, agentFirstName;
    Button logOutDBAgentBtn;

    TextView welcomeAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_agent);

        logOutDBAgentBtn = (Button) findViewById(R.id.logOutDBAgentBtn);
        welcomeAgent = (TextView) findViewById(R.id.welcomeAgent);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            agentMatricule = null;
        } else {
            agentMatricule = extras.getString("matricule");
            agentFirstName = extras.getString("agentFirstName");
            welcomeAgent.append(" " + agentFirstName);
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