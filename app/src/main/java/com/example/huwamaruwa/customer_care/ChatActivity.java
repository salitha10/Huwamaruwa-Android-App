package com.example.huwamaruwa.customer_care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import com.example.huwamaruwa.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    public void onResume() {
        super.onResume();

        //Chat list
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout_chat);
        ViewPager viewpager = (ViewPager)findViewById(R.id.View_pager_chat);

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());

        viewPageAdapter.addFragment(new ChatFragment(), "Chats");
        viewPageAdapter.addFragment(new UsersFragment(), "Users");

        viewpager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.getTabAt(0).setText("Chats");
        tabLayout.getTabAt(1).setText("Users");

    }


    //Initilaize tab layout
    class ViewPageAdapter extends FragmentPagerAdapter{

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPageAdapter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();

        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fr, String title){
            fragments.add(fr);
            titles.add(title);
        }
    }
}