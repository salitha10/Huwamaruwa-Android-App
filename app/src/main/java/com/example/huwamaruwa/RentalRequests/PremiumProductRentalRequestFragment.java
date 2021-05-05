package com.example.huwamaruwa.RentalRequests;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.Models.RequestRentModel;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.singleProduct.RequestRent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class PremiumProductRentalRequestFragment extends Fragment {

    private ArrayList<RequestRentModel>request_list;
     Product products_list[];
    private DatabaseReference dbRef;
    private RequestRentModel requestRent;
    RecyclerView recyclerView;
    Product product1;
    private MyPremiumProductRentalRequestRecyclerViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.premium_product_rental_request_fragment_layout, container, false);

            dbRef = FirebaseDatabase.getInstance().getReference();
            dbRef.keepSynced(true);
            request_list = new ArrayList<>();
            recyclerView = view.findViewById(R.id.admin_RentalRequsers_recycler);
        products_list = new Product[2];
        Query query1 = dbRef.child("RequestRent").orderByChild("status").equalTo("Pending");

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        requestRent = new RequestRentModel();
                        requestRent.setAddress(dataSnapshot.child("address").getValue().toString());
                        requestRent.setContactNumber(dataSnapshot.child("contactNumber").getValue().toString());
                        requestRent.setDuration(dataSnapshot.child("duration").getValue().toString());
                        requestRent.setInitialDeposit(Double.parseDouble(dataSnapshot.child("initialDeposit").getValue().toString()));
                        requestRent.setTotal(Double.parseDouble(dataSnapshot.child("total").getValue().toString()));
                        requestRent.setIsPremium(dataSnapshot.child("isPremium").getValue().toString());
                        requestRent.setProductId(dataSnapshot.child("productId").getValue().toString());
                        requestRent.setDateDif(dataSnapshot.child("dateDif").getValue().toString());
                        requestRent.setStatus(dataSnapshot.child("status").getValue().toString());
                        requestRent.setId(dataSnapshot.child("id").getValue().toString());
                        request_list.add(requestRent);
                    }
                    adapter = new MyPremiumProductRentalRequestRecyclerViewAdapter(request_list,getContext());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        // Set the adapter
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}