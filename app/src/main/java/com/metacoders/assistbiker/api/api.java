package com.metacoders.assistbiker.api;

import com.metacoders.assistbiker.models.NewsFeedModel;
import com.metacoders.assistbiker.models.ResponseModel;
import com.metacoders.assistbiker.models.Response_login;
import com.metacoders.assistbiker.models.Response_register;
import com.metacoders.assistbiker.models.Sent_Response_login;
import com.metacoders.assistbiker.models.Sent_Response_mobile;
import com.metacoders.assistbiker.models.Sent_Response_register;
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
    @POST("customers/create")
    Call<Response_register>postUserRegister(@Body Sent_Response_register body ) ;
    @GET("customers/newsfeed")
    Call<List<NewsFeedModel>> getNewsFeed ();
    @POST("customers/check_number")
    Call<ResponseModel> checkNumberValid(@Body Sent_Response_mobile body );



}
