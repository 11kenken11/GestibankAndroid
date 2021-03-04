package com.sip.gestibank.Remote;

import com.sip.gestibank.Models.Agent;
import com.sip.gestibank.Models.LoginInformation;
import com.sip.gestibank.Models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {


    @POST("/users")
    Call<User> createUser(@Body User user);

    @POST("/users")
    Call<User> createAgent(@Body Agent agent);

    @POST("/authenticate")
    Call<User> login(@Body LoginInformation loginInformation);

    @GET("/users/pending")
    Call<List<User>> getPendingClient();

    @GET("/user/{email}")
    Call<User> getClientByEmail(@Path("email") String email);

    @GET("/users/pending/{agentMatricule}")
    Call<List<User>> getPendingClientByAgent(@Path("agentMatricule") String agentMatricule);

    @GET("/agents")
    Call<List<Agent>> getAgents();

    @GET("/agents/{matricule}")
    Call<Agent> getAgentByMatricule(@Path("matricule") String matricule);

    @PUT("/agents/{matricule}")
    Call<Agent> updateAgentByMatricule(@Path("matricule") String matricule, @Body Agent agent);

    @PUT("/users/{email}")
    Call<User> updateClientByEmail(@Path("email") String email, @Body User user);

    @DELETE("/users/{email}")
    Call<Void> deleteUserByEmail(@Path("email") String email);
}
