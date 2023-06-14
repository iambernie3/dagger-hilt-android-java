package com.gte.myjavadaggerhilt.domain.repository;

import com.gte.myjavadaggerhilt.data.remote.CountryDto;

import java.util.ArrayList;

import retrofit2.Call;

public interface IApiRepository {
    Call<ArrayList<CountryDto>> fetchApi();
}
