package com.sip.gestibank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sip.gestibank.Models.LoginInformation;
import com.sip.gestibank.Models.User;
import com.sip.gestibank.Remote.APIUtils;
import com.sip.gestibank.Remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationActivity extends AppCompatActivity {

    EditText email, password;
    Button connectionBtn;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        email = (EditText) findViewById(R.id.newPwdAgent);
        password = (EditText) findViewById(R.id.confirmNewPwdAgent);
        connectionBtn = (Button) findViewById(R.id.validNewPwdAgentBtn);

        userService = APIUtils.getUserService();
    }




   /*public void login(View view) {
        if(view == connexionBtn) {
            String login = loginTextPlain.getText().toString();
            String password = passwordTextPlain.getText().toString();
                    LoginService loginService =
                    APIUtils.createService(LoginService.class, login, password);
            Call<User> call = loginService.basicLogin();
            call.enqueue(new Callback<User >() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        // user object available
                        //callWelcomeActivity();
                        Log.i("CONNEXION SUCCEED", "");
                    } else {
                        // error response, no access to resource?
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    // something went completely south (like no internet connection)
                    Log.d("Error", t.getMessage());
                }
            });
        }

    }*/

    public void authenticate(View view) {
        if(view == connectionBtn) {
            if(email.getText().toString().trim().length()==0|| password.getText().toString().trim().length()==0)
            {
                Toast.makeText(getApplicationContext(), "Error, Please enter all values", Toast.LENGTH_LONG).show();
                return;
            }
            LoginInformation loginInformation = new LoginInformation(email.getText().toString().trim(), password.getText().toString().trim());
            Call<User> call = userService.login(loginInformation);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),"Authentication success", Toast.LENGTH_LONG).show();
                        User user = response.body();
                        clearText();    Log.i("ROLE==>", user.getRole());

                        switch (user.getRole()) {
                            case "ADMIN" : {
                                Intent i = new Intent(getApplicationContext(), DashboardAdminActivity.class);Log.i("ADMIN==>", user.toString());
                                i.putExtra("adminFirstName", user.getFirstName());
                                startActivity(i);
                                break;
                            }

                            case "CLIENT" : {
                                Intent i = new Intent(getApplicationContext(), DashboardClientActivity.class);Log.i("CLIENT==>", user.toString());
                                i.putExtra("clientEmail", user.getEmail());
                                i.putExtra("clientFirstName", user.getFirstName());
                                startActivity(i);
                                break;
                            }

                            case "AGENT" : {
                                Intent i = new Intent(getApplicationContext(), DashboardAgentActivity.class);Log.i("AGENT==>", user.toString());
                                i.putExtra("matricule", user.getMatricule());
                                i.putExtra("agentFirstName", user.getFirstName());
                                startActivity(i);
                                break;
                            }

                            default: {

                            }

                        }

                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.i("FAIL==>", t.toString());
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                }

            });



        }

    }

    private void clearText() {
        email.setText("");
        password.setText("");

    }


    public void callWelcomeActivity(View view){
        Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);

        startActivity(i);
    }

}