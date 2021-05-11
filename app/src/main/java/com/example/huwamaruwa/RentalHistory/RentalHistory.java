package com.example.huwamaruwa.RentalHistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.huwamaruwa.Models.RentalHistoryModel;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.singleProduct.PremiumProduct;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RentalHistory extends AppCompatActivity {
private String productId;
private DatabaseReference dbRef;
private ArrayList<RentalHistoryModel>request_list;
private ArrayList<String>userId_list;
private  RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_history);

        request_list = new ArrayList<>();
        userId_list = new ArrayList<>();
        dbRef = FirebaseDatabase.getInstance().getReference().child("RequestRent");
        recyclerView = findViewById(R.id.premium_history_recycler);

       Intent intent = getIntent();
       productId = intent.getStringExtra(PremiumProduct.TAG_PRODUCT_ID);

        Query query = dbRef.orderByChild("productId").equalTo(productId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        RentalHistoryModel rentalHistoryModel = new RentalHistoryModel();
                        rentalHistoryModel.setStatus(dataSnapshot.child("status").getValue().toString());
                        rentalHistoryModel.setRentDate(dataSnapshot.child("duration").getValue().toString());
                        userId_list.add(dataSnapshot.child("userId").getValue().toString());
                        request_list.add(rentalHistoryModel);
                    }
                }
                RentalHistoryAdapter adapter = new RentalHistoryAdapter(request_list,getApplicationContext(),userId_list);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }
}