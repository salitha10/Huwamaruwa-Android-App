package com.example.huwamaruwa.ReviewUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.huwamaruwa.Models.SellerReview;
import com.example.huwamaruwa.ReviewUser.AllSellerReviewsAdapter;
import com.example.huwamaruwa.ReviewUser.MySellerReview;
import com.example.huwamaruwa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllSellerReviews extends AppCompatActivity {

    //variables
    RecyclerView recyclerView;
    DatabaseReference database;
    AllSellerReviewsAdapter allReviewsAdapter;
    ArrayList<SellerReview> list;
    DatabaseReference dbf;
    AllSellerReviewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_seller_reviews);

        //Top fragment
        MySellerReview FR = new MySellerReview();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft1 = fm.beginTransaction();
        ft1.add(R.id.myReviewLayout, FR).hide(FR);
        ft1.commit();

        //Bottom recycler view
        recyclerView = findViewById(R.id.allReviewsRecycle);
        database = FirebaseDatabase.getInstance().getReference("SellerReview");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        AllSellerReviewsAdapter adapter = new AllSellerReviewsAdapter(list, this);
        recyclerView.setAdapter(adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String cUser = user.getUid();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SellerReview pr = dataSnapshot.getValue(SellerReview.class);
                    if (pr.getReviewerID().equals(cUser)) {
                        list.add(pr);

                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error", "DB Cancelled");
            }
        });

    }
}