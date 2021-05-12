package com.example.huwamaruwa.Delivery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huwamaruwa.Models.RequestRentModel;
import com.example.huwamaruwa.Models.User;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.customer_care.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeliveryList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliveryList extends Fragment {

    private RecyclerView recyclerView;
    private RecAdapter recAdapter;
    private List<RequestRentModel> recs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivery_list, container, false);

        recyclerView = view.findViewById(R.id.deliveryRecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recs = new ArrayList<>();

        readList();
        return view;
    }

    private void readList() {

        final FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
        String cUser = User.getUid();

        DatabaseReference dbf = FirebaseDatabase.getInstance().getReference().child("RequestRent");
        dbf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                recs.clear();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    RequestRentModel recRent = snap.getValue(RequestRentModel.class);

                    //Test for null objects
                    assert recRent != null;
                    assert cUser != null;

                    Log.d("User", recRent.getUserId());

                    //if(!recRent.getUserId().equals(cUser)){

                    if (recRent.getStatus().equals("Approved")) {
                        recs.add(recRent);
                    }
                }

                recAdapter = new RecAdapter(getContext(), recs);
                recyclerView.setAdapter(recAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}