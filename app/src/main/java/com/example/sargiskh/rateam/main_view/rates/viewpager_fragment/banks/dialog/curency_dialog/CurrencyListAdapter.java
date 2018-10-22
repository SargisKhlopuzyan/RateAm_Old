package com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.dialog.curency_dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sargiskh.rateam.R;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.dialog.curency_dialog.model.CurrencyListDataModel;
import com.example.sargiskh.rateam.enums.CurrencyTypeEnum;

import java.util.List;

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.DataAdapterViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(CurrencyTypeEnum currencyType);
    }
    private final OnItemClickListener listener;

    private Context context;
    private List<CurrencyListDataModel> dataModels;
    private CurrencyTypeEnum selectedCurrencyType;

    public CurrencyListAdapter(OnItemClickListener listener, List<CurrencyListDataModel> currencyList, CurrencyTypeEnum selectedCurrencyType) {
        this.listener = listener;
        this.dataModels = currencyList;
        this.selectedCurrencyType = selectedCurrencyType;
    }

    @NonNull
    @Override
    public DataAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_recycler_view_currency_list, parent, false);
        return new DataAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapterViewHolder holder, int position) {
        holder.bind(listener, context, dataModels.get(position), selectedCurrencyType.equals(CurrencyTypeEnum.values()[position]) ? true : false);
    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }


    public class DataAdapterViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView imageView;
        private TextView textViewCurrency;
        private TextView textViewCurrencyDescription;
        private CheckBox checkbox;

        public DataAdapterViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewCurrency = itemView.findViewById(R.id.textViewCurrency);
            textViewCurrencyDescription = itemView.findViewById(R.id.textViewCurrencyDescription);
            checkbox = itemView.findViewById(R.id.checkbox);
        }

        public void bind(final OnItemClickListener listener, Context contextt, final CurrencyListDataModel dataModel, boolean isCheckBoxVisible) {

            Drawable d = contextt.getResources().getDrawable(dataModel.getIconId());
            imageView.setImageDrawable(d);
            textViewCurrency.setText(dataModel.getCurrency().toString());
            textViewCurrencyDescription.setText(dataModel.getCurrencyDescription());
            checkbox.setVisibility( isCheckBoxVisible ? View.VISIBLE : View.GONE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CurrencyTypeEnum currencyType = CurrencyTypeEnum.values()[getAdapterPosition()];
                    listener.onItemClick(currencyType);
                }
            });
        }

    }
}