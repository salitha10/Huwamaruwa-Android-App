package com.example.huwamaruwa.singleProduct;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.Home.Home_recycler_1_adapter;
import com.example.huwamaruwa.MainActivity;
import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.PremiumStore.PremiumStore;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.RentalHistory.RentalHistory;
import com.example.huwamaruwa.RentalRequests.tabItems.PagerAdapter;
import com.example.huwamaruwa.singleProduct.specification_and_feedback.FeedbackTab_fragment;
import com.example.huwamaruwa.singleProduct.specification_and_feedback.SpecificationTab_fragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PremiumProduct extends AppCompatActivity {
    //declare TAG
    public static final String REQUEST_RENT_TAG = "com.example.huwamaruwa.requestRent";
    public static final String TAG_PRODUCT_ID = "com.example.huwamaruwa.requestRent.product.id";
    public static final String TAG_SELLER_ID = "com.example.huwamaruwa.requestRent.product.sellerId";

    //declare Variables
    private ImageView img1,img2,img3,img4,imgMain;
    private TextView txtTitle,txtPrice,txtTime,txtMaxRent,txtMinRent,sellerName;
    private Button btnRentProduct;
    private Product product;
    private Product prevProduct;
    private LinearLayout premiumDescription,premiumStores,premiumLogo;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FeedbackTab_fragment feedbackTab_fragment;
    private SpecificationTab_fragment specificationTab_fragment;
    private DatabaseReference dbRef;
    private DatabaseReference ratingDb;
    private String sName;
    private CardView recentRentals;
    private CardView singleStore;
    private Toolbar toolbar;
    private RatingBar ratingBar;
    public static final String RS="RS. ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_product);

        //make loading animation



        //get View by Id
        img1 = findViewById(R.id.imgPremiumProduct_1);
        img2 = findViewById(R.id.imgPremiumProduct_2);
        img3 = findViewById(R.id.imgPremiumProduct_3);
        img4 = findViewById(R.id.imgPremiumProduct_4);
        imgMain = findViewById(R.id.imgPremiumProduct_main);
        toolbar = findViewById(R.id.toolbar);
        txtTitle = findViewById(R.id.txtPremiumProduct_title);
        txtPrice = findViewById(R.id.txtPremiumProduct_price);
        txtTime = findViewById(R.id.txtPremiumProduct_time);
        txtMaxRent = findViewById(R.id.txtPremiumProduct_MaxRentTime);
        txtMinRent =findViewById(R.id.txtPremiumProduct_MinRentTime);
        sellerName = findViewById(R.id.txtPremiumSeller_Id);

        premiumDescription = findViewById(R.id.premiumProduct_description);
        premiumStores = findViewById(R.id.premium_product_store_and_recent);
        premiumLogo = findViewById(R.id.premium_product_logo_icons);

        btnRentProduct = findViewById(R.id.btnRequestRent_form_send_request);

        //rating bar
        ratingBar = findViewById(R.id.premium_product_rating_bar);

        viewPager = findViewById(R.id.premiumProduct_viewpager);
        tabLayout = findViewById(R.id.premiumProduct_tab_view);

        recentRentals = findViewById(R.id.crdView_premium_recentRental);
        singleStore = findViewById(R.id.crdView_premium_single_store);

        feedbackTab_fragment = new FeedbackTab_fragment();
        specificationTab_fragment = new SpecificationTab_fragment();

        tabLayout.setupWithViewPager(viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),0);
        pagerAdapter.addFragment(specificationTab_fragment,"Specification");
        pagerAdapter.addFragment(feedbackTab_fragment,"FeedBack");
        viewPager.setAdapter(pagerAdapter);

        //set Toolbar title
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Get parcelable object via Intent
         product = getIntent().getParcelableExtra(Home_recycler_1_adapter.SINGLE_PRODUCT_TAG);

        getSupportActionBar().setTitle(product.getTitle());

        if (!product.getIsPremium()){ //restrict access for Non Premium Products
            premiumDescription.setVisibility(View.GONE);
            premiumStores.setVisibility(View.GONE);
            premiumLogo.setVisibility(View.GONE);
        }


        //get user ratings

        ratingDb = FirebaseDatabase.getInstance().getReference().child("ProductReviews");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final int[] numRating = {0};
                final int[] numCus = {0};
                Query q = ratingDb.orderByChild("productID").equalTo(product.getId());
                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChildren()){
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                numRating[0] += Integer.parseInt(dataSnapshot.child("averageRating").getValue().toString());
                                numCus[0]++;
                            }
                        }
                        ratingBar.setNumStars(5);
                        double avgRate =calcAvgRating(numRating[0],numCus[0]);
                        ratingBar.setRating((float) avgRate * 5);
                        Toast.makeText(PremiumProduct.this, "Number of avg "+avgRate, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        },1000);




        //setup images
        Glide.with(this).load(product.getImages1()).placeholder(R.drawable.image_loading_anim).error(R.drawable.image_error).into(img1);
        Glide.with(this).load(product.getImages2()).placeholder(R.drawable.image_loading_anim).error(R.drawable.image_error).into(img2);
        Glide.with(this).load(product.getImages3()).placeholder(R.drawable.image_loading_anim).error(R.drawable.image_error).into(img3);
        Glide.with(this).load(product.getImages4()).placeholder(R.drawable.image_loading_anim).error(R.drawable.image_error).into(img4);
        Glide.with(this).load(product.getImages1()).placeholder(R.drawable.image_loading_anim).error(R.drawable.image_error).into(imgMain);

        txtTitle.setText(product.getTitle());
        txtPrice.setText(product.isPerHour()?String.valueOf(product.getPrice()).concat(" /Per Hour"):String.valueOf(product.getPrice()).concat(" /Per Day"));
        txtMaxRent.setText(String.valueOf(product.getMaxRentalTime()));
        txtMinRent.setText(String.valueOf(product.getMinRentalTime()));

        //getting seller details accorting to seller id

       if (!product.getIsPremium()){//if product is not premium,display user details
           dbRef = FirebaseDatabase.getInstance().getReference().child("Users");
           Query query = dbRef.orderByChild("userId").equalTo(product.getSellerId()).limitToFirst(1);

           query.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {

                   if (snapshot.hasChildren()){
                       for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                           sName = dataSnapshot.child("name").getValue().toString();
                       }
                   }
                   sellerName.setText(sName);
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });
       }else sellerName.setVisibility(View.GONE);




        SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("dd");
        //Setting the time zone
        dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        int day = Integer.parseInt(dateTimeInGMT.format(new Date()));
        dateTimeInGMT = new SimpleDateFormat("HH");
        int hour =Integer.parseInt(dateTimeInGMT.format(new Date()));
        dateTimeInGMT = new SimpleDateFormat("mm");
        int min =Integer.parseInt(dateTimeInGMT.format(new Date()));
        dateTimeInGMT = new SimpleDateFormat("ss");
        int sec =Integer.parseInt(dateTimeInGMT.format(new Date()));
        dateTimeInGMT = new SimpleDateFormat("yyy");
        int year =Integer.parseInt(dateTimeInGMT.format(new Date()));

        if ((year - product.getDate_in_year())>0){
            txtTime.setText("Posted On "+(year - product.getDate_in_year())+" years ago");
        }else if ((day - product.getDate_in_day()) > 0){
            txtTime.setText("Posted On "+(day - product.getDate_in_day())+" days ago");
        }else if ((hour - product.getDate_in_hour()) > 0){
            txtTime.setText("Posted On "+(hour - product.getDate_in_hour())+" hours ago");
        }else if ((min - product.getDate_in_min()) > 0){
            txtTime.setText("Posted On "+(min - product.getDate_in_min())+" minutes ago");
        }else if ((sec - product.getDate_in_sec()) > 0){
            txtTime.setText("Posted On "+(sec - product.getDate_in_sec())+" seconds ago");
        }


    }



    @Override
    protected void onResume() {
        super.onResume();
        btnRentProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RequestRent.class);
                intent.putExtra(REQUEST_RENT_TAG,product);
                startActivity(intent);
            }
        });


        recentRentals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PremiumProduct.this, "Clicked History", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), RentalHistory.class);
                intent.putExtra(TAG_PRODUCT_ID,product.getId());
                startActivity(intent);
            }
        });

        singleStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PremiumStore.class);
                intent.putExtra(TAG_SELLER_ID,product.getSellerId());
                startActivity(intent);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Glide.with(getApplicationContext()).load(product.getImages2()).into(imgMain);
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Glide.with(getApplicationContext()).load(product.getImages1()).into(imgMain);
            }
        });


        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Glide.with(getApplicationContext()).load(product.getImages3()).into(imgMain);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Glide.with(getApplicationContext()).load(product.getImages4()).into(imgMain);
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public double calcAvgRating(int allRatedStars,int noOfUsers){
        return allRatedStars/((double)noOfUsers*5);
    }
}