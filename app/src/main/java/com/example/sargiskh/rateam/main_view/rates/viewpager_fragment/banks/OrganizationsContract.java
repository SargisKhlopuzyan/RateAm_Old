package com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks;

import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.Organization;

import java.util.Map;

public interface OrganizationsContract {

    interface View {
        void updateOrganizationData(Map<String, Organization> organizationMap);
        void displayError(String errorMessage);
    }

    interface Presenter {
        void getOrganizationsData();
    }

}