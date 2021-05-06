package com.example.huwamaruwa.Home;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Home_fragment extends Fragment {

    RecyclerView recyclerView1,recyclerView2,recyclerView3;
    ArrayList<Product>product_list_latest;
    ArrayList<Product>product_list_history;
    ArrayList<Product>product_list_new;
    DatabaseReference dRef_latest;
    Home_recycler_1_adapter home_recycler_1_adapter;
    Home_recycler_2_adapter home_recycler_2_adapter;



    public Home_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        recyclerView1 = view.findViewById(R.id.home_recycler_view_1);
        recyclerView2 = view.findViewById(R.id.home_recycler_view_2);
        recyclerView3 = view.findViewById(R.id.home_recycler_view_3);


        product_list_latest = new ArrayList<>();
        product_list_history = new ArrayList<>();
        product_list_new = new ArrayList<>();

        dRef_latest = FirebaseDatabase.getInstance().getReference();
        dRef_latest.keepSynced(true);
        Query query = dRef_latest.child("Product");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
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

                    product_list_latest.add(product);
                }
                home_recycler_1_adapter = new Home_recycler_1_adapter(product_list_latest, getContext());
                home_recycler_2_adapter = new Home_recycler_2_adapter(product_list_latest, getContext());
                recyclerView1.setAdapter(home_recycler_1_adapter);
                recyclerView2.setAdapter(home_recycler_2_adapter);
                recyclerView3.setAdapter(home_recycler_2_adapter);
                home_recycler_1_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        return view;
    }


}