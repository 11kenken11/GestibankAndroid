package com.sip.gestibank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DashboardClientActivity extends AppCompatActivity {

    private String clientEmail, clientFirstName;
    Button logOutDBClientBtn;
    TextView welcomeClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_client);

        logOutDBClientBtn = (Button) findViewById(R.id.logOutDBClientBtn);
        welcomeClient = (TextView) findViewById(R.id.welcomeClient);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            clientEmail = null;
        } else {
            clientEmail = extras.getString("clientEmail");
            /*clientFirstName = extras.getString("clientFirstName");
            welcomeClient.append(" " + clientFirstName);*/
        }
    }

    public void callCheckRequestActivity(View view){
        Intent i = new Intent(getApplicationContext(), CheckRequestActivity.class);
        startActivity(i);
    }

    public void callHistoryActivity(View view){
        Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
        i.putExtra("clientEmail", clientEmail);
        startActivity(i);
    }

    public void callChangePwdClientActivity(View view){
        Intent i = new Intent(getApplicationContext(), ChangePwdClientActivity.class);
        i.putExtra("clientEmail", clientEmail);
        startActivity(i);
    }

    public void callMainActivity(View view){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}