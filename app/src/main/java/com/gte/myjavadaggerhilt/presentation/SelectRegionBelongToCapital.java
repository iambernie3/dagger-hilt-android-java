package com.gte.myjavadaggerhilt.presentation;

import android.util.Log;

import com.gte.myjavadaggerhilt.core.CollectionsFilter;
import com.gte.myjavadaggerhilt.data.remote.CountryDto;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

public class SelectRegionBelongToCapital extends AutoCompleteSelectedItem{
    @Inject
    public SelectRegionBelongToCapital() {
        super();
    }

    @Override
    public <T> Collection<T> getData(Collection<T> list, String data) {
        ArrayList<String> regions = new ArrayList<>();
        ArrayList<CountryDto> listCountry2 = (ArrayList<CountryDto>) new CollectionsFilter().filter(x -> x.capital != null && x.capital.contains(data), (ArrayList<CountryDto>) list);
        for(CountryDto dto: listCountry2) {
            if(dto.region != null) {
                regions.add(dto.region);
            }
        }
        //noinspection unchecked
        return (Collection<T>) regions;
    }

    public ArrayList<String> toArrayListTypeString(ArrayList<CountryDto> list) {
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i = 0; i<list.size();i++) {
            arrayList.add(list.get(i)+"");
        }

        return arrayList;
    }
}
