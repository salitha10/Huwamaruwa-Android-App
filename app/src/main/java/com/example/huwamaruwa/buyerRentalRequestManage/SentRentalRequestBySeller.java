package com.example.huwamaruwa.buyerRentalRequestManage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.huwamaruwa.R;

import java.util.ArrayList;

public class SentRentalRequestBySeller extends AppCompatActivity {

    RecyclerView sentOffers_recyclerView;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_rental_request_by_seller);

        //Hooks
        sentOffers_recyclerView = findViewById(R.id.sentOffers_recyclerView);

        sentOffers_recyclerView();
    }

    private void sentOffers_recyclerView() {

        sentOffers_recyclerView.setHasFixedSize(true);
        sentOffers_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<SentRentalRequestsByASellerModel> sentRentalRequestsByASellerModels = new ArrayList<>();
        sentRentalRequestsByASellerModels.add(new SentRentalRequestsByASellerModel(R.drawable.aaa , "450", "JBL","Music","2020-05-02"));
        sentRentalRequestsByASellerModels.add(new SentRentalRequestsByASellerModel(R.drawable.aaa , "250", "JBL Boom","Music","2020-05-09"));
        sentRentalRequestsByASellerModels.add(new SentRentalRequestsByASellerModel(R.drawable.aaa , "450", "JBL Jam","Music","2020-05-05"));
        sentRentalRequestsByASellerModels.add(new SentRentalRequestsByASellerModel(R.drawable.aaa , "450", "JBL Jam","Music","2020-05-05"));
        sentRentalRequestsByASellerModels.add(new SentRentalRequestsByASellerModel(R.drawable.aaa , "450", "JBL Jam","Music","2020-05-05"));

        adapter = new SentRentalRequestBySellerAdapter(sentRentalRequestsByASellerModels);
        sentOffers_recyclerView.setAdapter(adapter);

    }
}