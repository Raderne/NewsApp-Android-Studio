package com.red.newsapp.api_response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
    @GET("v2/top-headlines")
    Call<Articles> getNewsArticles(@Query("country") String country, @Query("category") String category , @Query("pageSize") int pageSize, @Query("apiKey") String apiKey);

    @GET("v2/everything")
    Call<Articles> searchArticles(@Query("q") String q, @Query("pageSize") int pageSize, @Query("sortBy") String sort, @Query("apiKey") String apiKey);
}
