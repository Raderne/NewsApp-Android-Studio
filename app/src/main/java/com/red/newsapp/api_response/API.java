package com.red.newsapp.api_response;

import com.red.newsapp.Services.Auth.AuthUser;
import com.red.newsapp.Services.Auth.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {
    @GET("v2/top-headlines")
    Call<Articles> getNewsArticles(@Query("country") String country, @Query("category") String category , @Query("pageSize") int pageSize, @Query("apiKey") String apiKey);

    @GET("v2/everything")
    Call<Articles> searchArticles(@Query("q") String q, @Query("pageSize") int pageSize, @Query("sortBy") String sort, @Query("apiKey") String apiKey);

    @POST("auth/login")
    Call<AuthUser> login(@Body LoginRequest loginRequest);

    @POST("auth/register")
    Call<AuthUser> register(@Query("email") String email);
}
