package com.example.huwamaruwa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.huwamaruwa.Home.Customer_care_fragment;
import com.example.huwamaruwa.Home.Home_fragment;
import com.example.huwamaruwa.Models.User;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.addProduct.addProduct;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //define variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    StorageReference storageReference;
    FloatingActionButton floatingActionButton;

    FirebaseUser currentUser;
    DatabaseReference reference;

    TextView loginName, loginSellerType;
    String userId, name, userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get values by id
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);



        floatingActionButton = findViewById(R.id.floating_add_product);
        //set app name to toolbar
       // setSupportActionBar(toolbar);

        //set toggle event
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //set navigation view clickable
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        loginName = (TextView) headerView.findViewById(R.id.LoginName);
        loginSellerType = (TextView) headerView.findViewById(R.id.LoginSellerType);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.e("login",user.getUid());
        userId = user.getUid();

        Toast.makeText(getApplicationContext(), userId, Toast.LENGTH_LONG).show();

        reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Users").orderByChild("userId").equalTo(userId).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    name = dataSnapshot.child("name").getValue().toString();
                    userType = dataSnapshot.child("userType").getValue().toString();

                }
                loginName.setText(name);
                loginSellerType.setText(userType);

                    Toast.makeText(getApplicationContext(), "Set values", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //set when click back then close the nav drawer


    @Override
    protected void onResume() {
        super.onResume();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),addProduct.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

//            Toast.makeText(getApplicationContext(), userId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.nav_home:
                fragment = new Home_fragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentDefault,fragment);
                fragmentTransaction.commit();
                break;
            case R.id.nav_customer_care:
                fragment = new Customer_care_fragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentDefault,fragment);
                fragmentTransaction.commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void LogoutMethod(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, Login.class));
    }
}