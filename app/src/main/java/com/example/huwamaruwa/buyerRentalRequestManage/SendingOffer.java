package com.example.huwamaruwa.buyerRentalRequestManage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.huwamaruwa.Home.Home_recycler_1_adapter;
import com.example.huwamaruwa.R;

public class SendingOffer extends AppCompatActivity {

    TextView productName, category, quantity, requiredDate, budget;
    BuyerRentalRequestsModel buyerRentalRequestsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_offer);

        productName = findViewById(R.id.sendingOffer_productName);
        category = findViewById(R.id.sendingOffer_category);
        quantity = findViewById(R.id.sendingOffer_quantity);
        requiredDate = findViewById(R.id.sendingOffer_requiredDate);
        budget = findViewById(R.id.sendingOffer_budget);

        buyerRentalRequestsModel = getIntent().getParcelableExtra(AllBuyerRequestsAdapter.EXTRA_MESSAGE);

        productName.setText(buyerRentalRequestsModel.getProductTitle());
        category.setText(buyerRentalRequestsModel.getCategory());
        quantity.setText(buyerRentalRequestsModel.getQuantity());
        requiredDate.setText(buyerRentalRequestsModel.getRequiredDate());
        budget.setText(buyerRentalRequestsModel.getBudget());
        
    }
}