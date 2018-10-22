package com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.presenter;

import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.Organization;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.network.OrganizationsAPIService;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.network.OrganizationsRetrofitClientInstance;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.view.BanksFragmentInterface;
import com.example.sargiskh.rateam.util.Constants;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BanksPresenter implements BanksPresenterInterface {

    private BanksFragmentInterface banksFragmentInterface;

    public BanksPresenter(BanksFragmentInterface banksFragmentInterface) {
        this.banksFragmentInterface = banksFragmentInterface;
    }

    @Override
    public void getOrganizationsData() {
        /*Create handle for the RetrofitInstance interface*/
        OrganizationsAPIService service = OrganizationsRetrofitClientInstance.getRetrofitInstance().create(OrganizationsAPIService.class);
        Call<Map<String, Organization>> call = service.getData(Constants.BANKS_INFORMATION_URL);
        call.enqueue(new Callback<Map<String, Organization>>() {
            @Override
            public void onResponse(Call<Map<String, Organization>> call, Response<Map<String, Organization>> dataResponse) {
                BanksDataController.getInstance().setData(dataResponse.body());

//                2nd approach //In this case we can directly update view. this time we need handle orientation changes
//                banksFragmentInterface.updateOrganizationData(dataResponse);
            }

            @Override
            public void onFailure(Call<Map<String, Organization>> call, Throwable t) {
                banksFragmentInterface.displayError(t.getMessage());
            }
        });
    }
}
