package com.example.huwamaruwa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huwamaruwa.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserProfileEdit extends AppCompatActivity {

    EditText name, email, phone, address;
    String userId;
    DatabaseReference dbf;
    FirebaseUser user;
    User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_edit);

        name = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        phone = findViewById(R.id.Profile_phone);
        address = findViewById(R.id.Profile_address);

        user = FirebaseAuth.getInstance().getCurrentUser();
        Log.e("login", user.getUid());
        userId = user.getUid();



        dbf = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);


        dbf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    name.setText(snapshot.child("name").getValue().toString());
                    email.setText(snapshot.child("email").getValue().toString());
                    phone.setText(snapshot.child("phoneNo").getValue().toString());
                    address.setText(snapshot.child("address").getValue().toString());


                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateUserProfile(View view) {
        dbf = FirebaseDatabase.getInstance().getReference().child("Users");

        String addressUpdate = address.getText().toString();
        String nameUpdate = name.getText().toString().trim();
        String phoneUpdate = phone.getText().toString().trim();
        String emailUpdate = email.getText().toString().trim();
        HashMap hashMap = new HashMap();
        hashMap.put("address",addressUpdate);
        hashMap.put("phoneNo",phoneUpdate);
        hashMap.put("email",emailUpdate);
        hashMap.put("name",nameUpdate);



        dbf.child(userId).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                //Updating the user email in the authentication
                user.updateEmail(emailUpdate)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("AuthEmail Updated", "User email address updated.");
                                }
                            }
                        });
                Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_LONG).show();

            }
        });
    }
}