package com.metacoders.assistbiker.requests;


import com.metacoders.assistbiker.Utils.Constants;
import com.metacoders.assistbiker.api.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static api AllApi = retrofit.create(api.class);

    public static api AllApi() {
        return AllApi;

    }
}
