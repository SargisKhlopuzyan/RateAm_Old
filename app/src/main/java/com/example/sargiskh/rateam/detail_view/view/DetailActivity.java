package com.example.sargiskh.rateam.detail_view.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sargiskh.rateam.R;
import com.example.sargiskh.rateam.detail_view.adapter.DetailViewBranchListAdapter;
import com.example.sargiskh.rateam.detail_view.adapter.DetailViewCurrencyListAdapter;
import com.example.sargiskh.rateam.detail_view.model.Branch;
import com.example.sargiskh.rateam.detail_view.model.ResponseBranches;
import com.example.sargiskh.rateam.detail_view.presenter.DetailViewDataController;
import com.example.sargiskh.rateam.detail_view.presenter.DetailViewPresenter;
import com.example.sargiskh.rateam.enums.ExchangeTypeEnum;
import com.example.sargiskh.rateam.helper.HelperBranch;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.Organization;
import com.example.sargiskh.rateam.util.Constants;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements DetailViewInterface, DetailViewBranchListAdapter.BranchSelectedInterface {

    private Menu menu;

    private ImageView imageView;
    private TextView textViewOrganizationName;
    private TextView textViewOrganizationTitle;
    private TextView textViewOrganizationAddress;
    private TextView textViewOrganizationPhoneNumbers;
    private TextView textViewOrganizationWorkingDaysHours;
    private Button buttonViewOnTheMap;

    private RadioGroup radioGroup;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerViewCurrencyList;
    private RecyclerView recyclerViewBranchList;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;

    private DetailViewPresenter detailViewPresenter;
    private DetailViewDataController detailViewDataController;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private Organization organization;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailViewPresenter = new DetailViewPresenter(this);
        detailViewDataController = DetailViewDataController.getInstance();

        String organizationId = getIntent().getStringExtra(Constants.BUNDLE_ORGANIZATION_ID);
        organization = (Organization) getIntent().getSerializableExtra(Constants.BUNDLE_ORGANIZATION_DETAIL);
        detailViewDataController.setOrganizationId(organizationId);

        findViews();

        setSupportActionBar(toolbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(organization.title);
                    toolbar.setTitle(organization.title);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    toolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });

        LiveData<ResponseBranches> liveData = DetailViewDataController.getInstance().getData();
        liveData.observe(this, new Observer<ResponseBranches>() {
            @Override
            public void onChanged(@Nullable ResponseBranches value) {
                updateData(value);
            }
        });

        populateCurrencyRecyclerViews();
        populateBranchesRecyclerViews();
        setOrganizationName();
        setupRadioButtons();

        if (liveData.getValue() == null) {
            detailViewPresenter.getOrganizationDetailData(detailViewDataController.getOrganizationId());
        }
    }

    private void setOrganizationName() {
        textViewOrganizationName.setText(organization.title);
        imageView.setImageResource(R.drawable.icon_bank); // TODO
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void updateData(ResponseBranches responseBranches) {

        DetailViewBranchListAdapter dataAdapter = (DetailViewBranchListAdapter) recyclerViewBranchList.getAdapter();

        List<Branch> branchList = HelperBranch.getBranchList(responseBranches);
        if (dataAdapter != null) {
            dataAdapter.updateData(branchList);
        }

        Branch branch = HelperBranch.getHeadBranch(responseBranches, 1);
        updateBranchInfo(branch);

        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
//        textViewStatus.setVisibility(View.GONE);
        recyclerViewBranchList.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayError(String errorMessage) {

    }

    private void findViews() {
        imageView = findViewById(R.id.imageView);
        textViewOrganizationName = findViewById(R.id.textViewOrganizationName);
        textViewOrganizationTitle = findViewById(R.id.textViewOrganizationTitle);
        textViewOrganizationAddress = findViewById(R.id.textViewOrganizationAddress);
        textViewOrganizationPhoneNumbers = findViewById(R.id.textViewOrganizationPhoneNumbers);
        textViewOrganizationWorkingDaysHours = findViewById(R.id.textViewOrganizationWorkingDaysHours);
        buttonViewOnTheMap = findViewById(R.id.buttonViewOnTheMap);
        radioGroup = findViewById(R.id.radioGroup);

        toolbar = findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.app_bar);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerViewCurrencyList = findViewById(R.id.recyclerViewCurrencyList);
        recyclerViewBranchList = findViewById(R.id.recyclerViewBranchList);
    }

    private void setupRadioButtons() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                DetailViewCurrencyListAdapter dataAdapter = (DetailViewCurrencyListAdapter) recyclerViewCurrencyList.getAdapter();
                if (dataAdapter == null) {
                    return;
                }

                switch (checkedId) {
                    case R.id.radioButtonCash:
                        dataAdapter.setExchangeType(ExchangeTypeEnum.Cash);
                        break;
                    case R.id.radioButtonNonCash:
                        dataAdapter.setExchangeType(ExchangeTypeEnum.NonCash);
                        break;
                }
            }
        });
    }

    private void populateCurrencyRecyclerViews() {
        recyclerViewCurrencyList.setHasFixedSize(true);
        recyclerViewCurrencyList.setLayoutManager(new LinearLayoutManager(this));
        DetailViewCurrencyListAdapter detailViewCurrencyListAdapter = new DetailViewCurrencyListAdapter(organization, detailViewDataController.getExchangeType());
        recyclerViewCurrencyList.setAdapter(detailViewCurrencyListAdapter);
    }

    private void populateBranchesRecyclerViews() {
        recyclerViewBranchList.setHasFixedSize(true);
        recyclerViewBranchList.setLayoutManager(new LinearLayoutManager(this));
        DetailViewBranchListAdapter detailViewCurrencyListAdapter = new DetailViewBranchListAdapter(this);
        recyclerViewBranchList.setAdapter(detailViewCurrencyListAdapter);
    }

    @Override
    public void onBranchListItemClicked(Branch branch) {
        updateBranchInfo(branch);
    }

    private void updateBranchInfo(Branch branch) {
        if (branch == null) {
            return;
        }
        textViewOrganizationTitle.setText(branch.title.am);
        textViewOrganizationAddress.setText(branch.address.am);
        textViewOrganizationPhoneNumbers.setText(branch.contacts);

        String workingDayTime = "";
        for (int i = 0; i < branch.workhours.size(); i++) {
            if (i != 0) {
                workingDayTime += "\n";
            }
            workingDayTime += branch.workhours.get(i).days + "   " + branch.workhours.get(i).hours;
        }

        textViewOrganizationWorkingDaysHours.setText(workingDayTime);
    }
}
