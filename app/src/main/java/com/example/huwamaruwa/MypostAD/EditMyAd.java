package com.example.huwamaruwa.MypostAD;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.Progress.LoadingProgress;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.addCategory.EditCategoryData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class EditMyAd extends AppCompatActivity {


    EditText ProductName1edt,rentFeeedt,contactedt,descriptionedt, depositedt,maxDateedt,minDateedt;
    Button btnPostedt,btnGalleryedt;
    AutoCompleteTextView autoCompleteText1edt, autoCompleteText2;



    Product product;
    DatabaseReference dbref;
    StorageReference storageReference;
    ArrayList<Uri> img_list;
    LoadingProgress loadingProgress;
    String imgData[];
    RadioButton perDay_radioedt,RentperHour_radioedt;
    RadioGroup radioGroupedt;
    String Id;
    Date date;
    int date_in_sec;
    int date_in_min;
    int date_in_hour;
    int date_in_day;
    int date_in_year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_ad);

        ProductName1edt=findViewById(R.id.ProductName1edt);
        rentFeeedt=findViewById(R.id.rentFeeedt);
        contactedt=findViewById(R.id.contactedt);
        descriptionedt=findViewById(R.id.descriptionedt);
        depositedt=findViewById(R.id.depositedt);
        maxDateedt=findViewById(R.id.maxDateedt);
        minDateedt=findViewById(R.id.minDateedt);
        radioGroupedt=findViewById(R.id.radioGroupedt);

        img_list = new ArrayList<>();
        imgData = new String[4];


        autoCompleteText1edt=findViewById(R.id.autoCompleteText1edt);
        autoCompleteText2=findViewById(R.id. autoCompleteText2);




        btnPostedt=findViewById(R.id.btnPostedt);
        btnGalleryedt=findViewById(R.id.btnGalleryedt);

        perDay_radioedt=findViewById(R.id.perDay_radio);
        RentperHour_radioedt=findViewById(R.id.RentperHour_radio);

        Intent intent = getIntent();
        Id = intent.getStringExtra("id");
//        date_in_sec= Integer.parseInt(intent.getStringExtra("date_in_sec"));
//        date_in_min= Integer.parseInt(intent.getStringExtra("date_in_min"));
//        date_in_hour= Integer.parseInt(intent.getStringExtra("date_in_hour"));
//        date_in_day= Integer.parseInt(intent.getStringExtra("date_in_day"));
//        date_in_year= Integer.parseInt(intent.getStringExtra("date_in_year"));






        dbref = FirebaseDatabase.getInstance().getReference().child("Product").child(Id);
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    product = new Product();



                    SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("dd");
                    //Setting the time zone
                    dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                    product.setDate_in_day(Integer.parseInt(dateTimeInGMT.format(new Date())));

                    dateTimeInGMT = new SimpleDateFormat("HH");

                    product.setDate_in_hour(Integer.parseInt(dateTimeInGMT.format(new Date())));
                    dateTimeInGMT = new SimpleDateFormat("mm");
                    product.setDate_in_min(Integer.parseInt(dateTimeInGMT.format(new Date())));
                    dateTimeInGMT = new SimpleDateFormat("ss");
                    product.setDate_in_sec(Integer.parseInt(dateTimeInGMT.format(new Date())));
                    dateTimeInGMT = new SimpleDateFormat("yyy");
                    product.setDate_in_year(Integer.parseInt(dateTimeInGMT.format(new Date())));

                    ProductName1edt.setText(snapshot.child("title").getValue().toString());
                    perDay_radioedt.setText(snapshot.child("perHour").getValue().toString());
                    RentperHour_radioedt.setText(snapshot.child("perHour").getValue().toString());
                    rentFeeedt.setText(snapshot.child("price").getValue().toString());
                    contactedt.setText(snapshot.child("contactNumber").getValue().toString());
                    depositedt.setText(snapshot.child("depositPercentage").getValue().toString());
                    descriptionedt.setText(snapshot.child("description").getValue().toString());
                    maxDateedt.setText(snapshot.child("maxRentalTime").getValue().toString());
                    minDateedt.setText(snapshot.child("minRentalTime").getValue().toString());
                    autoCompleteText1edt.setText(snapshot.child("categoryID").getValue().toString());
                    autoCompleteText2.setText(snapshot.child("location").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnPostedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Id = intent.getStringExtra("id");


                dbref = FirebaseDatabase.getInstance().getReference().child("Product");
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()){
                            Product prod = new Product();
                            prod.setId(Id);
                            prod.setDate_in_year(product.getDate_in_year());
                            prod.setDate_in_day(product.getDate_in_day());
                            prod.setDate_in_hour(product.getDate_in_hour());
                            prod.setDate_in_min(product.getDate_in_min());
                            prod.setDate_in_sec(product.getDate_in_sec());

                            prod.setTitle(ProductName1edt.getText().toString().trim());
                            prod.setPerHour(Boolean.parseBoolean(perDay_radioedt.getText().toString().trim()));
                            prod.setPerHour(Boolean.parseBoolean(RentperHour_radioedt.getText().toString().trim()));
                            prod.setPrice(Double.parseDouble(rentFeeedt.getText().toString().trim()));
                            prod.setContactNumber( contactedt.getText().toString().trim());
                            prod.setDepositPercentage(Double.parseDouble(depositedt.getText().toString().trim()));
                            prod.setDescription(descriptionedt.getText().toString().trim());
                            prod.setMaxRentalTime(Integer.parseInt(maxDateedt.getText().toString().trim())) ;
                            prod.setMinRentalTime(Integer.parseInt(minDateedt.getText().toString().trim()));


                            dbref.child(Id).setValue(prod);
                            Toast.makeText(EditMyAd.this, "Update Successfully", Toast.LENGTH_SHORT).show();


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });












    }
}