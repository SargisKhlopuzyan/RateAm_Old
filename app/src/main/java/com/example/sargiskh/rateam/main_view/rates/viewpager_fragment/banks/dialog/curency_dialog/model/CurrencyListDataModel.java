package com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.dialog.curency_dialog.model;

import com.example.sargiskh.rateam.enums.CurrencyTypeEnum;

public class CurrencyListDataModel {

    private CurrencyTypeEnum currencyType;

    private String currencyDescription;

    private int iconId;


    public CurrencyListDataModel(CurrencyTypeEnum currencyType, String currencyDescription, int iconId) {
        this.currencyType = currencyType;
        this.currencyDescription = currencyDescription;
        this.iconId = iconId;
    }

    public CurrencyTypeEnum getCurrency() {
        return currencyType;
    }

    public String getCurrencyDescription() {
        return currencyDescription;
    }

    public int getIconId() {
        return iconId;
    }



    public void setCurrency(CurrencyTypeEnum currencyType) {
        this.currencyType = currencyType;
    }

    public void setCurrencyDescription(String currencyDescription) {
        this.currencyDescription = currencyDescription;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
