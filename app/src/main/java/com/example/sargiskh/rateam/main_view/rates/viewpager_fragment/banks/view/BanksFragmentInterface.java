package com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.view;

import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.Organization;

import java.util.Map;

public interface BanksFragmentInterface {
    void updateOrganizationData(Map<String, Organization> organizationMap);
    void displayError(String errorMessage);
}