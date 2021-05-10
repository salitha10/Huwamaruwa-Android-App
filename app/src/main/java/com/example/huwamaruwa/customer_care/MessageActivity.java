package com.example.huwamaruwa.customer_care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.Models.User;
import com.example.huwamaruwa.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MessageActivity extends AppCompatActivity {

    ImageView pic;
    TextView name;

    ImageButton send, camera;
    EditText message;


    FirebaseUser fb;
    DatabaseReference dbf;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //Init
        pic = findViewById(R.id.profile_image);
        name = findViewById(R.id.userName);
        message = findViewById(R.id.chat_message);
        send = findViewById(R.id.send_button);
        camera = findViewById(R.id.camera_button);
        intent = getIntent();

        //final FirebaseUser cUser = FirebaseAuth.getInstance().getCurrentUser();
        String cUser = "3lrP6PcxDRgYUZtdqhuHE6nDwJC2";
        String uID = intent.getStringExtra("userId");
        Log.d("new UID", uID);

        //Set onclick listener, Send message
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = message.getText().toString().trim();
                if(!msg.equals("")){
                    //Get user ID Send message
                    sendMessage(cUser, uID, msg);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Message is empty", Toast.LENGTH_SHORT);
                }

                message.setText("");
            }
        });

        DatabaseReference dbf = FirebaseDatabase.getInstance().getReference().child("Users").child(uID);
        dbf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                name.setText(user.getName());

                if (user.getUserImage().equals("default")) {
                    //Use circular images
                    /**
                     *
                     */
                } else {
                    Glide.with(getApplicationContext()).load(user.getUserImage()).circleCrop().into(pic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //send message
    private void sendMessage(String sender, String receiver, String message ){
        DatabaseReference dbf = FirebaseDatabase.getInstance().getReference();

        HashMap <String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        dbf.child("Chats").push().setValue(hashMap);


    }
}