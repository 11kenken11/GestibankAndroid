package com.sip.gestibank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sip.gestibank.Models.Exchange;
import com.sip.gestibank.Remote.APIUtils;
import com.sip.gestibank.Remote.ExchangeService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConvertCurrencyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ExchangeService exchangeService;
    //List<Exchange> list = new ArrayList<Exchange>();
    Exchange exchange;
    //Spinner baseCurrencySpinner, destinationCurrencySpinner;
    AutoCompleteTextView currencyTextView;
    EditText amoutTextPlain, resultTextPlain;

    TextView resultTextView;
    Button btnUpdateActor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_currency);

        //baseCurrencySpinner = (Spinner) findViewById(R.id.baseCurrencySpinner);

        //destinationCurrencySpinner = (Spinner) findViewById(R.id.destinationCurrencySpinner);

        amoutTextPlain = (EditText) findViewById(R.id.confirmNewPwdAgent);
        resultTextPlain = (EditText) findViewById(R.id.agentTel);
        //resultTextPlain.setEnabled(false);
        resultTextPlain.setInputType(InputType.TYPE_NULL);
        resultTextPlain.setKeyListener(null);
        resultTextPlain.setTextIsSelectable(true);

        currencyTextView = (AutoCompleteTextView) findViewById(R.id.currencyAutoCompleteTextView);
        btnUpdateActor = (Button) findViewById(R.id.validNewPwdAgentBtn);
        exchangeService = APIUtils.getExchangeService();
        /*getExchangesList();
        Log.i("RATES: ", exchange.toString());*/
       //baseCurrencySpinner.setOnItemSelectedListener(this);

        Call<Exchange> call = exchangeService.getExchange();
        call.enqueue(new Callback<Exchange>() {
            @Override
            public void onResponse(Call<Exchange> call, Response<Exchange> response) {
                if(response.isSuccessful()){
                    exchange = response.body();
                    Log.i("Data: ", exchange.toString());

                    //Creating the ArrayAdapter instance having the country list
                    /*ArrayAdapter aa = new ArrayAdapter(ConvertCurrencyActivity.this,android.R.layout.simple_spinner_item, exchange.getRates().keySet().toArray());
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    baseCurrencySpinner.setAdapter(aa);*/


                    ArrayAdapter aa = new ArrayAdapter(ConvertCurrencyActivity.this,android.R.layout.simple_spinner_item, exchange.getRates().keySet().toArray());
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //Setting the ArrayAdapter data on the Spinner
                    //baseCurrencySpinner.setAdapter(aa);
                    currencyTextView.setThreshold(0);
                    currencyTextView.setAdapter(aa);
                    currencyTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View arg0) {
                            currencyTextView.showDropDown();
                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<Exchange> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getApplicationContext(),exchange.toString() , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void convert(View view) {
        if(view == btnUpdateActor) {
            Double amount = Double.valueOf(amoutTextPlain.getText().toString());
            String rateKey = currencyTextView.getText().toString();
            Double rate = Double.valueOf(exchange.getRates().get(rateKey).toString());
            Double result = amount * rate;
            resultTextPlain.setText(result.toString());
        }
    }
}