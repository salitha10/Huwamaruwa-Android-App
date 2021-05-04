package com.example.huwamaruwa.ProductReviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import com.example.huwamaruwa.R;

public class allProductReviews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product_reviews);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft1 = fm.beginTransaction();
        ft1.add(R.id.myReviewLayout, new myReviewFragment());
        ft1.commit();

        FragmentTransaction ft2 = fm.beginTransaction();
        ft2.add(R.id.allReviewLayout, new OtherReviewsFragment());
        ft2.commit();

    }
}