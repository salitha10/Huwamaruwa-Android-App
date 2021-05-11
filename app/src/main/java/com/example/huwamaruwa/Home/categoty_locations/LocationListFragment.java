package com.example.huwamaruwa.Home.categoty_locations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huwamaruwa.Models.Categories;
import com.example.huwamaruwa.Models.LocationModel;
import com.example.huwamaruwa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LocationListFragment extends Fragment {

    private ArrayList<LocationModel> loc_list;
    RecyclerView recyclerView;
    DatabaseReference dbRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_category_list, container, false);
        loc_list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.category_list_recycler);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Locations");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        LocationModel location = new LocationModel();
                        location.setLocation(dataSnapshot.child("location").getValue().toString());
                        location.setId(dataSnapshot.child("id").getValue().toString());
                        loc_list.add(location);
                    }
                    LocationListAdapter adapter = new LocationListAdapter(loc_list,getContext());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}