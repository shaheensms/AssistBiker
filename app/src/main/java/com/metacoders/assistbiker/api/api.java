package com.metacoders.assistbiker.api;

import com.metacoders.assistbiker.models.Response_login;
import com.metacoders.assistbiker.models.Sent_Response_login;
import com.metacoders.assistbiker.models.testModel;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface api {

    @GET("users")
    Call<List<testModel>> getUsers();
    @POST("customers/login")
    Call<Response_login>postUserLoagin(@Body Sent_Response_login body ) ;




}
