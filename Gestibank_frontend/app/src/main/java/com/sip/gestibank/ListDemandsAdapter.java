package com.sip.gestibank;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.sip.gestibank.Models.Agent;
import com.sip.gestibank.Models.Exchange;
import com.sip.gestibank.Models.User;
import com.sip.gestibank.Remote.APIUtils;
import com.sip.gestibank.Remote.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDemandsAdapter extends BaseAdapter {

    private List<User> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    private UserService userService;
    private List<Agent> agents;
    //private Agent agent;
    //private User user;

    public ListDemandsAdapter(Context aContext, List<User> listData) {
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_demands_layout, null);
            holder = new ViewHolder();

            holder.clientName = (TextView)
                    convertView.findViewById(R.id.userNameFirstName);
            holder.clientEmail = (TextView)
                    convertView.findViewById(R.id.userEmail);
            holder.clientTel = (TextView)
                    convertView.findViewById(R.id.userTel);

            holder.agentSpinner = (Spinner)
                    convertView.findViewById(R.id.agentSpinner);

            holder.affectClientBtn = (Button)
                    convertView.findViewById(R.id.validateClientBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        User user = this.listData.get(position);
        holder.clientName.setText(user.getLastName() + " " + user.getFirstName());
        holder.clientEmail.setText("Email : " + user.getEmail());
        holder.clientTel.setText("Tel: " + user.getTel());
        //=====================================================================================================

        userService = APIUtils.getUserService();
        /*getExchangesList();
        Log.i("RATES: ", exchange.toString());*/
        //baseCurrencySpinner.setOnItemSelectedListener(this);
        agents = new ArrayList<>();
        Call<List<Agent>> call = userService.getAgents();
        call.enqueue(new Callback<List<Agent>>() {
            @Override
            public void onResponse(Call<List<Agent>> call, Response<List<Agent>> response) {
                if(response.isSuccessful()){
                    agents = response.body();
                    Log.i("==>Data: ", agents.toString());

                    List<String> matricules = new ArrayList<String>();
                    agents.forEach(agent -> matricules.add(agent.getMatricule()));
                    //Object[] o = new Object[];

                    ArrayAdapter aa = new ArrayAdapter(context , android.R.layout.simple_spinner_item, matricules); //(ListDemandsAdapter.class, android.R.layout.simple_spinner_item, matricules.toArray());
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    holder.agentSpinner.setAdapter(aa);


                }

            }

            @Override
            public void onFailure(Call<List<Agent>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });

        //=====================================================================================================

        holder.affectClientBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("AFFECTATION: ","client affectaction started"  );
                //affect client to agent
                String matricule = holder.agentSpinner.getSelectedItem().toString();
               //String email = holder.clientEmail.getText().toString();
                user.setAgentMatricule(matricule);
                Call<User> call = userService.updateClientByEmail(user.getEmail(), user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(context ,"client affected to agent :" + matricule, Toast.LENGTH_LONG).show();
                            Intent i = new Intent(context , ListDemandsActivity.class);
                            context.startActivity(i);
                        } else {
                            Toast.makeText(context ,"client affectation failed !", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(context ,"client affectation failed !", Toast.LENGTH_LONG).show();
                    }
                });
               /* Call<User> call = userService.getClientByEmail(email);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {Log.i("==>GETCLIENT: ", user.toString());
                        if(response.isSuccessful()){
                            User user = response.body();
                            Log.i("==>User: ", user.toString());
                            user.setAgentMatricule(matricule);
                            userService.updateClientByEmail(email, user);
                            Toast.makeText(context ,"client affected to agent :!" + matricule, Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("ERROR AFFECTATION: ", t.getMessage());
                    }
                });*/


                /*Intent i = new Intent(v.getContext(), UpdateActorActivity.class);
                i.putExtra("nom", acteur.getNom());
                context.startActivity(i);*/
            }
        });
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
        TextView clientName, clientTel, clientEmail;
        Spinner agentSpinner;
        Button affectClientBtn;
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updateResults(List<User> results) {
        listData = results;
        //Triggers the list update
        notifyDataSetChanged();
    }
}
