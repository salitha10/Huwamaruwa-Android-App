package com.example.huwamaruwa.Delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.huwamaruwa.R;
import com.google.android.gms.maps.MapView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

public class AdminDeliveryScreen extends AppCompatActivity {

    MapView map;
    Button pickedUp, inTransit, delivered;
    TextView bAddress, sAddress;
    String date;
    SeekBar sb;
    DatabaseReference dbf1, dbf2, dbf3;

    String rentID = getIntent().getStringExtra("recID");
    String sellerID = getIntent().getStringExtra("sellerID");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delivery_screen);

        pickedUp = (Button)findViewById(R.id.btnPickedUp);
        inTransit = (Button)findViewById(R.id.btnIntransit);
        delivered = (Button)findViewById(R.id.btnDelivered);
        sb = (SeekBar)findViewById(R.id.seekBar);
        sb.setMax(3);
        sb.setProgress(0);
        sb.setEnabled(false);
        bAddress = (TextView)findViewById(R.id.txtBAddress);
        sAddress = (TextView)findViewById(R.id.txtSAddress);
        dbf1 = FirebaseDatabase.getInstance().getReference().child("Users");
        dbf2 = FirebaseDatabase.getInstance().getReference().child("RequestRent");
        dbf3 = FirebaseDatabase.getInstance().getReference().child("Delivery");
    }

    public void onResume() {
        super.onResume();

        dbf2.child(rentID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bAddress.setText(snapshot.child("address").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        dbf1.child(sellerID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sAddress.setText(snapshot.child("address").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        dbf1.child(rentID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    String status = snapshot.child("status").getValue().toString();

                    if(status.equals("Picked Up")){
                        sb.setEnabled(true);
                        sb.setProgress(1);
                        pickedUp.setEnabled(false);
                        sb.setEnabled(false);
                    }
                    else if(status.equals("In Transit")){
                        sb.setEnabled(true);
                        sb.setProgress(2);
                        pickedUp.setEnabled(false);
                        inTransit.setEnabled(false);
                        sb.setEnabled(false);
                    }
                    else if(status.equals("Delivered")){
                        sb.setEnabled(true);
                        sb.setProgress(3);
                        pickedUp.setEnabled(false);
                        inTransit.setEnabled(false);
                        delivered.setEnabled(false);
                        sb.setEnabled(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //Pickup button press
    public void pickedUP(View view) {
        sb.setEnabled(true);
        sb.setProgress(1);
        pickedUp.setEnabled(false);
        sb.setEnabled(false);
        dbf2.child(rentID).child("status").setValue("Picked Up");

    }

    //InTransit button press
    public void inTransit(View view){
        sb.setEnabled(true);
        sb.setProgress(2);
        pickedUp.setEnabled(false);
        inTransit.setEnabled(false);
        sb.setEnabled(false);
        dbf2.child(rentID).child("status").setValue("In Transit");


    }

    //Delivered button pressed
    public void delivered(View view){
        sb.setEnabled(true);
        sb.setProgress(3);
        pickedUp.setEnabled(false);
        inTransit.setEnabled(false);
        delivered.setEnabled(false);
        sb.setEnabled(false);
        dbf2.child(rentID).child("status").setValue("Delivered");
    }

}