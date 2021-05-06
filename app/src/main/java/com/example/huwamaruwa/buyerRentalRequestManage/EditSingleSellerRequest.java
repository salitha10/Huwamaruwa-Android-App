package com.example.huwamaruwa.buyerRentalRequestManage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huwamaruwa.Models.SendingOffersModel;
import com.example.huwamaruwa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditSingleSellerRequest extends AppCompatActivity {

    EditText sendingProductTitle, sendingRental, sendingQuantity;
    TextView requestId;
    SendingOffersModel sendingOffersModel;
    Button update, cancel;
    DatabaseReference dbf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_single_seller_request);


        sendingProductTitle = findViewById(R.id.sendingForm_productName);
        requestId = findViewById(R.id.sendingForm_requestId);
        sendingRental = findViewById(R.id.sendingForm_rental);
        sendingQuantity = findViewById(R.id.sendingForm_quantity);
        update = findViewById(R.id.btnSendingForm_sendOffer);
        cancel = findViewById(R.id.btnSendingForm_cancel);

        sendingOffersModel = getIntent().getParcelableExtra(SentRentalRequestBySellerAdapter.EXTRA_MESSAGE1);

        sendingProductTitle.setText(sendingOffersModel.getProductName());
        sendingQuantity.setText(sendingOffersModel.getQuantity());
        sendingRental.setText(sendingOffersModel.getRental());
        requestId.setText(sendingOffersModel.getProductRequestId());

    }

    public void UpdateUser(View view){
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
            dbf.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(requestId.getText().toString())){
                        sendingOffersModel.setProductName(sendingProductTitle.getText().toString());
                        sendingOffersModel.setQuantity(sendingQuantity.getText().toString());
                        sendingOffersModel.setRental(sendingRental.getText().toString());

                        dbf = FirebaseDatabase.getInstance().getReference().child("SentOffersForProductRequest").child(requestId.getText().toString());
                        dbf.setValue(sendingOffersModel);
                        dbf.keepSynced(true);

                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(EditSingleSellerRequest.this,SentRentalRequestBySeller.class);
                        startActivity(intent);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}