package com.example.sargiskh.rateam.detail_view;

import com.example.sargiskh.rateam.detail_view.model.ResponseBranches;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.Organization;

import java.util.Map;

public interface OrganizationDetailContract {

    interface View {
        void updateData(ResponseBranches responseBranches);
        void displayError(String errorMessage);
    }

    interface Presenter {
        void getOrganizationDetailData(String organizationId);
    }

}