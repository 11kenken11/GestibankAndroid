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

import com.sip.gestibank.Models.User;
import com.sip.gestibank.Remote.APIUtils;
import com.sip.gestibank.Remote.UserService;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDemandsByAgentAdapter extends BaseAdapter {

    private UserService userService;
    private List<User> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    public ListDemandsByAgentAdapter(Context aContext, List<User> listData) {
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
        ListDemandsByAgentAdapter.ViewHolder holder;
        userService = APIUtils.getUserService();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_demands_by_agent_layout, null);
            holder = new ListDemandsByAgentAdapter.ViewHolder();

            holder.clientName = (TextView)
                    convertView.findViewById(R.id.userByAgentNameFirstName);
            holder.clientEmail = (TextView)
                    convertView.findViewById(R.id.userByAgentEmail);
            holder.clientTel = (TextView)
                    convertView.findViewById(R.id.userByAgentTel);
            holder.validateClientBtn = (Button)
                    convertView.findViewById(R.id.validateClientBtn);
            holder.refuseClientBtn = (Button)
                    convertView.findViewById(R.id.refuseClientBtn);
            convertView.setTag(holder);
        } else {
            holder = (ListDemandsByAgentAdapter.ViewHolder) convertView.getTag();
        }
        User user = this.listData.get(position);
        holder.clientName.setText(user.getLastName() + " " + user.getFirstName());
        holder.clientEmail.setText("Email : " + user.getEmail());
        holder.clientTel.setText("Tel: " + user.getTel());

        holder.validateClientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set status VALIDE
                Log.i("VALIDATE CLIENT: ",user.getLastName() + " " + user.getFirstName());

                user.setStatus("VALIDE");
                user.setPassword(generatePwd());

                Call<User> call = userService.updateClientByEmail(user.getEmail(), user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "client approved :" + user.getLastName() + " " + user.getFirstName(), Toast.LENGTH_LONG).show();
                            Intent i = new Intent(context, ValidDemandsActivity.class);
                            i.putExtra("agentMatricule", user.getAgentMatricule());
                            context.startActivity(i);
                        } else {
                            Toast.makeText(context, "client approbation failed !", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(context, "client approbation failed !", Toast.LENGTH_LONG).show();
                    }

                });
            }
        });
        holder.refuseClientBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //set client status REFUSE
                Log.i("REFUSE CLIENT: ",user.getLastName() + " " + user.getFirstName());

                user.setStatus("REFUSE");

                Call<User> call = userService.updateClientByEmail(user.getEmail(), user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "client refused :" + user.getLastName() + " " + user.getFirstName(), Toast.LENGTH_LONG).show();
                            Intent i = new Intent(context, ValidDemandsActivity.class);
                            i.putExtra("agentMatricule", user.getAgentMatricule());
                            context.startActivity(i);
                        } else {
                            Toast.makeText(context, "client refuse failed !", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(context, "client refuse failed !", Toast.LENGTH_LONG).show();
                    }

                });

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
        Button validateClientBtn;
        Button refuseClientBtn;
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
}