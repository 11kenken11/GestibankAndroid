package com.sip.gestibank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sip.gestibank.Models.User;
import com.sip.gestibank.Remote.APIUtils;
import com.sip.gestibank.Remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends AppCompatActivity {

    TextView alreadyRegisterdTV;
    EditText lastNameTextPlain, firstNameTextPlain, telTextPlain, emailTextPlain;
    Button createAccountBtn;
    private RadioGroup typeCompteRG;
    private RadioButton cacBtn, cscBtn, epBtn;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        lastNameTextPlain = (EditText) findViewById(R.id.newPwdAgent);
        firstNameTextPlain = (EditText) findViewById(R.id.confirmNewPwdAgent);
        telTextPlain = (EditText) findViewById(R.id.agentTel);
        emailTextPlain = (EditText) findViewById(R.id.agentEmail);
        createAccountBtn = (Button) findViewById(R.id.validNewPwdAgentBtn);
        typeCompteRG = (RadioGroup) findViewById(R.id.typeCompteRG);
        cacBtn = (RadioButton) findViewById(R.id.cacBtn);
        cscBtn = (RadioButton) findViewById(R.id.cscBtn);
        epBtn = (RadioButton) findViewById(R.id.epBtn);

        SpannableString ss = new SpannableString("Si vous avez déjà un compte, cliquer ici.");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(CreateAccountActivity.this, AuthenticationActivity.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 37, 40, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        alreadyRegisterdTV = (TextView) findViewById(R.id.alreadyRegisterdTV);
        alreadyRegisterdTV.setText(ss);
        alreadyRegisterdTV.setMovementMethod(LinkMovementMethod.getInstance());
        alreadyRegisterdTV.setHighlightColor(Color.TRANSPARENT);

        userService = APIUtils.getUserService();
    }

    public void createAccount(View view) {
        if(view == createAccountBtn) {
            if(lastNameTextPlain.getText().toString().trim().length()==0|| firstNameTextPlain.getText().toString().trim().length()==0
                    || telTextPlain.getText().toString().trim().length()==0|| emailTextPlain.getText().toString().trim().length()==0
                    || typeCompteRG.getCheckedRadioButtonId() == -1)
            {
                Toast.makeText(getApplicationContext(), "Error, Please enter all values", Toast.LENGTH_LONG).show();
                return;
            }//User(String lastName, String firstName, String email, String tel, String typeCompte
            // get selected radio button from radioGroup
            int selectedId = typeCompteRG.getCheckedRadioButtonId();

            // find the radiobutton by returned id
            RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);
            String typeCompte = selectedRadioButton.getText().toString();
            User user = new User(lastNameTextPlain.getText().toString(), firstNameTextPlain.getText().toString(),
                    emailTextPlain.getText().toString(), telTextPlain.getText().toString(),
                    typeCompte);

            Call<User> call = userService.createUser(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),"client account created!", Toast.LENGTH_LONG).show();
                        Log.i("client Created : ",response.body().toString());
                        clearText();
                        Intent i = new Intent(getApplicationContext(), AuthenticationActivity.class);
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
        lastNameTextPlain.setText("");
        firstNameTextPlain.setText("");
        telTextPlain.setText("");
        emailTextPlain.setText("");
        typeCompteRG.clearCheck();

    }


}