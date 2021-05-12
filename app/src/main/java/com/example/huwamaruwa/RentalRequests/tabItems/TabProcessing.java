package com.example.huwamaruwa.RentalRequests.tabItems;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.huwamaruwa.Models.RequestRentModel;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.RentalRequests.tabbedrecyclerAdapters.ProcessingReqAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TabProcessing extends Fragment {
    RecyclerView processingRecycler;
    ArrayList<RequestRentModel> request_list;
    DatabaseReference dbRef;
    TextView noData;
    String userId;
    LottieAnimationView searchLottie,emptyLottie;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.tab_processing,container,false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.e("login",user.getUid());
        userId = user.getUid();


        searchLottie = view.findViewById(R.id.processing_tab_search_lottie);
        emptyLottie = view.findViewById(R.id.processing_tab_empty_lottie);
        searchLottie.setVisibility(View.GONE);
        emptyLottie.setVisibility(View.GONE);


    request_list = new ArrayList<>();
    processingRecycler = view.findViewById(R.id.RentalReq_seller_side_processing_recycler);
                dbRef = FirebaseDatabase.getInstance().getReference();
        Query query = dbRef.child("RequestRent").orderByChild("isPremium").equalTo("false");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                        if (dataSnapshot.child("status").getValue().toString().equals("Approved")&& (dataSnapshot.child("sellerId").getValue().toString().equals(userId))){
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
                            request.setSellerId(dataSnapshot.child("sellerId").getValue().toString());
                            request_list.add(request);
                        }
                    }
                    if (request_list.isEmpty()) emptyLottie.setVisibility(View.VISIBLE);
                }else {
                    request_list = null;
                    searchLottie.setVisibility(View.GONE);
                    emptyLottie.setVisibility(View.VISIBLE);
                }


                ProcessingReqAdapter processingReqAdapter = new ProcessingReqAdapter(request_list,getContext());
                processingRecycler.setAdapter(processingReqAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
         processingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}