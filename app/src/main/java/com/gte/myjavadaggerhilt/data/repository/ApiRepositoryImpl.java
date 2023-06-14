package com.gte.myjavadaggerhilt.data.repository;

import com.gte.myjavadaggerhilt.data.remote.CountryDto;
import com.gte.myjavadaggerhilt.data.remote.MyApi;
import com.gte.myjavadaggerhilt.domain.repository.IApiRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;

public class ApiRepositoryImpl implements IApiRepository {

    private MyApi api;

    @Inject
    public ApiRepositoryImpl(MyApi api){
        this.api = api;
    }

    @Override
    public Call<ArrayList<CountryDto>> fetchApi() {
        return api.getList();
    }
}
