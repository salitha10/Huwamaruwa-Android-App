package com.example.huwamaruwa.buyerRentalRequestManage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.ProfileView;
import com.example.huwamaruwa.R;

public class ProductImageFullScreen extends AppCompatActivity {

    ImageView fullImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_image_full_screen);

        fullImage = findViewById(R.id.FullScreenImage);

        Intent i = getIntent();
        String msg = i.getStringExtra(SentRentalRequestBySellerAdapter.EXTRA_MESSAGE2);
        Glide.with(this).load(msg).into(fullImage);
    }
}