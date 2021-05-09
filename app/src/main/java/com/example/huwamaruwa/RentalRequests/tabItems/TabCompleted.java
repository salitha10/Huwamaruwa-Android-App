package com.example.huwamaruwa.RentalRequests.tabItems;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huwamaruwa.Models.RequestRentModel;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.RentalRequests.tabbedrecyclerAdapters.CompleatedReqAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TabCompleted extends Fragment {
    DatabaseReference dbRef;
    ArrayList<RequestRentModel> request_list;
    RecyclerView completedRecycler;
    TextView noData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.tab_completed,container,false);

        noData = view.findViewById(R.id.txtCompletedNoData);
        completedRecycler = view.findViewById(R.id.RentalReq_seller_side_completed_recycler);
        request_list  = new ArrayList<>();
        dbRef = FirebaseDatabase.getInstance().getReference();
        Query query = dbRef.child("RequestRent").orderByChild("isPremium").equalTo("false");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                        if (dataSnapshot.child("status").getValue().toString().equals("Delivered")){
                            RequestRentModel request = new RequestRentModel();
                            request.setAddress(dataSnapshot.child("address").getValue().toString());
                            request.setContactNumber(dataSnapshot.child("contactNumber").getValue().toString());
                            request.setDuration(dataSnapshot.child("duration").getValue().toString());
                            request.setInitialDeposit(Double.parseDouble(dataSnapshot.child("initialDeposit").getValue().toString()));
                            request.setTotal(Double.parseDouble(dataSnapshot.child("total").getValue().toString()));
                            request.setIsPremium(dataSnapshot.child("isPremium").getValue().toString());
                            request.setProductId(dataSnapshot.child("productId").getValue().toString());
                            request.setDateDif(dataSnapshot.child("dateDif").getValue().toString());
                            request.setStatus(dataSnapshot.child("status").getValue().toString());
                            request.setId(dataSnapshot.child("id").getValue().toString());
                            request.setUserId(dataSnapshot.child("userId").getValue().toString());
                            request_list.add(request);
                        }


                    }
                }
                else request_list = null;

                CompleatedReqAdapter compleatedReqAdapter = new CompleatedReqAdapter(request_list,getContext());
                if (request_list != null) completedRecycler.setAdapter(compleatedReqAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        completedRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}