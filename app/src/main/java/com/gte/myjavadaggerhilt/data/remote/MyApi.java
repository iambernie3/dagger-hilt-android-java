package com.gte.myjavadaggerhilt.data.remote;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApi {
    @GET("/v3.1/all")
    Call<ArrayList<CountryDto>> getList();
}
