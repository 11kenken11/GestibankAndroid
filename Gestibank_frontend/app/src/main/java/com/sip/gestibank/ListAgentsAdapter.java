package com.sip.gestibank;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.sip.gestibank.Models.Agent;
import com.sip.gestibank.Models.User;
import com.sip.gestibank.Remote.APIUtils;
import com.sip.gestibank.Remote.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAgentsAdapter extends BaseAdapter {

    private UserService userService;
    private List<Agent> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    public ListAgentsAdapter(Context aContext, List<Agent> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
 /*LayoutInflater est une classe utilisée pour instancier le fichier XML de
mise en page dans ses objets de vue correspondants qui peuvent être utilisés dans les
programmes Java. En termes simples, il existe deux façons de créer une interface
utilisateur dans android . L'un est une manière statique et l'autre est dynamique ou
par programme.*/
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        userService = APIUtils.getUserService();
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_agents_layout, null);
            holder = new ViewHolder();

            holder.agentNameFirstName = (TextView)
                    convertView.findViewById(R.id.agentNameFirstName);
            holder.agentMat = (TextView)
                    convertView.findViewById(R.id.agentMat);
            holder.agentMailTel = (TextView)
                    convertView.findViewById(R.id.agentMailTel);
            holder.deleteAgentBtn = (Button)
                    convertView.findViewById(R.id.deleteAgentBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Agent agent = this.listData.get(position);
        holder.agentNameFirstName.setText(agent.getLastName() + " " + agent.getFirstName());
        holder.agentMat.setText("Matricule : " + agent.getMatricule());
        holder.agentMailTel.setText("Email: " + agent.getEmail() +  " - Tel: " + agent.getTel());


        //=====================================================================================

        holder.deleteAgentBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("DELETE AGENT: ", agent.getEmail()  );

                Call<Void> call = userService.deleteUserByEmail(agent.getEmail());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(context ,"deleted agent :" + agent.getMatricule(), Toast.LENGTH_LONG).show();
                            Intent i = new Intent(context , ListAgentsActivity.class);
                            context.startActivity(i);
                        } else {
                            Toast.makeText(context ,"agent suppression failed !", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context ,"agent suppression failed !", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        //=====================================================================================
        return convertView;
    }
    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getMipmapResIdByName(String resName) {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap",
                pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }
    static class ViewHolder {
        TextView agentNameFirstName, agentMat, agentMailTel;
        Button deleteAgentBtn;
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updateResults(List<Agent> results) {
        listData = results;
        //Triggers the list update
        notifyDataSetChanged();
    }


}
