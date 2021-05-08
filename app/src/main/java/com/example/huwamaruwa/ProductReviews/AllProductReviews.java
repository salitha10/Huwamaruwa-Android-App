package com.example.huwamaruwa.ProductReviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.huwamaruwa.Models.ProductReviews;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.buyerRentalRequestManage.AllBuyerRequestsAdapter;
import com.example.huwamaruwa.buyerRentalRequestManage.BuyerRentalRequestsModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllProductReviews extends AppCompatActivity {

    //variables
    RecyclerView recyclerView;
    DatabaseReference database;
    AllReviewsAdapter allReviewsAdapter;
    ArrayList<ProductReviews> list;
    DatabaseReference dbf;
    AllReviewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product_reviews);

        //Top fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft1 = fm.beginTransaction();
        ft1.add(R.id.myReviewLayout, new MyReviewFragment());
        ft1.commit();

        //Bottom recycler view
        recyclerView = findViewById(R.id.allReviewsRecycle);
        database = FirebaseDatabase.getInstance().getReference("ProductReviews");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        AllReviewsAdapter adapter = new AllReviewsAdapter(list,this);
        recyclerView.setAdapter(adapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductReviews pr = dataSnapshot.getValue(ProductReviews.class);
                    list.add(pr);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
