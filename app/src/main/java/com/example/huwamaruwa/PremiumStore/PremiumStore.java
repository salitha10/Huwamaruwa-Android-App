package com.example.huwamaruwa.PremiumStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;

import android.widget.TextView;

import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.singleProduct.PremiumProduct;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PremiumStore extends AppCompatActivity {
    private static final String TAG ="premiumStore" ;
    private String sellerID;
private DatabaseReference dbRef;
private DatabaseReference uDbRef;
private ArrayList<Product>product_list;
private RecyclerView recyclerView;
private TextView storeName;
private String sellerName;
private Toolbar toolbar;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_store);

        storeName = findViewById(R.id.txt_premium_store_seller_name);
        toolbar = findViewById(R.id.toolbar);
        //setup toolbar
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setTitle("Premium Store");
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    });


        product_list = new ArrayList<>();
        recyclerView = findViewById(R.id.premium_store_recycler);
        Intent intent = getIntent();
        sellerID = intent.getStringExtra(PremiumProduct.TAG_SELLER_ID);

    uDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
    Query query = uDbRef.orderByChild("userId").equalTo(sellerID);

    query.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.hasChildren()){
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    sellerName = dataSnapshot.child("name").getValue().toString();
                }
            }
            storeName.setText(sellerName);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });



    //get database reference
    dbRef = FirebaseDatabase.getInstance().getReference().child("Product");
    //query
    Query query1 = dbRef.orderByChild("sellerId").equalTo(sellerID);

    query1.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.hasChildren()){ //check whether query has value or not
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                   if (Boolean.parseBoolean(dataSnapshot.child("isPremium").getValue().toString())){ //check the product is premium or not
                       Product product = new Product();
                       product.setTitle(dataSnapshot.child("title").getValue().toString());
                       product.setPrice(Double.parseDouble(dataSnapshot.child("price").getValue().toString()));
                       product.setDescription(dataSnapshot.child("description").getValue().toString());
                       product.setImages1(dataSnapshot.child("images1").getValue().toString());
                       product.setImages2(dataSnapshot.child("images2").getValue().toString());
                       product.setImages3(dataSnapshot.child("images3").getValue().toString());
                       product.setImages4(dataSnapshot.child("images4").getValue().toString());
                       product.setIsPremium(Boolean.parseBoolean(dataSnapshot.child("isPremium").getValue().toString()));
                       product.setMinRentalTime(Integer.parseInt(dataSnapshot.child("minRentalTime").getValue().toString()));
                       product.setMaxRentalTime(Integer.parseInt(dataSnapshot.child("maxRentalTime").getValue().toString()));
                       product.setId(dataSnapshot.child("id").getValue().toString());
                       product.setContactNumber(dataSnapshot.child("contactNumber").getValue().toString());
                       product.setDate_in_day(Integer.parseInt(dataSnapshot.child("date_in_day").getValue().toString()));
                       product.setDate_in_hour(Integer.parseInt(dataSnapshot.child("date_in_hour").getValue().toString()));
                       product.setDate_in_min(Integer.parseInt(dataSnapshot.child("date_in_min").getValue().toString()));
                       product.setDate_in_sec(Integer.parseInt(dataSnapshot.child("date_in_sec").getValue().toString()));
                       product.setDate_in_year(Integer.parseInt(dataSnapshot.child("date_in_year").getValue().toString()));
                       product.setPerHour(Boolean.parseBoolean(dataSnapshot.child("perHour").getValue().toString()));
                       product.setDepositPercentage(Double.parseDouble(dataSnapshot.child("depositPercentage").getValue().toString()));
                       product.setLocation(dataSnapshot.child("location").getValue().toString());
                       product.setSellerId(dataSnapshot.child("sellerId").getValue().toString());
                       product_list.add(product);
                   }
                }
                //create adapter class object
                PremiumStoreAdapter adapter = new PremiumStoreAdapter(product_list,getApplicationContext());
                //setup adapter to recycler view
                recyclerView.setAdapter(adapter);
                //setup grid layout for recycler view
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
            }else Log.e(TAG,"Premium Store:Null object");
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });



    }
}