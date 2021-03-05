package com.sip.gestibank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.sip.gestibank.Models.User;
import com.sip.gestibank.Remote.APIUtils;
import com.sip.gestibank.Remote.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDemandsActivity extends AppCompatActivity {

    private UserService userService;
    private List<User> list;
    private Button logOutLDAdminBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_demands);

        logOutLDAdminBtn = (Button) findViewById(R.id.logOutLDAdminBtn);

        userService = APIUtils.getUserService();

        getListData();Log.i("LIST CLIENT", list.toString());
        /*final ListView listView = (ListView) findViewById(R.id.listDemandsView);
        listView.setAdapter(new ListDemandsAdapter(this, list));*/
    }

    public void getListData() {
        list = new ArrayList<User>();
        Call<List<User>> call = userService.getPendingClient();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"pending clients!", Toast.LENGTH_LONG).show();
                    Log.i("pending clients : ",response.body().toString());


                    list = response.body();
                    final ListView listView = (ListView) findViewById(R.id.listDemandsView);
                    listView.setAdapter(new ListDemandsAdapter(getApplicationContext(), list));
                    /*Intent i = new Intent(getApplicationContext(), DashboardAdminActivity.class);
                    startActivity(i);*/
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                Log.i("pending clients FAIL: ",t.toString());
            }

        });


        //return list;
    }

    public void callMainActivity(View view){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }
}