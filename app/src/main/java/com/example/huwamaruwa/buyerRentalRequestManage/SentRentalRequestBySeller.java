package com.example.huwamaruwa.buyerRentalRequestManage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.huwamaruwa.Models.SendingOffersModel;
import com.example.huwamaruwa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SentRentalRequestBySeller extends AppCompatActivity {

    RecyclerView sentOffers_recyclerView;
    DatabaseReference database;
    ArrayList<SendingOffersModel> list;
    SentRentalRequestBySellerAdapter sentRentalRequestBySellerAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_rental_request_by_seller);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Sent Requests");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Hooks
        sentOffers_recyclerView = findViewById(R.id.sentOffers_recyclerView);
        database = FirebaseDatabase.getInstance().getReference("SentOffersForProductRequest");
        sentOffers_recyclerView.setHasFixedSize(true);
        sentOffers_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        sentRentalRequestBySellerAdapter = new SentRentalRequestBySellerAdapter(list, this);
        sentOffers_recyclerView.setAdapter(sentRentalRequestBySellerAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SendingOffersModel sendingOffersModel = dataSnapshot.getValue(SendingOffersModel.class);
                    list.add(sendingOffersModel);
                }
                sentRentalRequestBySellerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void sentOffers_recyclerView() {


    }

//                ArrayList < SentRentalRequestsByASellerModel > sentRentalRequestsByASellerModels = new ArrayList<>();
//        sentRentalRequestsByASellerModels.add(new SentRentalRequestsByASellerModel(R.drawable.aaa , "450", "JBL","Music","2020-05-02"));
//        sentRentalRequestsByASellerModels.add(new SentRentalRequestsByASellerModel(R.drawable.aaa , "250", "JBL Boom","Music","2020-05-09"));
//        sentRentalRequestsByASellerModels.add(new SentRentalRequestsByASellerModel(R.drawable.aaa , "450", "JBL Jam","Music","2020-05-05"));
//        sentRentalRequestsByASellerModels.add(new SentRentalRequestsByASellerModel(R.drawable.aaa , "450", "JBL Jam","Music","2020-05-05"));
//        sentRentalRequestsByASellerModels.add(new SentRentalRequestsByASellerModel(R.drawable.aaa , "450", "JBL Jam","Music","2020-05-05"));
//
//        adapter = new SentRentalRequestBySellerAdapter(sentRentalRequestsByASellerModels);
//        sentOffers_recyclerView.setAdapter(adapter);

}
