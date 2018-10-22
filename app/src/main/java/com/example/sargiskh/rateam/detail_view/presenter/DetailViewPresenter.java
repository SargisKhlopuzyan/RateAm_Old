package com.example.sargiskh.rateam.detail_view.presenter;

import android.util.Log;

import com.example.sargiskh.rateam.detail_view.model.ResponseBranches;
import com.example.sargiskh.rateam.detail_view.network.BranchesAPIService;
import com.example.sargiskh.rateam.detail_view.network.BranchesRetrofitClientInstance;
import com.example.sargiskh.rateam.detail_view.view.DetailViewInterface;
import com.example.sargiskh.rateam.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewPresenter implements DetailViewPresenterInterface {

    private DetailViewInterface detailViewInterface;

    public DetailViewPresenter(DetailViewInterface detailViewInterface) {
        this.detailViewInterface = detailViewInterface;
    }

    @Override
    public void getOrganizationDetailData(String organizationId) {
        /*Create handle for the RetrofitInstance interface*/
        BranchesAPIService service = BranchesRetrofitClientInstance.getRetrofitInstance().create(BranchesAPIService.class);
        Call<ResponseBranches> call = service.getData(getUrlByOrganizationId(organizationId));
        call.enqueue(new Callback<ResponseBranches>() {
            @Override
            public void onResponse(Call<ResponseBranches> call, Response<ResponseBranches> dataResponse) {
                DetailViewDataController.getInstance().setData(dataResponse.body());

//                2nd approach //In this case we can directly update view. this time we need handle orientation changes
//                detailViewInterface.updateOrganizationData(dataResponse.body());
            }

            @Override
            public void onFailure(Call<ResponseBranches> call, Throwable t) {
                DetailViewDataController.getInstance().setData(null);
                Log.e("LOG_TAG", "onFailure: " + t);
                detailViewInterface.displayError(t.getMessage());
            }
        });
    }

    private String getUrlByOrganizationId(String organizationId) {
        return Constants.BANK_INFORMATION_URL + organizationId;
    }
}
