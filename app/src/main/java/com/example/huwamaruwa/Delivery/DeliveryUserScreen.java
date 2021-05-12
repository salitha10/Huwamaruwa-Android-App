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

public class DeliveryUserScreen extends AppCompatActivity {

    MapView map;
    TextView bAddress, sAddress, txtStatus;
    String date;
    SeekBar sb;
    DatabaseReference dbf1, dbf2, dbf3;

    //String rentID = "-M_2HpdA_lCTNFKnS9FK";
    //String sellerID = "3lrP6PcxDRgYUZtdqhuHE6nDwJC2";

    String sellerID = getIntent().getStringExtra("sellerID");
    String rentID = getIntent().getStringExtra("rentID");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_user_screen);
        sb = (SeekBar)findViewById(R.id.seekBar);
        sb.setMax(3);
        sb.setProgress(0);
        sb.setEnabled(false);
        bAddress = (TextView)findViewById(R.id.txtBAddress);
        sAddress = (TextView)findViewById(R.id.txtSAddress);
        txtStatus = (TextView)findViewById(R.id.status);
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
                        sb.setEnabled(false);
                        txtStatus.setText("PickedUp");

                    }
                    else if(status.equals("In Transit")){
                        sb.setEnabled(true);
                        sb.setProgress(2);
                        sb.setEnabled(false);
                        txtStatus.setText("In Transit");
                    }
                    else if(status.equals("Delivered")){
                        sb.setEnabled(true);
                        sb.setProgress(3);
                        sb.setEnabled(false);
                        txtStatus.setText("Delivered");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}