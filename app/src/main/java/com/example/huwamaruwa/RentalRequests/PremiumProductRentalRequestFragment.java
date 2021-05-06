package com.example.huwamaruwa.RentalRequests;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.huwamaruwa.Models.RequestRentModel;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.RentalRequests.tabItems.PagerAdapter;
import com.example.huwamaruwa.RentalRequests.tabItems.PremiumCompletedTab;
import com.example.huwamaruwa.RentalRequests.tabItems.PremiumPendingTab;
import com.example.huwamaruwa.RentalRequests.tabItems.PremiumProcessingTab;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;


public class PremiumProductRentalRequestFragment extends Fragment {


    ViewPager viewPager;
    TabLayout tabLayout;
    PremiumCompletedTab premiumCompletedTab;
    PremiumPendingTab premiumPendingTab;
    PremiumProcessingTab premiumProcessingTab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_non_premium__requests_seller_side, container, false);

        viewPager = view.findViewById(R.id.viewPager_request_rent);
        tabLayout = view.findViewById(R.id.tab_layout_rental_request);

        premiumCompletedTab = new PremiumCompletedTab();
        premiumPendingTab = new PremiumPendingTab();
        premiumProcessingTab = new PremiumProcessingTab();

        tabLayout.setupWithViewPager(viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(),1);
        pagerAdapter.addFragment(premiumPendingTab,"Pending");
        pagerAdapter.addFragment(premiumProcessingTab,"Processing");
        pagerAdapter.addFragment(premiumCompletedTab,"Completed");
        viewPager.setAdapter(pagerAdapter);
        return view;
    }
}