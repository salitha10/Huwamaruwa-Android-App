package com.example.huwamaruwa.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huwamaruwa.Models.LocationModel;
import com.example.huwamaruwa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddLocation extends AppCompatActivity {
    Button btnLocation;
    TextView txtLocation;
    DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

       btnLocation = findViewById(R.id.btnLocationAdd);
       txtLocation = findViewById(R.id.txtLocation_name);


    }

    @Override
    protected void onResume() {
        super.onResume();
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Locations");
                try {
                    LocationModel locationModel = new LocationModel();
                    locationModel.setLocation(txtLocation.getText().toString().trim());
                    String id = dbRef.push().getKey();
                    locationModel.setId(id);
                    dbRef.child(id).setValue(locationModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddLocation.this, "Location Added", Toast.LENGTH_SHORT).show();
                        }
                    });

                }catch (Exception e){

                }
            }
        });
    }
}