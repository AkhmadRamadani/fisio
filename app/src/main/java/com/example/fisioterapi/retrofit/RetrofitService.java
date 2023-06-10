package com.example.fisioterapi.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://pushnotiffisioterapi-production.up.railway.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    public RetrofitAPI getRetrofitAPI() {
        return retrofitAPI;
    }


}
