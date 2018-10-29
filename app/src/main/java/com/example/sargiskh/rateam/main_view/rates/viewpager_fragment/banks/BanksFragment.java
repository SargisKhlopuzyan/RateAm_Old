package com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sargiskh.rateam.R;
import com.example.sargiskh.rateam.dialog.ErrorMessageDialogFragment;
import com.example.sargiskh.rateam.enums.CurrencyTypeEnum;
import com.example.sargiskh.rateam.enums.ExchangeTypeEnum;
import com.example.sargiskh.rateam.enums.SortOrderEnum;
import com.example.sargiskh.rateam.enums.SortTypeEnum;
import com.example.sargiskh.rateam.helper.FilterHelperOrganization;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.adapter.BanksAdapter;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.data_controller.BanksDataController;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.dialog.curency_dialog.CurrencyListDialogFragment;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.dialog.exchenge_type_dialog.ExchangeTypeDialogFragment;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.Organization;
import com.example.sargiskh.rateam.util.Constants;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BanksFragment extends Fragment implements OrganizationsContract.View {

    private SwipeRefreshLayout swipeRefreshLayout;
    private Button buttonExchangeType;
    private Button buttonCurrencyType;

    private TextView textViewPurchase;
    private TextView textViewSale;
    private RecyclerView recyclerView;

    private OrganizationsContract.Presenter banksPresenter;
    private BanksDataController banksDataController;


    public BanksFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        banksPresenter = new BanksPresenter(this);
        banksDataController = BanksDataController.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_banks, container, false);
        findViews(view);
        populateViews();
        setListeners();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LiveData<Map<String, Organization>> liveData = BanksDataController.getInstance().getData();
        liveData.observe(this, new Observer<Map<String, Organization>>() {
            @Override
            public void onChanged(@Nullable Map<String, Organization> value) {
                updateOrganizationData(value);
            }
        });

        if (liveData.getValue() == null) {
            banksPresenter.getOrganizationsData();
        }
    }

    @Override
    public void onDestroy() {
        banksDataController.getData().removeObservers(this);
        super.onDestroy();
    }

    @Override
    public void updateOrganizationData(Map<String, Organization> organizationMap) {

        BanksAdapter dataAdapter = (BanksAdapter) recyclerView.getAdapter();
        if (dataAdapter != null) {

            Map<String, Organization> organizationFilteredMap = FilterHelperOrganization.getOrganizationFilteredMapByExchangeTypeAndCurrencyType(organizationMap, banksDataController.getExchangeType(), banksDataController.getCurrencyType());

            if (banksDataController.getSortType() == SortTypeEnum.Purchase) {
                Map<String, Organization> organizationSortedMap = FilterHelperOrganization.sortMapForPurchase(organizationFilteredMap, banksDataController.getExchangeType(), banksDataController.getCurrencyType(), banksDataController.getSortOrderForPurchase());
                dataAdapter.updateData(organizationSortedMap, banksDataController.getExchangeType(), banksDataController.getCurrencyType());
            } else if (banksDataController.getSortType() == SortTypeEnum.Sale) {
                Map<String, Organization> organizationSortedMap = FilterHelperOrganization.sortMapForSale(organizationFilteredMap, banksDataController.getExchangeType(), banksDataController.getCurrencyType(), banksDataController.getSortOrderForSale());
                dataAdapter.updateData(organizationSortedMap, banksDataController.getExchangeType(), banksDataController.getCurrencyType());
            } else {
                dataAdapter.updateData(organizationFilteredMap, banksDataController.getExchangeType(), banksDataController.getCurrencyType());
            }
        }

        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void displayError(String errorMessage) {
        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
        showErrorDialog(errorMessage);
    }

    private void findViews(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        buttonExchangeType = view.findViewById(R.id.buttonExchangeType);
        buttonCurrencyType = view.findViewById(R.id.buttonCurrencyType);

        textViewPurchase = view.findViewById(R.id.textViewPurchase);
        textViewSale = view.findViewById(R.id.textViewSale);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void populateViews() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        BanksAdapter banksAdapter = new BanksAdapter(banksDataController.getExchangeType(), banksDataController.getCurrencyType());
        recyclerView.setAdapter(banksAdapter);

        if (banksDataController.getExchangeType() == ExchangeTypeEnum.Cash) {
            buttonExchangeType.setText(getString(R.string.cash));
        } else {
            buttonExchangeType.setText(getString(R.string.non_cash));
        }

        if (banksDataController.getSortType() == SortTypeEnum.Unsorted) {
            textViewPurchase.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_down_disabled,0);
            textViewSale.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_down_disabled,0);
        } else if (banksDataController.getSortType() == SortTypeEnum.Purchase) {
            textViewPurchase.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_up_enabled,0);
            textViewSale.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_down_disabled,0);
        } else {
            textViewPurchase.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_down_disabled,0);
            textViewSale.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_up_enabled,0);
        }
    }

    private void setListeners() {
        textViewPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (banksDataController.getSortType() == SortTypeEnum.Unsorted) {

                    banksDataController.setSortType(SortTypeEnum.Purchase);

                    banksDataController.setSortOrderForPurchase(SortOrderEnum.Ascending);
                    textViewPurchase.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_up_enabled,0);
                    banksDataController.setSortOrderForSale(SortOrderEnum.Descending);
                    textViewSale.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_down_disabled,0);

                } else if (banksDataController.getSortType() == SortTypeEnum.Purchase) {

                    if (banksDataController.getSortOrderForPurchase() == SortOrderEnum.Descending) {
                        banksDataController.setSortOrderForPurchase(SortOrderEnum.Ascending);
                        textViewPurchase.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_up_enabled,0);
                    } else {
                        banksDataController.setSortOrderForPurchase(SortOrderEnum.Descending);
                        textViewPurchase.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_down_enabled,0);
                    }

                } else {

                    if (banksDataController.getSortOrderForPurchase() == SortOrderEnum.Descending) {
                        banksDataController.setSortOrderForPurchase(SortOrderEnum.Ascending);
                        textViewPurchase.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_up_enabled,0);
                        banksDataController.setSortOrderForSale(SortOrderEnum.Descending);
                        textViewSale.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_down_disabled,0);
                    } else {
                        banksDataController.setSortOrderForPurchase(SortOrderEnum.Descending);
                        textViewPurchase.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_down_enabled,0);
                        banksDataController.setSortOrderForSale(SortOrderEnum.Ascending);
                        textViewSale.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_up_disabled,0);
                    }
                    banksDataController.setSortType(SortTypeEnum.Purchase);

                }
                sortData(banksDataController.getSortOrderForPurchase());
            }
        });

        textViewSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (banksDataController.getSortType() == SortTypeEnum.Unsorted) {

                    banksDataController.setSortType(SortTypeEnum.Sale);

                    banksDataController.setSortOrderForSale(SortOrderEnum.Ascending);
                    textViewSale.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_up_enabled,0);
                    banksDataController.setSortOrderForPurchase(SortOrderEnum.Descending);
                    textViewPurchase.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_down_disabled,0);

                } else if (banksDataController.getSortType() == SortTypeEnum.Sale) {

                    if (banksDataController.getSortOrderForSale() == SortOrderEnum.Descending) {
                        banksDataController.setSortOrderForSale(SortOrderEnum.Ascending);
                        textViewSale.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_up_enabled,0);
                    } else {
                        banksDataController.setSortOrderForSale(SortOrderEnum.Descending);
                        textViewSale.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_down_enabled,0);
                    }

                } else {

                    if (banksDataController.getSortOrderForSale() == SortOrderEnum.Descending) {
                        banksDataController.setSortOrderForSale(SortOrderEnum.Ascending);
                        textViewSale.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_up_enabled,0);
                        banksDataController.setSortOrderForPurchase(SortOrderEnum.Descending);
                        textViewPurchase.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_down_disabled,0);
                    } else {
                        banksDataController.setSortOrderForSale(SortOrderEnum.Descending);
                        textViewSale.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_down_enabled,0);
                        banksDataController.setSortOrderForPurchase(SortOrderEnum.Ascending);
                        textViewPurchase.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.icons_sort_up_disabled,0);
                    }
                    banksDataController.setSortType(SortTypeEnum.Sale);
                }

                sortData(banksDataController.getSortOrderForSale());
            }
        });

        buttonExchangeType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("ExchangeTypeDialogFragment");
                if (prev != null) {
                    return;
                }

                Bundle bundle = new Bundle();
                int intValue = banksDataController.getExchangeType().ordinal();
                bundle.putInt(Constants.BUNDLE_EXCHANGE_TYPE, intValue);

                ft.addToBackStack(null);
                DialogFragment dialogFragment = new ExchangeTypeDialogFragment();
                dialogFragment.setTargetFragment(BanksFragment.this, Constants.REQUEST_CODE_EXCHANGE_TYPE);
                dialogFragment.setArguments(bundle);

                getFragmentManager().beginTransaction().add(dialogFragment,"ExchangeTypeDialogFragment").commit();
            }
        });

        buttonCurrencyType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("CurrencyListDialogFragment");
                if (prev != null) {
                    return;
                }

                Bundle bundle = new Bundle();
                int intValue = banksDataController.getCurrencyType().ordinal();
                bundle.putInt(Constants.BUNDLE_CURRENCY_TYPE, intValue);

                ft.addToBackStack(null);
                DialogFragment dialogFragment = new CurrencyListDialogFragment();
                dialogFragment.setTargetFragment(BanksFragment.this, Constants.REQUEST_CODE_CURRENCY_TYPE);
                dialogFragment.setArguments(bundle);

                getFragmentManager().beginTransaction().add(dialogFragment,"CurrencyListDialogFragment").commit();
            }
        });

        //TODO - Map button functionality not implemented


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                banksPresenter.getOrganizationsData();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case Constants.REQUEST_CODE_EXCHANGE_TYPE:
                if (resultCode == Activity.RESULT_OK) {
                    handleExchangeTypeChange(data);
                }
                break;
            case Constants.REQUEST_CODE_CURRENCY_TYPE:
                if (resultCode == Activity.RESULT_OK) {
                    handleCurrencyTypeChange(data);
                }
                break;
        }
    }

    private void handleExchangeTypeChange(Intent data) {
        int intValue = data.getExtras().getInt(Constants.BUNDLE_EXCHANGE_TYPE,0);
        ExchangeTypeEnum exchangeType = ExchangeTypeEnum.values()[intValue];
        banksDataController.setExchangeType(exchangeType);

        switch (exchangeType) {
            case Cash:
                buttonExchangeType.setText(getText(R.string.cash));
                break;
            case NonCash:
                buttonExchangeType.setText(getText(R.string.non_cash));
                break;
        }

        updateFilteredData();
    }

    private void handleCurrencyTypeChange(Intent data) {
        int intValue = data.getExtras().getInt(Constants.BUNDLE_CURRENCY_TYPE,0);
        CurrencyTypeEnum currencyType = CurrencyTypeEnum.values()[intValue];
        banksDataController.setCurrencyType(currencyType);


        switch (currencyType) {
            case AUD:
                buttonCurrencyType.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_australia, 0, R.drawable.icons_sort_down_enabled, 0);
                buttonCurrencyType.setText(CurrencyTypeEnum.AUD.toString());
                break;
            case CAD:
                buttonCurrencyType.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_canada, 0, R.drawable.icons_sort_down_enabled, 0);
                buttonCurrencyType.setText(CurrencyTypeEnum.CAD.toString());
                break;
            case XAU:
                buttonCurrencyType.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_gold, 0, R.drawable.icons_sort_down_enabled, 0);
                buttonCurrencyType.setText(CurrencyTypeEnum.XAU.toString());
                break;
            case USD:
                buttonCurrencyType.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_united_states, 0, R.drawable.icons_sort_down_enabled, 0);
                buttonCurrencyType.setText(CurrencyTypeEnum.USD.toString());
                break;
            case RUR:
                buttonCurrencyType.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_russia, 0, R.drawable.icons_sort_down_enabled, 0);
                buttonCurrencyType.setText(CurrencyTypeEnum.RUR.toString());
                break;
            case JPY:
                buttonCurrencyType.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_japan, 0, R.drawable.icons_sort_down_enabled, 0);
                buttonCurrencyType.setText(CurrencyTypeEnum.JPY.toString());
                break;
            case GEL:
                buttonCurrencyType.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_georgia, 0, R.drawable.icons_sort_down_enabled, 0);
                buttonCurrencyType.setText(CurrencyTypeEnum.GEL.toString());
                break;
            case GBP:
                buttonCurrencyType.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_united_kingdom, 0, R.drawable.icons_sort_down_enabled, 0);
                buttonCurrencyType.setText(CurrencyTypeEnum.GBP.toString());
                break;
            case EUR:
                buttonCurrencyType.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_european_union, 0, R.drawable.icons_sort_down_enabled, 0);
                buttonCurrencyType.setText(CurrencyTypeEnum.EUR.toString());
                break;
            case CHF:
                buttonCurrencyType.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_switzerland, 0, R.drawable.icons_sort_down_enabled, 0);
                buttonCurrencyType.setText(CurrencyTypeEnum.CHF.toString());
                break;
        }

        updateFilteredData();
    }

    private void updateFilteredData() {
        Map<String, Organization> organizationMap = banksDataController.getData().getValue();
        updateOrganizationData(organizationMap);
    }

    private void showErrorDialog(String errorMessage) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("ErrorMessageDialogFragment");
        if (prev != null) {
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_ERROR_MESSAGE, errorMessage);

        ft.addToBackStack(null);
        DialogFragment dialogFragment = new ErrorMessageDialogFragment();
        dialogFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().add(dialogFragment,"ErrorMessageDialogFragment").commit();
    }

    private void sortData(SortOrderEnum sortOrder) {
        BanksAdapter dataAdapter = (BanksAdapter) recyclerView.getAdapter();
        if (dataAdapter != null) {
            Map<String, Organization> organizationFilteredMap = dataAdapter.getData();

            if (banksDataController.getSortType() == SortTypeEnum.Purchase) {
                Map<String, Organization> organizationSortedMap = FilterHelperOrganization.sortMapForPurchase(organizationFilteredMap, banksDataController.getExchangeType(), banksDataController.getCurrencyType(), sortOrder);
                dataAdapter.updateData(organizationSortedMap, banksDataController.getExchangeType(), banksDataController.getCurrencyType());
            } else if (banksDataController.getSortType() == SortTypeEnum.Sale) {
                Map<String, Organization> organizationSortedMap = FilterHelperOrganization.sortMapForSale(organizationFilteredMap, banksDataController.getExchangeType(), banksDataController.getCurrencyType(), sortOrder);
                dataAdapter.updateData(organizationSortedMap, banksDataController.getExchangeType(), banksDataController.getCurrencyType());
            } else {
                dataAdapter.updateData(organizationFilteredMap, banksDataController.getExchangeType(), banksDataController.getCurrencyType());
            }
        }
    }

    public void updateRecyclerView(Map<String, Organization> organizationMap) {
        BanksAdapter dataAdapter = (BanksAdapter) recyclerView.getAdapter();
        if (dataAdapter != null) {
            dataAdapter.updateData(organizationMap, banksDataController.getExchangeType(), banksDataController.getCurrencyType());
        }
    }
}
