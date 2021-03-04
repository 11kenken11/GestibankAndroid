package com.sip.gestibank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sip.gestibank.Models.Agent;
import com.sip.gestibank.Models.User;
import com.sip.gestibank.Remote.APIUtils;
import com.sip.gestibank.Remote.UserService;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAgentActivity extends AppCompatActivity {

    EditText agentName, agentFirstName, agentTel, agentEmail, agentMatricule;
    Button createAgentBtn, logOutCAAdminBtn;

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_agent);

        agentName = (EditText) findViewById(R.id.newPwdAgent);
        agentFirstName = (EditText) findViewById(R.id.confirmNewPwdAgent);
        agentTel = (EditText) findViewById(R.id.agentTel);
        agentEmail = (EditText) findViewById(R.id.agentEmail);
        agentMatricule = (EditText) findViewById(R.id.agentMatricule);
        createAgentBtn = (Button) findViewById(R.id.validNewPwdAgentBtn);
        logOutCAAdminBtn = (Button) findViewById(R.id.logOutCAAdminBtn);

        userService = APIUtils.getUserService();
    }

    public void createAccount(View view) {
        if(view == createAgentBtn) {
            if(agentName.getText().toString().trim().length()==0|| agentFirstName.getText().toString().trim().length()==0
                    || agentTel.getText().toString().trim().length()==0|| agentEmail.getText().toString().trim().length()==0
                    || agentMatricule.getText().toString().trim().length()==0)
            {
                Toast.makeText(getApplicationContext(), "Error, Please enter all values", Toast.LENGTH_LONG).show();
                return;
            }
            String password = generatePwd();//(Math.random()).toString(36).slice(-8);
            Agent agent = new Agent(agentName.getText().toString(),  agentFirstName.getText().toString(), agentEmail.getText().toString(),
                                    agentTel.getText().toString(), password , agentMatricule.getText().toString());

            Log.i("user======> : ",agent.toString());
            Call<User> call = userService.createAgent(agent);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),"agent account created!", Toast.LENGTH_LONG).show();
                        Log.i("agent Created : ",response.body().toString());
                        clearText();
                        Intent i = new Intent(getApplicationContext(), DashboardAdminActivity.class);
                        startActivity(i);
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                }

            });



        }

    }

    private void clearText() {
        agentName.setText("");
        agentFirstName.setText("");
        agentTel.setText("");
        agentEmail.setText("");
        agentMatricule.setText("");

    }

    public String generatePwd() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println("MDP=====>" + generatedString);
        return generatedString;
    }

    public void callMainActivity(View view){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}