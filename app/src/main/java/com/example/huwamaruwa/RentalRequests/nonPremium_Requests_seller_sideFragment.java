package com.example.huwamaruwa.RentalRequests;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huwamaruwa.R;
import com.example.huwamaruwa.RentalRequests.tabItems.PagerAdapter;
import com.example.huwamaruwa.RentalRequests.tabItems.TabCompleted;
import com.example.huwamaruwa.RentalRequests.tabItems.TabPending;
import com.example.huwamaruwa.RentalRequests.tabItems.TabProcessing;

import com.google.android.material.tabs.TabLayout;


public class nonPremium_Requests_seller_sideFragment extends Fragment {

ViewPager viewPager;
TabLayout tabLayout;
TabCompleted tabCompleted;
TabPending tabPending;
TabProcessing tabProcessing;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_non_premium__requests_seller_side, container, false);

        viewPager = view.findViewById(R.id.viewPager_request_rent);
        tabLayout = view.findViewById(R.id.tab_layout_rental_request);

        tabCompleted = new TabCompleted();
        tabPending = new TabPending();
        tabProcessing = new TabProcessing();


        tabLayout.setupWithViewPager(viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(),0);
        pagerAdapter.addFragment(tabPending,"Pending");
        pagerAdapter.addFragment(tabProcessing,"Processing");
        pagerAdapter.addFragment(tabCompleted,"Completed");
        viewPager.setAdapter(pagerAdapter);


        return view;
    }
}