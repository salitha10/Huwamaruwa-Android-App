package com.example.huwamaruwa.buyerRentalRequestManage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huwamaruwa.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BuyerRentalRequest extends AppCompatActivity {

    EditText productTitle, category, quantity, requiredDate, budget;
    Button submit, cancel;
    DatabaseReference dbf;
    BuyerRentalRequestsModel buyerRentalRequestsModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_rental_request);

        productTitle = (EditText)findViewById(R.id.prouctNameEdit);
        category = (EditText)findViewById(R.id.productCategoryEdit);
        quantity = (EditText)findViewById(R.id.productQuantityEdit);
        requiredDate = (EditText)findViewById(R.id.requiredDateEdit);
        budget = (EditText)findViewById(R.id.budgetEdit);

        cancel =findViewById(R.id.buyerReqCancel);
        submit =findViewById(R.id.buyerReqsubmit);

        buyerRentalRequestsModel = new BuyerRentalRequestsModel();


    }
    private void clearContent(){
        productTitle.setText("");
        category.setText("");
        quantity.setText("");
        requiredDate.setText("");
        budget.setText("");
    }

    public void Save(View v){
        try {
            dbf = FirebaseDatabase.getInstance().getReference().child("BuyerRentalRequest");

            if (TextUtils.isEmpty(productTitle.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Text Field is empty", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(category.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Text Field is empty", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(quantity.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Text Field is empty", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(requiredDate.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Text Field is empty", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(budget.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Text Field is empty", Toast.LENGTH_SHORT).show();
            }
            else{

                buyerRentalRequestsModel.setProductTitle(productTitle.getText().toString().trim());
                buyerRentalRequestsModel.setCategory(category.getText().toString().trim());
                buyerRentalRequestsModel.setQuantity(quantity.getText().toString().trim());
                buyerRentalRequestsModel.setRequiredDate(requiredDate.getText().toString().trim());
                buyerRentalRequestsModel.setBudget(budget.getText().toString().trim());

                dbf.push().setValue(buyerRentalRequestsModel);

                clearContent();

                Toast.makeText(getApplicationContext(), "Request added successfully", Toast.LENGTH_LONG).show();
            }


        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Failed adding the request", Toast.LENGTH_SHORT).show();

        }


    }
}