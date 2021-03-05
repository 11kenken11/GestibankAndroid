package com.sip.gestibank.Remote;

import com.sip.gestibank.Models.User;

import retrofit2.Call;
import retrofit2.http.POST;

public interface LoginService {
    @POST("/authenticate")
    Call<User> login();
}
