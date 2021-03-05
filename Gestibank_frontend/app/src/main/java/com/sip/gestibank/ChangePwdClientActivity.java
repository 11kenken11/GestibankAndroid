package com.sip.gestibank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sip.gestibank.Models.User;
import com.sip.gestibank.Remote.APIUtils;
import com.sip.gestibank.Remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePwdClientActivity extends AppCompatActivity {

    EditText newPwdClient, confirmNewPwdClient;
    Button validNewPwdClientBtn, logOutCPClientBtn;
    private UserService userService;
    private String clientEmail;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd_client);

        newPwdClient = (EditText) findViewById(R.id.newPwdClient);
        confirmNewPwdClient = (EditText) findViewById(R.id.confirmNewPwdClient);
        validNewPwdClientBtn = (Button) findViewById(R.id.validNewPwdClientBtn);
        logOutCPClientBtn = (Button) findViewById(R.id.logOutCPClientBtn);

        userService = APIUtils.getUserService();

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            clientEmail = null;
        } else {
            clientEmail = extras.getString("clientEmail");
        }
    }

    public void validNewPwdClient(View view) {
        if(view == validNewPwdClientBtn) {
            if(newPwdClient.getText().toString().trim().equals(confirmNewPwdClient.getText().toString().trim())) {

                //==================================================================================

                user = new User();
                Call<User> call = userService.getClientByEmail(clientEmail);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()) {
                            Log.i("retrieved user : ",response.body().toString());


                            user = response.body();
                            user.setPassword(newPwdClient.getText().toString().trim());Log.i("TEST : " , user.toString());
                            updateClient(user);

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                        Log.i("password update failed: ",t.toString());
                    }

                });

                //==================================================================================

            }
        } else {
            Toast.makeText(this, "confirm doesn't match the new password!" , Toast.LENGTH_LONG).show();
        }
    }

    private void clearText() {
        newPwdClient.setText("");
        confirmNewPwdClient.setText("");
    }

    private void updateClient(User user) {
        Call<User> call = userService.updateClientByEmail(user.getEmail(), user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext() ,"password update success!" , Toast.LENGTH_LONG).show();
                    clearText();
                    Intent i = new Intent(getApplicationContext() , DashboardClientActivity.class);
                    i.putExtra("clientEmail", user.getEmail());
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext() ,"password update failed !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext() ,"password update failed !", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void callMainActivity(View view){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}