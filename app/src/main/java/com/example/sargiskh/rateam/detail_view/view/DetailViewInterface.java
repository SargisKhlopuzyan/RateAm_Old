package com.example.sargiskh.rateam.detail_view.view;

import com.example.sargiskh.rateam.detail_view.model.ResponseBranches;

public interface DetailViewInterface {
    void updateData(ResponseBranches responseBranches);
    void displayError(String errorMessage);
}