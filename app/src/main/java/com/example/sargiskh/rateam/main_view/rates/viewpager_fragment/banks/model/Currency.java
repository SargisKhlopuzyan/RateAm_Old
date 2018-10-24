package com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Currency implements Serializable {

    @SerializedName("0") public Transaction cash;

    @SerializedName("1") public Transaction nonCash;

}