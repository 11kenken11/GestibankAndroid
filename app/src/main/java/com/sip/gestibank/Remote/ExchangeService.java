package com.sip.gestibank.Remote;

import com.sip.gestibank.Models.Exchange;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ExchangeService {

    @GET("latest/")
    Call<Exchange> getExchange();
    //Call<List<Exchange>> getExchanges();
}
