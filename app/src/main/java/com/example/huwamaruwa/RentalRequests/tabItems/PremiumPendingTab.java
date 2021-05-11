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

import com.airbnb.lottie.LottieAnimationView;
import com.example.huwamaruwa.Models.RequestRentModel;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.RentalRequests.tabbedrecyclerAdapters.PendingRequestAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PremiumPendingTab extends Fragment {
    RecyclerView pendingRecycler;
    DatabaseReference dbRef;
    ArrayList<RequestRentModel> request_list;
    TextView noData;
    LottieAnimationView searchLottie,emptyLottie;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.tab_pending,container,false);

        pendingRecycler = view.findViewById(R.id.RentalReq_seller_side_pending_recycler);

        searchLottie = view.findViewById(R.id.pending_tab_search_lottie);
        searchLottie.setVisibility(View.GONE);

        emptyLottie = view.findViewById(R.id.pending_tab_empty_lottie);
        emptyLottie.setVisibility(View.GONE);

        request_list = new ArrayList<>();
        dbRef = FirebaseDatabase.getInstance().getReference();
        Query query = dbRef.child("RequestRent").orderByChild("isPremium").equalTo("true");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                       if (dataSnapshot.child("status").getValue().toString().equals("Pending")){
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
                }else {
                    request_list = null;
                }

                PendingRequestAdapter pendingRequestAdapter = new PendingRequestAdapter(request_list,getContext());
                pendingRecycler.setAdapter(pendingRequestAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        if (request_list == null ) ;
        else pendingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}
