package com.example.huwamaruwa.singleProduct;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.Home.Home_recycler_1_adapter;
import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.R;

public class PremiumProduct extends AppCompatActivity {
    public static final String REQUEST_RENT_TAG = "com.example.huwamaruwa.requestRent";
    ImageView img1,img2,img3,img4,imgMain;
    TextView txtTitle,txtPrice,txtTime;
    Button btnRentProduct;
    Product product;
    public static final String RS="RS. ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_product);

        img1 = findViewById(R.id.imgPremiumProduct_1);
        img2 = findViewById(R.id.imgPremiumProduct_2);
        img3 = findViewById(R.id.imgPremiumProduct_3);
        img4 = findViewById(R.id.imgPremiumProduct_4);
        imgMain = findViewById(R.id.imgPremiumProduct_main);

        txtTitle = findViewById(R.id.txtPremiumProduct_title);
        txtPrice = findViewById(R.id.txtPremiumProduct_price);
        txtTime = findViewById(R.id.txtPremiumProduct_time);

        btnRentProduct = findViewById(R.id.btnRequestRent_form_send_request);

        product = getIntent().getParcelableExtra(Home_recycler_1_adapter.SINGLE_PRODUCT_TAG);

        Glide.with(this).load(product.getImages1()).into(img1);
        Glide.with(this).load(product.getImages2()).into(img2);
        Glide.with(this).load(product.getImages3()).into(img3);
        Glide.with(this).load(product.getImages4()).into(img4);
        Glide.with(this).load(product.getImages1()).into(imgMain);

        txtTitle.setText(product.getTitle());
        txtPrice.setText(RS.concat(String.valueOf(product.getPrice()))+"/Per Day");


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
}