package com.example.sargiskh.rateam.main_view;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sargiskh.rateam.R;
import com.example.sargiskh.rateam.decoration_fragment.calculator.CalculatorFragment;
import com.example.sargiskh.rateam.decoration_fragment.more.MoreFragment;
import com.example.sargiskh.rateam.decoration_fragment.transaction.TransactionFragment;
import com.example.sargiskh.rateam.main_view.rates.RatesFragment;

import com.example.sargiskh.rateam.main_view.viewpager.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.transaction_selecor,
            R.drawable.rates_selecor,
            R.drawable.calculator_selecor,
            R.drawable.more_selecor
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TransactionFragment(), getResources().getString(R.string.transaction));
        adapter.addFragment(new RatesFragment(), getResources().getString(R.string.rates));
        adapter.addFragment(new CalculatorFragment(), getResources().getString(R.string.calculator));
        adapter.addFragment(new MoreFragment(), getResources().getString(R.string.more));
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

}
