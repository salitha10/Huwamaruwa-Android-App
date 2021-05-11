package com.example.huwamaruwa.Home.categoty_locations;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huwamaruwa.Models.Categories;
import com.example.huwamaruwa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryListFragment extends Fragment {
    private ArrayList<Categories>cat_list;
    RecyclerView recyclerView;
    DatabaseReference dbRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        cat_list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.category_list_recycler);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Category");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Categories categories = new Categories();
                        categories.setCategoryTitle(dataSnapshot.child("categoryTitle").getValue().toString());
                        categories.setCatType1(dataSnapshot.child("catType1").getValue().toString());
                        categories.setCatType2(dataSnapshot.child("catType2").getValue().toString());
                        categories.setCatType3(dataSnapshot.child("catType3").getValue().toString());
                        categories.setCatType4(dataSnapshot.child("catType4").getValue().toString());
                        categories.setCatType5(dataSnapshot.child("catType5").getValue().toString());
                        categories.setCatType6(dataSnapshot.child("catType6").getValue().toString());

                        cat_list.add(categories);
                    }
                    CategoryListAdapter adapter = new CategoryListAdapter(cat_list,getContext());
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