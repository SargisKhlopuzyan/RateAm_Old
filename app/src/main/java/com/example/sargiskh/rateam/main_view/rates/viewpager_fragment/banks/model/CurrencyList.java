package com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model;

import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.currency.AUD;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.currency.CAD;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.currency.CHF;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.currency.EUR;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.currency.GBP;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.currency.GEL;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.currency.JPY;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.currency.RUR;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.currency.USD;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.currency.XAU;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CurrencyList implements Serializable {

    @SerializedName("AUD") public AUD AUD;

    @SerializedName("CAD") public CAD CAD;

    @SerializedName("CHF") public CHF CHF;

    @SerializedName("EUR") public EUR EUR;

    @SerializedName("GBP") public GBP GBP;

    @SerializedName("GEL") public GEL GEL;

    @SerializedName("JPY") public JPY JPY;

    @SerializedName("RUR") public RUR RUR;

    @SerializedName("USD") public USD USD;

    @SerializedName("XAU") public XAU XAU;
}