package com.sip.gestibank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class ListAgentsActivity extends AppCompatActivity {

    private UserService userService;
    private List<Agent> list;
    private Button logOutLAAdminBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_agents);

        logOutLAAdminBtn = (Button) findViewById(R.id.logOutLAAdminBtn);

        userService = APIUtils.getUserService();

        getListData();
        Log.i("LIST AGENTS", list.toString());
    }

    public void getListData() {
        list = new ArrayList<Agent>();
        Call<List<Agent>> call = userService.getAgents();
        call.enqueue(new Callback<List<Agent>>() {
            @Override
            public void onResponse(Call<List<Agent>> call, Response<List<Agent>> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"agents!", Toast.LENGTH_LONG).show();
                    Log.i("agents : ",response.body().toString());


                    list = response.body();
                    final ListView listView = (ListView) findViewById(R.id.listAgentsView);
                    listView.setAdapter(new ListAgentsAdapter(getApplicationContext(), list));
                }
            }

            @Override
            public void onFailure(Call<List<Agent>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                Log.i("agents FAIL: ",t.toString());
            }

        });


        //return list;
    }

    public void callMainActivity(View view){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}