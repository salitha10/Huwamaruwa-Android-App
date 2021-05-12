package com.example.huwamaruwa.Home.GridList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.huwamaruwa.Home.categoty_locations.CategoryListAdapter;
import com.example.huwamaruwa.Home.categoty_locations.LocationListAdapter;
import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GridList extends AppCompatActivity {
    DatabaseReference dbRef;
    ArrayList<Product>product_list;
    RecyclerView recyclerView;
    Query query;
    String location;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_list);
        product_list  = new ArrayList<>();
        recyclerView = findViewById(R.id.grid_recycler_griidList);
        toolbar = findViewById(R.id.toolbar);
        
        Intent intent = getIntent();
        String catTitle = intent.getStringExtra(CategoryListAdapter.TAG_CATEGORY_TITLE);
        
        //setup Toolbar
        //set Toolbar title

        String title = null;
        if (intent.getStringExtra(LocationListAdapter.TAG_Location_TITLE) != null){
            title = location;
        }else if (intent.getStringExtra(intent.getStringExtra(CategoryListAdapter.TAG_CATEGORY_TITLE)) != null){
            title = catTitle;
        }
        



        location = intent.getStringExtra(LocationListAdapter.TAG_Location_TITLE);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Product");

        if (intent.getStringExtra(CategoryListAdapter.TAG_CATEGORY_TITLE) != null ){
            query = dbRef.orderByChild("categoryID").equalTo(catTitle);
        }else{
            query = dbRef;
        }


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
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
                        if (intent.getStringExtra(LocationListAdapter.TAG_Location_TITLE) != null){
                            if (product.getLocation().equals(location)){
                                product_list.add(product);
                            }
                        }else  product_list.add(product);

                    }

                }
                    GridListAdapter adapter = new GridListAdapter(product_list,getApplicationContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}