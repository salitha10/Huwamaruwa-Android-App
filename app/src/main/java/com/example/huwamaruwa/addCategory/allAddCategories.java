package com.example.huwamaruwa.addCategory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huwamaruwa.AddCategory;
import com.example.huwamaruwa.Models.Categories;
import com.example.huwamaruwa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class allAddCategories extends AppCompatActivity {
    private static final String TAG = "categoryActivity";
    RecyclerView recyclerView;
    DatabaseReference database;
    CategoryAdapter catadopter;
    ArrayList<Categories> list;
    Button btnAddNew;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_add_categories);

        recyclerView = findViewById(R.id.category_recycler_view);
        btnAddNew=findViewById(R.id.btnAddNewCat);

        database = FirebaseDatabase.getInstance().getReference();

        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddCategory.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Add button is active", Toast.LENGTH_SHORT).show();

            }
        });

        getData();


    }

    private void getData() {

        Query query = database.child("Category");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Categories categories = new Categories();
                    categories.setCategoryTitle(dataSnapshot.child("categoryTitle").getValue().toString());
                    categories.setCatId(dataSnapshot.child("catId").getValue().toString());

                    list.add(categories);
                    Toast.makeText(getApplicationContext(), categories.getCatId(), Toast.LENGTH_SHORT).show();


                }
                catadopter = new CategoryAdapter(list, getApplicationContext());
                recyclerView.setAdapter(catadopter);
                catadopter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}