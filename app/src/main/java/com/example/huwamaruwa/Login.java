package com.example.huwamaruwa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.Models.UserBehaviours;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    TextInputLayout email, password;
    Button loginButton ,forgotPassword;
    public static UserBehaviours userBehaviours;
    FirebaseAuth firebaseAuth;
    DatabaseReference dbRef;
    ArrayList<Product>product_list_history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.userEmail);
        password = findViewById(R.id.userPassword);
        loginButton = findViewById(R.id.loginBtn);
        forgotPassword = findViewById(R.id.btnForgotPassword);

        firebaseAuth = FirebaseAuth.getInstance();

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog  = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Forgot Password?");
                passwordResetDialog.setMessage("Enter your Email to reset the password");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail = resetMail.getText().toString();
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this,"Reset Email Link Sent",Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this,"Error: "+ e.getMessage(),Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                });
                passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //redirect to login page
                    }
                });
                passwordResetDialog.create().show();
            }
        });
    }
    public void signUp(View view){
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }

    public void login(View view){

        boolean check = true;

        try {

            if (TextUtils.isEmpty(email.getEditText().getText().toString())) {
                check = false;
                Toast.makeText(getApplicationContext(), "Email Text Field is empty", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(password.getEditText().getText().toString())) {
                check = false;
                Toast.makeText(getApplicationContext(), "Password Text Field is empty", Toast.LENGTH_SHORT).show();
            }

            else{



                if (check == true) {

                    // firebase authentication implementation
                    firebaseAuth.signInWithEmailAndPassword(email.getEditText().getText().toString(),
                            password.getEditText().getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser user = firebaseAuth.getCurrentUser();

                                        if (checkAvailable(user.getUid())){
                                            product_list_history = new ArrayList<>();
                                            dbRef = FirebaseDatabase.getInstance().getReference().child("UserBehaviours").child(user.getUid());
                                            Query userBehaveQuery = dbRef.orderByKey();
                                            userBehaveQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                                        Product product = new Product();
                                                        product.setTitle(dataSnapshot.child("title").getValue().toString());
                                                        product.setPrice(Double.parseDouble(dataSnapshot.child("price").getValue().toString()));
                                                        product.setDescription(dataSnapshot.child("description").getValue().toString());
                                                        product.setImages1(dataSnapshot.child("images1").getValue().toString());
                                                        product.setImages2(dataSnapshot.child("images2").getValue().toString());
                                                        product.setImages3(dataSnapshot.child("images3").getValue().toString());
                                                        product.setImages4(dataSnapshot.child("images4").getValue().toString());
                                                        product.setIsPremium(Boolean.parseBoolean(dataSnapshot.child("isPremium").getValue().toString()));
                                                        product.setMinRentalTime(Integer.parseInt(dataSnapshot.child("minRentalTime").getValue().toString()));
                                                        product.setMaxRentalTime(Integer.parseInt(dataSnapshot.child("maxRentalTime").getValue().toString()));
                                                        product.setId(dataSnapshot.child("id").getValue().toString());
                                                        product.setContactNumber(dataSnapshot.child("contactNumber").getValue().toString());
                                                        product.setDate_in_day(Integer.parseInt(dataSnapshot.child("date_in_day").getValue().toString()));
                                                        product.setDate_in_hour(Integer.parseInt(dataSnapshot.child("date_in_hour").getValue().toString()));
                                                        product.setDate_in_min(Integer.parseInt(dataSnapshot.child("date_in_min").getValue().toString()));
                                                        product.setDate_in_sec(Integer.parseInt(dataSnapshot.child("date_in_sec").getValue().toString()));
                                                        product.setDate_in_year(Integer.parseInt(dataSnapshot.child("date_in_year").getValue().toString()));
                                                        product.setPerHour(Boolean.parseBoolean(dataSnapshot.child("perHour").getValue().toString()));
                                                        product.setDepositPercentage(Double.parseDouble(dataSnapshot.child("depositPercentage").getValue().toString()));
                                                        product.setLocation(dataSnapshot.child("location").getValue().toString());
                                                        product.setCategoryID(dataSnapshot.child("categoryID").getValue().toString());
                                                        product.setSellerId(dataSnapshot.child("sellerId").getValue().toString());
                                                        product_list_history.add(product);
                                                    }
                                                    userBehaviours = new UserBehaviours(user.getUid());
                                                    userBehaviours.setBehaveArray(product_list_history);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });


                                        }else {
                                            userBehaviours = new UserBehaviours(user.getUid());
                                        }

                                        startActivity(new Intent(Login.this, MainActivity.class));

                                    }else{
                                        Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                }






            }
        }catch (Exception e){
        }
    }
    public boolean checkAvailable(String id){
        final boolean[] count = {false};
        dbRef = FirebaseDatabase.getInstance().getReference().child("UserBehaviours");
        Query query = dbRef;

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if(dataSnapshot.getKey().equals(id)) count[0] = true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return count[0];

    }
}