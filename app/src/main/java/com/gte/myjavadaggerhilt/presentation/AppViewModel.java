package com.gte.myjavadaggerhilt.presentation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gte.myjavadaggerhilt.core.ApiEnum;
import com.gte.myjavadaggerhilt.core.CollectionsFilter;
import com.gte.myjavadaggerhilt.data.remote.CountryDto;
import com.gte.myjavadaggerhilt.domain.ApiResponse;
import com.gte.myjavadaggerhilt.domain.repository.IApiRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class AppViewModel extends ViewModel {

    public MutableLiveData<ApiResponse<ArrayList<CountryDto>>> apiState = new MutableLiveData<>();
    private final IApiRepository repository;

    @Inject
    public AppViewModel(IApiRepository repository){
        this.repository = repository;
    }

    public void fetchApi() {
        Call<ArrayList<CountryDto>>  call = repository.fetchApi();
        call.enqueue(new Callback<ArrayList<CountryDto>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<CountryDto>> call, @NonNull Response<ArrayList<CountryDto>> response) {

                if(response.code() == 200) {
                    ArrayList<CountryDto> list = response.body();
                    apiState.postValue(new ApiResponse<>(
                            list, ApiEnum.SUCCESS,response.message())
                    );
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<CountryDto>> call, @NonNull Throwable t) {
                Log.e("API","response-t:"+t.getMessage());
                apiState.postValue(new ApiResponse<>(
                        null,ApiEnum.ERROR,t.getMessage())

                );
            }
        });
    }
}
