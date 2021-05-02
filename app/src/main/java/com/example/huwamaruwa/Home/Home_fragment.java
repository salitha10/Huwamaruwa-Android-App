package com.example.huwamaruwa.Home;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home_fragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Product>product_list;
    DatabaseReference dRef;
    Home_recycler_1_adapter home_recycler_1_adapter;

    public Home_fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        recyclerView = view.findViewById(R.id.home_recycler_view_1);
        product_list = new ArrayList<>();
        dRef = FirebaseDatabase.getInstance().getReference();

        Query query = dRef.child("Product");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product = new Product();
                    product.setTitle(dataSnapshot.child("title").getValue().toString());
                    product.setPrice(dataSnapshot.child("price").getValue().toString());
                    product.setDescription(dataSnapshot.child("description").getValue().toString());
                    product.setImages1(dataSnapshot.child("images1").getValue().toString());
                    product.setImages2(dataSnapshot.child("images2").getValue().toString());
                    product.setImages3(dataSnapshot.child("images3").getValue().toString());
                    product.setImages4(dataSnapshot.child("images4").getValue().toString());
                    product_list.add(product);
                }
                home_recycler_1_adapter = new Home_recycler_1_adapter(product_list, getContext());
                recyclerView.setAdapter(home_recycler_1_adapter);
                home_recycler_1_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        return view;
    }


}