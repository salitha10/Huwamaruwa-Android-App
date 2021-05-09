package com.example.huwamaruwa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ProfPicFullScreen extends AppCompatActivity {

    ImageView fullImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_pic_full_screen);

        fullImage = findViewById(R.id.FullScreenImage);

        Intent i = getIntent();
        String msg = i.getStringExtra(ProfileView.EXTRA_MESSAGE2);
        Glide.with(this).load(msg).into(fullImage);
    }
}