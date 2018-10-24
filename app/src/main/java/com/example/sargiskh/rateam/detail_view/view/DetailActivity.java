package com.example.sargiskh.rateam.detail_view.view;

import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
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
import com.example.sargiskh.rateam.dialog.ErrorMessageDialogFragment;
import com.example.sargiskh.rateam.enums.DaysOfWeekEnum;
import com.example.sargiskh.rateam.enums.ExchangeTypeEnum;
import com.example.sargiskh.rateam.helper.HelperBranch;
import com.example.sargiskh.rateam.main_view.MainActivity;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.dialog.exchenge_type_dialog.ExchangeTypeDialogFragment;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.Organization;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.view.BanksFragment;
import com.example.sargiskh.rateam.util.Constants;

import java.lang.reflect.Method;
import java.time.DayOfWeek;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements DetailViewInterface, DetailViewBranchListAdapter.BranchSelectedInterface {

    private final int REQUEST_PHONE_CALL = 0;

    private Menu menu;

    private NestedScrollView nestedScrollView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;

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

    private DetailViewPresenter detailViewPresenter;
    private DetailViewDataController detailViewDataController;

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
        setListeners();

        setSupportActionBar(toolbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    if (!swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setEnabled(false);
                    }
                }

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

        setupSwipeRefreshLayout();
        populateCurrencyRecyclerViews();
        populateBranchesRecyclerViews();
        setOrganizationName();
        setupRadioButtons();

        if (liveData.getValue() == null) {
            detailViewPresenter.getOrganizationDetailData(detailViewDataController.getOrganizationId());
        }
    }

    private void setListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                detailViewPresenter.getOrganizationDetailData(detailViewDataController.getOrganizationId());
            }
        });
    }

    private void setupSwipeRefreshLayout() {
        swipeRefreshLayout.setProgressViewOffset(false, 0, 300);
    }

    private void setOrganizationName() {
        textViewOrganizationName.setText(organization.title);
        imageView.setImageResource(R.drawable.icon_bank); // TODO - Need to be replaced with appropriate bank icon
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
        if (branch == null && branchList.size() > 0) {
            branch = branchList.get(0);
        }
        updateBranchInfo(branch);

        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
        recyclerViewBranchList.setVisibility(View.VISIBLE);

    }

    @Override
    public void displayError(String errorMessage) {
        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
        showErrorDialog(errorMessage);
    }

    private void findViews() {
        nestedScrollView = findViewById(R.id.nestedScrollView);
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
        appBarLayout.setExpanded(true);
        nestedScrollView.scrollTo(0, 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        detailViewDataController.setData(null);
    }

    private void updateBranchInfo(Branch branch) {
        if (branch == null) {
            return;
        }
        String title = branch.title.am != null ? branch.title.am : branch.title.ru;
        title = title != null ? title : branch.title.en;
        title = title != null ? title : "";

        String address = branch.address.am != null ? branch.address.am : branch.address.ru;
        address = address != null ? address : branch.address.en;
        address = address != null ? address : "";

        String contacts = branch.contacts;
        contacts = contacts.replaceAll("[() ]","");
        contacts = (contacts.charAt(0) != '+' ? "+" : "") + contacts;

        String workingDayTime = "";
        for (int i = 0; i < branch.workhours.size(); i++) {
            if (i != 0) {
                workingDayTime += "\n";
            }
            String workingDays = branch.workhours.get(i).days.trim();
            if (workingDays.contains("-")) {
                workingDays = DaysOfWeekEnum.values()[Integer.parseInt("" + workingDays.charAt(0))].toString() + " - " + DaysOfWeekEnum.values()[Integer.parseInt("" + workingDays.charAt(2))].toString();
                workingDays = workingDays.substring(0, 1).toUpperCase() + workingDays.substring(1);
            } else {
                workingDays = DaysOfWeekEnum.values()[Integer.parseInt("" + workingDays.charAt(0))].toString().toUpperCase();
            }

            workingDayTime += workingDays + "   " + branch.workhours.get(i).hours;
        }

        textViewOrganizationTitle.setText(title);
        textViewOrganizationAddress.setText(address);
        SpannableString content = new SpannableString(contacts);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textViewOrganizationPhoneNumbers.setText(content);
        textViewOrganizationWorkingDaysHours.setText(workingDayTime);


        if (!contacts.isEmpty()) {
            textViewOrganizationPhoneNumbers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                    }
                    else
                    {
                        makeACall();
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeACall();
                }
                return;
            }
        }
    }

    private void makeACall() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        final String uri = "tel:" + textViewOrganizationPhoneNumbers.getText().toString().trim();
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    private void showErrorDialog(String errorMessage) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("ErrorMessageDialogFragment");
        if (prev != null) {
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString(Constants.BUNDLE_ERROR_MESSAGE, errorMessage);

        ft.addToBackStack(null);
        DialogFragment dialogFragment = new ErrorMessageDialogFragment();
        dialogFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(dialogFragment,"ErrorMessageDialogFragment").commit();
    }

}
