package com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks;

import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.Organization;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.network.OrganizationsAPIService;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.network.OrganizationsRetrofitClientInstance;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.data_controller.BanksDataController;
import com.example.sargiskh.rateam.util.Constants;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BanksPresenter implements OrganizationsContract.Presenter {

    private OrganizationsContract.View viewInterface;

    public BanksPresenter(OrganizationsContract.View viewInterface) {
        this.viewInterface = viewInterface;
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

//                2nd approach //In this case we can directly update viewInterface. this time we need handle orientation changes
//                banksFragmentInterface.updateOrganizationData(dataResponse);
            }

            @Override
            public void onFailure(Call<Map<String, Organization>> call, Throwable t) {
                viewInterface.displayError(t.getMessage());
            }
        });
    }
}
