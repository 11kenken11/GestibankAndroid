package com.sip.gestibank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sip.gestibank.Models.Agent;
import com.sip.gestibank.Models.User;
import com.sip.gestibank.Remote.APIUtils;
import com.sip.gestibank.Remote.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePwdAgentActivity extends AppCompatActivity {

    EditText newPwdAgent, confirmNewPwdAgent;
    Button validNewPwdAgentBtn, logOutCPAgentBtn;
    private UserService userService;
    private String agentMatricule;
    private Agent agent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd_agent);

        newPwdAgent = (EditText) findViewById(R.id.newPwdAgent);
        confirmNewPwdAgent = (EditText) findViewById(R.id.confirmNewPwdAgent);
        validNewPwdAgentBtn = (Button) findViewById(R.id.validNewPwdAgentBtn);
        logOutCPAgentBtn = (Button) findViewById(R.id.logOutCPAgentBtn);

        userService = APIUtils.getUserService();

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            agentMatricule = null;
        } else {
            agentMatricule = extras.getString("agentMatricule");
        }
    }

    public void validNewPwdAgent(View view) {
        if(view == validNewPwdAgentBtn) {
            if(newPwdAgent.getText().toString().trim().equals(confirmNewPwdAgent.getText().toString().trim())) {

                //==================================================================================

                agent = new Agent();
                Call<Agent> call = userService.getAgentByMatricule(agentMatricule);
                call.enqueue(new Callback<Agent>() {
                    @Override
                    public void onResponse(Call<Agent> call, Response<Agent> response) {
                        if(response.isSuccessful()) {

                            Log.i("retrieved agent : ",response.body().toString());


                            agent = response.body();
                            agent.setPassword(newPwdAgent.getText().toString().trim());
                            updateAgent(agent);

                        }
                    }

                    @Override
                    public void onFailure(Call<Agent> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                        Log.i("password update failed: ",t.toString());
                    }

                });

                //==================================================================================

            } else {
                Toast.makeText(this, "confirm doesn't match the new password!" , Toast.LENGTH_LONG).show();
            }
        }
    }

    private void clearText() {
        newPwdAgent.setText("");
        confirmNewPwdAgent.setText("");
    }

    private void updateAgent(Agent agent) {
            Call<Agent> call = userService.updateAgentByMatricule(agent.getMatricule(), agent);
        call.enqueue(new Callback<Agent>() {
            @Override
        public void onResponse(Call<Agent> call, Response<Agent> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext() ,"agent password update success :" , Toast.LENGTH_LONG).show();
                    clearText();
                    Intent i = new Intent(getApplicationContext() , DashboardAgentActivity.class);
                    i.putExtra("agentMatricule", agent.getMatricule());
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext() ,"agent password update failed !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
        public void onFailure(Call<Agent> call, Throwable t) {
                Toast.makeText(getApplicationContext() ,"agent password update failed !", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void callMainActivity(View view){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}