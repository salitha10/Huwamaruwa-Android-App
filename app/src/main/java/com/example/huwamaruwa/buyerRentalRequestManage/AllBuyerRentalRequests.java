package com.example.huwamaruwa.buyerRentalRequestManage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.huwamaruwa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllBuyerRentalRequests extends AppCompatActivity {

    //variables
    Toolbar toolbar;
    RecyclerView recyclerView;
    DatabaseReference database;
    AllBuyerRequestsAdapter allBuyerRequestsAdapter;
    ArrayList<BuyerRentalRequestsModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_buyer_rental_requests);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Product Requests");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.allBuyerRequests_recyclerView);
        database = FirebaseDatabase.getInstance().getReference("BuyerRentalRequest");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        allBuyerRequestsAdapter = new AllBuyerRequestsAdapter(list,this);
        recyclerView.setAdapter(allBuyerRequestsAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BuyerRentalRequestsModel buyerRentalRequestsModel = dataSnapshot.getValue(BuyerRentalRequestsModel.class);
                    list.add(buyerRentalRequestsModel);
                }
                allBuyerRequestsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}