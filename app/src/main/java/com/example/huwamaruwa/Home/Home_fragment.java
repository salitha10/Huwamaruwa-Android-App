package com.example.huwamaruwa.Home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huwamaruwa.Home.GridList.GridList;
import com.example.huwamaruwa.MainActivity;
import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.Progress.LoadingProgress;
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

public class Home_fragment extends Fragment {
    private static final String TAG_LATEST_MORE_TITLE = "com.example.latest_viewMore";

    //declare Private variables

    private RecyclerView recyclerView1,recyclerView2,recyclerView3;
    private ArrayList<Product>product_list_latest;
    private ArrayList<Product>product_list_history;
    private ArrayList<Product>product_list_history_premium;
    private ArrayList<Product>product_list_premium;
    private DatabaseReference dRef_latest;
    private DatabaseReference dRef_history;
    private Home_recycler_1_adapter home_recycler_1_adapter;
    private Home_recycler_3_adapter home_recycler_3_adapter;
    private Home_recycler_2_adapter home_recycler_2_adapter;
    private String userId;
    private Dialog dialog;
    private Button viewMoreLatest,viewMoreHistory;
    private LinearLayout historyLayout;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch onlyPremium;
    private static final String TAG = "home_fragment";
    boolean onlyPre = false;
    //default constructor
    public Home_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        dialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.loading_wait);
        dialog.setCancelable(false);
        //getting current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.e("login",user.getUid());
        userId = user.getUid();

        //set loading animation

        //get views by ids
        //layout
        historyLayout = view.findViewById(R.id.linearLayout2);
        viewMoreHistory = view.findViewById(R.id.btnhistory_view_more);

        //recycler
        recyclerView1 = view.findViewById(R.id.home_recycler_view_1);
        recyclerView2 = view.findViewById(R.id.home_recycler_view_2);
        recyclerView3 = view.findViewById(R.id.home_recycler_view_3);

        //initialize arrays
        product_list_latest = new ArrayList<>();
        product_list_history = new ArrayList<>();
        product_list_history_premium = new ArrayList<>();
        product_list_premium = new ArrayList<>();

        viewMoreLatest = view.findViewById(R.id.btnlatest_view_more);
        //onlyPremium = view.findViewById(R.id.only_premium);


        //Retrieve value for recycler view 1(pro ads)
        dRef_latest = FirebaseDatabase.getInstance().getReference(); //get db reference
        dRef_latest.keepSynced(true);
        Query query = dRef_latest.child("Product");


        dialog.show();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){ //check whether query has data or not


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
                        product_list_latest.add(product); //add data for array list
                        if (product.getIsPremium()){//check whether add is premium or not
                            product_list_premium.add(product);
                        }
                    }
                }else {
                    Log.e(TAG,"home fragment:null Object");
                }

                //create adapter objects
                home_recycler_1_adapter = new Home_recycler_1_adapter(product_list_premium, getContext());
                home_recycler_3_adapter = new Home_recycler_3_adapter(product_list_latest, getContext());


                recyclerView1.setAdapter(home_recycler_1_adapter);

                recyclerView3.setAdapter(home_recycler_3_adapter);
                home_recycler_1_adapter.notifyDataSetChanged();
                home_recycler_3_adapter.notifyDataSetChanged();

                viewMoreLatest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), GridList.class);
                       // intent.putExtra(TAG_LATEST_MORE_TITLE,);
                        getContext().startActivity(intent);
                    }
                });
                viewMoreHistory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), GridList.class);
                        // intent.putExtra(TAG_LATEST_MORE_TITLE,);
                        getContext().startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//onlyPremium.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        if (onlyPremium.isChecked()) onlyPre = true;
//        else onlyPre= false;
//    }
//});

        //retrieve user history in db
        dRef_history = FirebaseDatabase.getInstance().getReference().child("UserBehaviours").child(userId);
        Query userBehaveQuery = dRef_history.orderByKey();
        userBehaveQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
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
                        if (product.getIsPremium()) product_list_history_premium.add(product);
                    }

                }
                if (onlyPre){
                    recyclerView2.setAdapter(home_recycler_1_adapter);
                    if (product_list_history_premium.size() < 3){

                    }
                    else
                    home_recycler_2_adapter = new Home_recycler_2_adapter(product_list_history_premium,getContext());
                }else {
                    if (product_list_history.size() < 4){
                            historyLayout.setVisibility(View.GONE);
                            recyclerView2.setVisibility(View.GONE);
                    }
                    else
                    home_recycler_2_adapter = new Home_recycler_2_adapter(product_list_history,getContext());
                    recyclerView2.setAdapter(home_recycler_2_adapter);
                }

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        dialog.dismiss();
                    }
                }, 1000);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //manage layouts in recycler view
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        if (product_list_history.isEmpty()){

        }
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        return view;
    }


}