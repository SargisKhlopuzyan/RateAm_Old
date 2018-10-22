package com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.network;

import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.Organization;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface OrganizationsAPIService {
    @GET
    Call<Map<String, Organization>> getData(@Url String url);
}
