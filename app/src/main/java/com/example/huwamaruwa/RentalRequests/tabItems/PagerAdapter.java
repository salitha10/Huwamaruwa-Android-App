package com.example.huwamaruwa.RentalRequests.tabItems;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments_list = new ArrayList<>();
    private List<String> fragment_title_list = new ArrayList<>();
    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void addFragment(Fragment fragment,String title){
        fragments_list.add(fragment);
        fragment_title_list.add(title);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments_list.get(position);
    }

    @Override
    public int getCount() {
        return fragments_list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragment_title_list.get(position);
    }
}
