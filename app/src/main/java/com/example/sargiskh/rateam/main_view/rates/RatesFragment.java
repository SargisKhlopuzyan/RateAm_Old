package com.example.sargiskh.rateam.main_view.rates;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.sargiskh.rateam.R;
import com.example.sargiskh.rateam.main_view.rates.viewpager_fragment.banks.BanksFragment;
import com.example.sargiskh.rateam.decoration_fragment.cb.CBFragment;
import com.example.sargiskh.rateam.decoration_fragment.exchange_point.ExchangePointFragment;
import com.example.sargiskh.rateam.main_view.viewpager.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatesFragment extends Fragment {

    private ViewPager viewPager;

    private RadioGroup radioGroup;

    public RatesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rates, container, false);
        findViews(view);

        setupViewPager(viewPager);
        setupRadioButtons();

        return view;
    }

    private void findViews(View view) {
        viewPager = view.findViewById(R.id.viewpager);
        radioGroup = view.findViewById(R.id.radioGroup);
    }

    private void setupRadioButtons() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonBanks:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.radioButtonExchangePoint:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.radioButtonCB:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new BanksFragment(),null);
        adapter.addFragment(new ExchangePointFragment(),null);
        adapter.addFragment(new CBFragment(),null);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

}