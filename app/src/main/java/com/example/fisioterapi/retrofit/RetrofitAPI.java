package com.example.fisioterapi.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface RetrofitAPI {
    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @POST("send-emergency-notif")
    Call<Map<String, String>> sendEmergencyNotif(@Field("lat") String latitude, @Field("long") String longitude, @Field("reason") String reason, @Field("patientId") String patientId);
}
