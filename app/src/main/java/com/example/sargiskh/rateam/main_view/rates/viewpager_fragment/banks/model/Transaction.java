package com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Transaction implements Serializable {

    @SerializedName("buy") public Float buy;

    @SerializedName("sell") public Float sell;
}