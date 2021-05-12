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
import com.example.huwamaruwa.ReviewUser.AllSellerReviewAdapter;
import com.example.huwamaruwa.ProductReviews.MyReviewFragment;
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

    RecyclerView recyclerView;
    DatabaseReference database;
    AllSellerReviewAdapter allReviewsAdapter;
    ArrayList<SellerReview> list;
    DatabaseReference dbf;
    AllSellerReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_seller_reviews);

       // String sID = getIntent().getStringExtra("SellerID");
        //Log.d("SellerID", sID);

        String sID = "-M_TegdG5KJNDkmprM22";

        MyReviewFragment FR = new MyReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("SellerID", sID);
        FR.setArguments(bundle);

        //Top fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft1 = fm.beginTransaction();
        ft1.add(R.id.myReviewLayout, FR).hide(FR);
        ft1.commit();

        //Bottom recycler view
        recyclerView = findViewById(R.id.allReviewsRecycle);
        database = FirebaseDatabase.getInstance().getReference("SellerReviews");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Init recyclerview
        list = new ArrayList<>();
        AllSellerReviewAdapter adapter = new AllSellerReviewAdapter(list,this);
        recyclerView.setAdapter(adapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String cUser = user.getUid();
        String cUser = "Lud7rSb7CyeJLQt7saQOVYTZv953";

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SellerReview sr = dataSnapshot.getValue(SellerReview.class);
                    if(sr.getReviewerID().equals(sID) && !sr.getReviewerID().equals(cUser)) {
                        list.add(sr);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error", "DB Cancelled");
            }
        });

    }
}