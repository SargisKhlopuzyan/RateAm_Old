package com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Organization implements Serializable {

    @SerializedName("title") public String title;

    @SerializedName("date") public Integer date;

    @SerializedName("logo") public String logo;

    @SerializedName("list") public CurrencyList currencyList;
}
