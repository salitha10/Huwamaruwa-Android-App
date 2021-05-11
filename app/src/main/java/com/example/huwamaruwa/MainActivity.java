package com.example.huwamaruwa;



import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.Home.AddLocation;
import com.example.huwamaruwa.Home.Customer_care_fragment;
import com.example.huwamaruwa.Home.Home_fragment;


import com.example.huwamaruwa.Home.categoty_locations.CategoryListFragment;
import com.example.huwamaruwa.Home.categoty_locations.LocationListFragment;
import com.example.huwamaruwa.Models.UserBehaviours;

import com.example.huwamaruwa.RentalRequests.PremiumProductRentalRequestFragment;
import com.example.huwamaruwa.RentalRequests.nonPremium_Requests_seller_sideFragment;
import com.example.huwamaruwa.addProduct.AddNewItem;
import com.example.huwamaruwa.buyerRentalRequestManage.AllBuyerRentalRequests;
import com.example.huwamaruwa.buyerRentalRequestManage.BuyerRentalRequest;
import com.example.huwamaruwa.buyerRentalRequestManage.SentRentalRequestBySeller;
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
import com.mikhaellopez.circularimageview.CircularImageView;

import android.widget.Button;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //define variables
    int clicker;
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
    CircularImageView profileIcon;
    String userId, name, userType, userProfIcon;

    FloatingActionButton floatingActionButton_add;
    FloatingActionButton floatingActionButton_req;
    TextView txtFloatingAdd,txtFloatingReq;
    ConstraintLayout floatingSheet;
    Animation rotateOpenAnim;
    Animation rotateCloseAnim;
    Animation fromBottomAnim;
    Animation toBottomAnim;
    Animation bottomSheet;
    Animation topSheet;
    Button btnCategory;
    Button btnLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        //

        //get values by id
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        btnCategory = findViewById(R.id.btnCategory_home);
        btnLocation = findViewById(R.id.btnCancel);

        clicker = 0;
        floatingActionButton = findViewById(R.id.floating_add_product);
        floatingActionButton_add = findViewById(R.id.floating_add_button);
        floatingActionButton_req = findViewById(R.id.floating_buyer_req_button);
        txtFloatingAdd = findViewById(R.id.txtfloating_add);
        txtFloatingReq = findViewById(R.id.txtfloating_req);
        floatingSheet = findViewById(R.id.floating_bottom_sheet);


        //get Current User
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.e("login",user.getUid());
        userId = user.getUid();

        //set toolbr title

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Home");
        //hide data
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //retrieve current user details from database
                reference = FirebaseDatabase.getInstance().getReference();
                Query query = reference.child("Users").orderByChild("userId").equalTo(userId).limitToFirst(1);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            name = dataSnapshot.child("name").getValue().toString();
                            userType = dataSnapshot.child("userType").getValue().toString();
                            userProfIcon = dataSnapshot.child("userImage").getValue().toString();
                            if(userProfIcon != "".trim()){
                                Glide.with(MainActivity.this).load(userProfIcon).into(profileIcon);
                            }
                            else{
                                Glide.with(MainActivity.this).load("https://firebasestorage.googleapis.com/v0/b/huwamaruwa-3e019.appspot.com/o/User%20Profile%20Pictures%2F1620409342626.png?alt=media&token=8798ca6c-5856-46ac-936b-9342eff852a0").into(profileIcon);
                            }


                        }
                        //hide respective menu items
                        Menu menu = navigationView.getMenu();

                        if (userType.equals("Buyer")){
                            menu.findItem(R.id.nav_admin).setVisible(false);
                            menu.findItem(R.id.nav_seller).setVisible(false);
                        }else if (userType.equals("Seller")){
                            menu.findItem(R.id.nav_admin).setVisible(false);
                            menu.findItem(R.id.nav_buyer).setVisible(false);
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
        },1000);



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
        profileIcon = (CircularImageView) headerView.findViewById(R.id.profile_icon);




//        Toast.makeText(getApplicationContext(), userId, Toast.LENGTH_LONG).show();






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
        profileIcon = (CircularImageView) headerView.findViewById(R.id.profile_icon);




//        Toast.makeText(getApplicationContext(), userId, Toast.LENGTH_LONG).show();



    }
    //set when click back then close the nav drawer


    @Override
    protected void onResume() {
        super.onResume();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rotateOpenAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_open_floating_anim);
                rotateCloseAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_closefloating_anim);
                fromBottomAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_bottom_floating_anim);
                toBottomAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.to_bottom_floating_anim);
                bottomSheet = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.floating_bottom_top_animation);
                topSheet = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.floating_bottom_bottom_animation);
                setVisibility(clicker);

               //
            }
        });
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new CategoryListFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentDefault,fragment);
                fragmentTransaction.commit();
            }
        });
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new LocationListFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentDefault,fragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void setVisibility(int cli) {
        if (cli == 0){
            if (!userType.equals("Buyer")){
                if (!userType.equals("Admin")) {
                    floatingActionButton_req.setVisibility(View.GONE);
                    txtFloatingReq.setVisibility(View.GONE);
                }
                floatingActionButton_add.setVisibility(View.VISIBLE);
                floatingActionButton_add.setAnimation(fromBottomAnim);
                txtFloatingAdd.setVisibility(View.VISIBLE);
                txtFloatingAdd.setAnimation(fromBottomAnim);

            }else if (!userType.equals("Seller")){
                if (!userType.equals("Admin")) {
                    floatingActionButton_add.setVisibility(View.GONE);
                    txtFloatingAdd.setVisibility(View.GONE);
                }
                floatingActionButton_req.setVisibility(View.VISIBLE);
                floatingActionButton_req.setAnimation(fromBottomAnim);
                txtFloatingReq.setVisibility(View.VISIBLE);
                txtFloatingReq.setAnimation(fromBottomAnim);

            }
            floatingSheet.setVisibility(View.VISIBLE);
            floatingSheet.setAnimation(bottomSheet);
            floatingActionButton.setAnimation(rotateOpenAnim);
            clicker = 1;

        }else{
            if (!userType.equals("Buyer")){
                txtFloatingAdd.setAnimation(toBottomAnim);
                floatingActionButton_add.setAnimation(toBottomAnim);
                floatingActionButton_add.setVisibility(View.GONE);

            }else if (!userType.equals("Seller")){
                floatingActionButton_req.setVisibility(View.GONE);
                floatingActionButton_req.setAnimation(toBottomAnim);
                txtFloatingReq.setAnimation(toBottomAnim);
            }
            floatingActionButton.setAnimation(rotateCloseAnim);
            floatingSheet.setAnimation(topSheet);
            floatingSheet.setVisibility(View.GONE);
            clicker = 0;
        }
        floatingActionButton_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddNewItem.class);
                startActivity(intent);

            }
        });
        floatingActionButton_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BuyerRentalRequest.class));
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

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
            case R.id.nav_customer_care:

                fragment = new Customer_care_fragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentDefault,fragment);
                fragmentTransaction.commit();
                floatingActionButton.setVisibility(View.INVISIBLE);
                break;
            case R.id.admin_Rental_requests:
                floatingActionButton.setVisibility(View.INVISIBLE);
                fragment = new PremiumProductRentalRequestFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentDefault,fragment);
                fragmentTransaction.commit();
                break;
                case R.id.nav_seller_requests:
                    floatingActionButton.setVisibility(View.INVISIBLE);
                fragment = new nonPremium_Requests_seller_sideFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentDefault,fragment);
                fragmentTransaction.commit();
                break;
            case R.id.admin_add_location:

                floatingActionButton.setVisibility(View.INVISIBLE);
                startActivity(new Intent(MainActivity.this, AddLocation.class));
                break;
            case R.id.nav_seller_product_request:
                startActivity(new Intent(getApplicationContext(), AllBuyerRentalRequests.class));
                break;
            case R.id.nav_seller_sent_product_offers:
                startActivity(new Intent(getApplicationContext(), SentRentalRequestBySeller.class));
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void LogoutMethod(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void ViewUserProfile(View view){
        Intent intent = new Intent(getApplicationContext(), ProfileView.class);
        startActivity(intent);
    }


    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi != null && wifi.isConnected())||(mobile != null && mobile.isConnected())){
            return true;
        }else return false;
    }


}