package com.example.sargiskh.rateam.detail_view.network;

import com.example.sargiskh.rateam.detail_view.model.ResponseBranches;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface BranchesAPIService {
    @GET
    Call<ResponseBranches> getData(@Url String url);
}
