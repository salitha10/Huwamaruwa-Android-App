package com.example.huwamaruwa.Models;

import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserBehaviours {
    private DatabaseReference dbRef;
    private ArrayList<Product>products;
    private String userId;

    public UserBehaviours(String userId) {
        products = new ArrayList<>();
        dbRef = FirebaseDatabase.getInstance().getReference().child("UserBehaviours");
        this.userId = userId;
    }

    public void addBehaviour(Product product){
        int count = 0;
        for (Product product1:
                products) {
            if(product1.getId().equals(product.getId())){
                count++;
            }
        }
        if (count == 0) products.add(product);
    }

    public void uploadData(){
        dbRef.child(userId).setValue(products);
    }

    public void setBehaveArray(ArrayList<Product>list){
        for (int i = 0 ; i < list.size() ; i++) {
            this.products.add(list.get(i));
        }
    }
    public ArrayList<Product> getProducts() {
        return products;
    }

}