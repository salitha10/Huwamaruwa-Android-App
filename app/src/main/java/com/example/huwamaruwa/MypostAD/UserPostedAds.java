package com.example.huwamaruwa.MypostAD;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserPostedAds extends AppCompatActivity {
    RecyclerView recyclerView;
    private DatabaseReference database;
    ArrayList<Product> post_list;
    private String sellerId;
    addPostAdapter addPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posted_ads);


//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        sellerId = user.getUid();

        database = FirebaseDatabase.getInstance().getReference();


        recyclerView = findViewById(R.id.recycleView_ad);
        post_list = new ArrayList<>();



        database = FirebaseDatabase.getInstance().getReference().child("Product");
        Query query = database.orderByChild("sellerId").equalTo("H5wttm6bLnTmC0hUTNnD34VsL162");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = new Product();

                    product.setImages1(dataSnapshot.child("images1").getValue().toString());
                    product.setImages2(dataSnapshot.child("images2").getValue().toString());
                    product.setImages3(dataSnapshot.child("images3").getValue().toString());
                    product.setImages4(dataSnapshot.child("images4").getValue().toString());
                    product.setSellerId(dataSnapshot.child("sellerId").getValue().toString());
                    product.setId(dataSnapshot.child("id").getValue().toString());
                    product.setTitle(dataSnapshot.child("title").getValue().toString());
                    product.setPrice(Double.parseDouble(dataSnapshot.child("price").getValue().toString()));

                    post_list.add(product);

                    Toast.makeText(UserPostedAds.this, product.getTitle(), Toast.LENGTH_SHORT).show();
                }
                addPostAdapter addPost = new addPostAdapter(getApplicationContext(), post_list);
                recyclerView.setAdapter(addPost);
                addPost.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
