package com.example.sargiskh.rateam.detail_view.presenter;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.sargiskh.rateam.detail_view.model.Branch;
import com.example.sargiskh.rateam.detail_view.model.ResponseBranches;
import com.example.sargiskh.rateam.enums.ExchangeTypeEnum;

import java.util.Map;

public class DetailViewDataController {

    private static DetailViewDataController instance = null;

    private DetailViewDataController() { }

    public static DetailViewDataController getInstance() {
        if (instance == null) {
            synchronized (DetailViewDataController.class) {
                if (instance == null) {
                    instance = new DetailViewDataController();
                }
            }
        }
        return instance;
    }


    // LiveData
    private MutableLiveData<ResponseBranches> liveData = new MutableLiveData<>();

    public LiveData<ResponseBranches> getData() {
        return liveData;
    }

    public void setData(ResponseBranches data) {
        if (data == null) {
            Log.e("LOG_TAG", "ResponseBranches == null");
        }
        liveData.setValue(data);
    }


    //Exchange Type
    private ExchangeTypeEnum exchangeType = ExchangeTypeEnum.Cash;

    public void setExchangeType(ExchangeTypeEnum exchangeType) {
        this.exchangeType = exchangeType;
    }

    public ExchangeTypeEnum getExchangeType() {
        return exchangeType;
    }


    //Organization Id
    private String organizationId = "";

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

}
