package com.gte.myjavadaggerhilt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.gte.myjavadaggerhilt.data.remote.CountryDto;
import com.gte.myjavadaggerhilt.databinding.ActivityMainBinding;
import com.gte.myjavadaggerhilt.presentation.AppViewModel;
import com.gte.myjavadaggerhilt.presentation.SelectRegionBelongToCapital;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    AppViewModel appViewModel;
    private ArrayList<CountryDto> listOfCountry = new ArrayList<>();
    private ArrayList<String> capitalies = new ArrayList<>();
    private ArrayList<String> regions = new ArrayList<>();
    private String countryName = "";

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
                    listOfCountry = ( ArrayList<CountryDto>) result.getData().stream().filter(x -> x.capital != null).collect(Collectors.toList());

                    listOfCountry.forEach( c -> capitalies.addAll(c.capital));
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
            String selectedCapital = (String)parent.getItemAtPosition(position);
            List<CountryDto> list = listOfCountry.stream().filter(x -> x.capital.contains(selectedCapital)).collect(Collectors.toList());
            regions.clear();
            list.forEach(x -> regions.add(x.region));
            countryName = list.get(0).name.common;
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
        binding.autoCompleteRegion.setOnItemClickListener((parent, view, position, id) -> {
            binding.autoCompleteCountry.setText(countryName);
        });

    }
}