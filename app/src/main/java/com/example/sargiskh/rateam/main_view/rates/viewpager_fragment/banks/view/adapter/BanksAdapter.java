package com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sargiskh.rateam.R;
import com.example.sargiskh.rateam.detail_view.view.DetailActivity;
import com.example.sargiskh.rateam.helper.HelperOrganization;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.model.Organization;
import com.example.sargiskh.rateam.enums.CurrencyTypeEnum;
import com.example.sargiskh.rateam.enums.ExchangeTypeEnum;
import com.example.sargiskh.rateam.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BanksAdapter extends RecyclerView.Adapter<BanksAdapter.DataAdapterViewHolder> {

    private Map<String, Organization> organizationMap = new HashMap<String, Organization>();

    private List<Organization> organizationList = new ArrayList();
    private List<String> organizationIdList = new ArrayList<>();

    private ExchangeTypeEnum exchangeType;
    private CurrencyTypeEnum currencyType;
    private Context context;


    public BanksAdapter(ExchangeTypeEnum exchangeType, CurrencyTypeEnum currencyType) {
        this.exchangeType = exchangeType;
        this.currencyType = currencyType;
    }

    @NonNull
    @Override
    public DataAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_recycler_view_bank, parent, false);
        return new DataAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapterViewHolder holder, int position) {
        holder.bindData(organizationList.get(position), organizationIdList.get(position));
    }

    @Override
    public int getItemCount() {
        return organizationMap.size();
    }

    public void updateData(Map<String, Organization> organizationMap, ExchangeTypeEnum exchangeType, CurrencyTypeEnum currencyType) {
        this.organizationMap = organizationMap;
        this.exchangeType = exchangeType;
        this.currencyType = currencyType;

        organizationList = HelperOrganization.getOrganizationList(organizationMap);
        organizationIdList = HelperOrganization.getOrganizationIdList(organizationMap);

        notifyDataSetChanged();
    }

    public Map<String, Organization> getData() {
        return organizationMap;
    }

    public class DataAdapterViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewBankName;
        private TextView textViewBankPurchase;
        private TextView textViewBankSale;

        public DataAdapterViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewBankName = itemView.findViewById(R.id.textViewBankName);
            textViewBankPurchase = itemView.findViewById(R.id.textViewBankPurchase);
            textViewBankSale = itemView.findViewById(R.id.textViewBankSale);
        }

        public void bindData(final Organization organization, final String organizationId) {

            imageView.setImageResource(R.drawable.icon_bank);
            textViewBankName.setText(organization.title);

            populatePurchaseAndSaleTextViews(organization, exchangeType, currencyType, textViewBankPurchase, textViewBankSale);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putString(Constants.BUNDLE_ORGANIZATION_ID, organizationId);
                    bundle.putSerializable(Constants.BUNDLE_ORGANIZATION_DETAIL, organization);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }

    private void populatePurchaseAndSaleTextViews(Organization organization, ExchangeTypeEnum exchangeType, CurrencyTypeEnum currencyType, TextView textViewBankPurchase, TextView textViewBankSale) {

        switch (exchangeType) {
            case Cash:
                textViewBankPurchase.setText(organization.currencyMap.get(currencyType.toString()).cash.buy.toString());
                textViewBankSale.setText(organization.currencyMap.get(currencyType.toString()).cash.sell.toString());
            case NonCash:
                textViewBankPurchase.setText(organization.currencyMap.get(currencyType.toString()).nonCash.buy.toString());
                textViewBankSale.setText(organization.currencyMap.get(currencyType.toString()).nonCash.sell.toString());
                break;
        }
    }

}