package com.gte.myjavadaggerhilt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.gte.myjavadaggerhilt.data.remote.CountryDto;
import com.gte.myjavadaggerhilt.databinding.ActivityMainBinding;
import com.gte.myjavadaggerhilt.presentation.AppViewModel;
import com.gte.myjavadaggerhilt.presentation.SelectRegionBelongToCapital;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    AppViewModel appViewModel;
    private ArrayList<CountryDto> listOfCountry = new ArrayList<>();
    private final ArrayList<String> capitalies = new ArrayList<>();
    private ArrayList<String> regions = new ArrayList<>();

    @Inject
    SelectRegionBelongToCapital selectRegionBelongToCapital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        appViewModel.fetchApi();
        appViewModel.apiState.observe(this,result -> {
            switch (result.getStatus()) {
                case SUCCESS:
                    listOfCountry.clear();
                    capitalies.clear();
                    listOfCountry = result.getData();
                    for(CountryDto dto: listOfCountry) {
                        if(dto.capital != null) {
                            capitalies.addAll(dto.capital);
                        }
                    }


//                    Log.e("API","Country:"+countryDto.name.common);
//                    Log.e("API","Region:"+countryDto.region);
//                    ArrayList<String> capitalies = countryDto.capital;
//                    for(String capital: capitalies) {
//                        Log.e("API","Capital:"+capital);
//                    }
                    //assert list != null;
                    //list = (ArrayList<CountryDto>) new CollectionsFilter().filter(x -> x.name.common.equals("Northern Mariana Islands"),list);

                    prepareCapitalAdapter();
                    break;
                case ERROR:
                    Toast.makeText(this,result.getMessage(),Toast.LENGTH_LONG).show();
                    break;
                default:
            }
        });
    }

    private void prepareCapitalAdapter() {
        Collections.sort(capitalies);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,capitalies);
        binding.autoCompleteCapital.setAdapter(adapter);
        binding.autoCompleteCapital.setThreshold(1);
        binding.ivCapitalShow.setOnClickListener(v-> binding.autoCompleteCapital.showDropDown());
        binding.autoCompleteCapital.setOnItemClickListener((parent, view, position, id) -> {
            ArrayList<CountryDto> list = (ArrayList<CountryDto>)selectRegionBelongToCapital.getData(listOfCountry,capitalies.get(position));
            regions.clear();
            regions = selectRegionBelongToCapital.toArrayListTypeString(list);
            prepareRegionAdapter();
        });
    }


    private void prepareRegionAdapter(){
        binding.autoCompleteRegion.setText("");
        Collections.sort(regions);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,regions);
        adapter.notifyDataSetChanged();

        binding.autoCompleteRegion.setAdapter(adapter);
        binding.autoCompleteRegion.setThreshold(1);
        binding.ivRegionShow.setOnClickListener(v-> binding.autoCompleteRegion.showDropDown());

    }
}