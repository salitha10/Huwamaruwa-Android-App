package com.example.huwamaruwa.buyerRentalRequestManage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huwamaruwa.Home.Home_recycler_1_adapter;
import com.example.huwamaruwa.Models.SendingOffersModel;
import com.example.huwamaruwa.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SendingOffer extends AppCompatActivity {

    TextView productName, category, quantity, requiredDate, budget, buyerRequestId;
    BuyerRentalRequestsModel buyerRentalRequestsModel;
    EditText sendingProductTitle, sendingRental, sendingQuantity;
    Button sendingOfferSubmit, getSendingOfferCancel;
    DatabaseReference dbf;
    SendingOffersModel sendingOffersModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_offer);

        productName = findViewById(R.id.sendingOffer_productName);
        category = findViewById(R.id.sendingOffer_category);
        quantity = findViewById(R.id.sendingOffer_quantity);
        requiredDate = findViewById(R.id.sendingOffer_requiredDate);
        budget = findViewById(R.id.sendingOffer_budget);
        buyerRequestId = findViewById(R.id.sendingOffer_buyerRequestId);

        buyerRentalRequestsModel = getIntent().getParcelableExtra(AllBuyerRequestsAdapter.EXTRA_MESSAGE);

        productName.setText(buyerRentalRequestsModel.getProductTitle());
        category.setText(buyerRentalRequestsModel.getCategory());
        quantity.setText(buyerRentalRequestsModel.getQuantity());
        requiredDate.setText(buyerRentalRequestsModel.getRequiredDate());
        budget.setText(buyerRentalRequestsModel.getBudget());
        buyerRequestId.setText(buyerRentalRequestsModel.getBuyerRequestId());

        //sending Offer Details
        sendingProductTitle = findViewById(R.id.sendingForm_productName);
        sendingRental = findViewById(R.id.sendingForm_rental);
        sendingQuantity = findViewById(R.id.sendingForm_quantity);
        sendingOfferSubmit = findViewById(R.id.btnSendingForm_sendOffer);
        getSendingOfferCancel = findViewById(R.id.btnSendingForm_cancel);

        sendingOffersModel = new SendingOffersModel();

    }

    public void sendOffer(View view){
        try {


            dbf = FirebaseDatabase.getInstance().getReference().child("SentOffersForProductRequest");

            if (TextUtils.isEmpty(sendingProductTitle.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Text Field is empty", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(sendingQuantity.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Text Field is empty", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(sendingRental.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Text Field is empty", Toast.LENGTH_SHORT).show();
            }
            else{
                sendingOffersModel.setProductName(sendingProductTitle.getText().toString().trim());
                sendingOffersModel.setQuantity(sendingQuantity.getText().toString().trim());
                sendingOffersModel.setRental(sendingRental.getText().toString().trim());
                sendingOffersModel.setStatus("Pending".trim());
                sendingOffersModel.setSendingRequestId(buyerRequestId.getText().toString().trim());
                sendingOffersModel.setProductRequestId(dbf.push().getKey());
                sendingOffersModel.setProductImage("".trim());

//                dbf.push().setValue(sendingOffersModel);
                dbf.child(sendingOffersModel.getProductRequestId()).setValue(sendingOffersModel);


                Toast.makeText(getApplicationContext(), "Request added successfully", Toast.LENGTH_LONG).show();

            }
        }catch (Exception e){

        }

    }
}