package com.example.huwamaruwa.addProduct;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import android.widget.Switch;
import android.widget.Toast;

import com.example.huwamaruwa.MainActivity;
import com.example.huwamaruwa.Models.Product;

import com.example.huwamaruwa.Progress.LoadingProgress;
import com.example.huwamaruwa.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddNewItem extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextCat , autoCompleteTextloc;

    EditText addProductName, rentFee, addProdcontact, prodDescription, deposit,edtmaxDate,edtminRentTime;
    Switch  swhAddpost;
    private static final int CAMERA_REQEST = 1888;
    Button btnTakePhoto, btnGallery, btnPost;
    ArrayList<Uri> img_list;

    Product post;

    DatabaseReference dbRefe;
    StorageReference sdbRefe;
    LoadingProgress loadingProgress;
    String imgData[];
    RadioButton RentperDay_radio,RentperHour_radio;
    String userId;

    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_iteam);

        btnGallery = (Button) findViewById(R.id.btnGallery);
        btnPost = (Button) findViewById(R.id.btnPost);

        addProductName= (EditText) findViewById(R.id.ProductName1);
        rentFee = (EditText) findViewById(R.id.rentFee);
        addProdcontact = (EditText) findViewById(R.id.contact);
        prodDescription = (EditText) findViewById(R.id.description);
        deposit = (EditText) findViewById(R.id.deposit);
        edtmaxDate=(EditText)findViewById(R.id.maxDate);
        edtminRentTime=(EditText)findViewById(R.id.minDate);
        swhAddpost=(Switch)findViewById(R.id.swhAddpost);

        //getting current user
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        Log.e("login",user.getUid());
//        userId = user.getUid();

        img_list = new ArrayList<>();
        imgData = new String[4];
        loadingProgress = new LoadingProgress(AddNewItem.this);


        autoCompleteTextCat = findViewById(R.id.autoCompleteText1);
        autoCompleteTextloc = findViewById(R.id.autoCompleteText2);


        RentperHour_radio = findViewById(R.id.RentperHour_radio);
        RentperDay_radio = findViewById(R.id.perDay_radio);



        String[] category = {"Book", "Kitchenware", "Electronic", "Jewelleries", "Videography Equipments",
                "Construction", "Catering Equipments", "Camping Equipments", "Health Equipments",
                "Gaming Equipment", "Costumes","Ceremony Equipment"};


        String[] Location = {"Colombo", "Kandy", "Galle", "Ampara", "Anuradhapura", "Badulla", "Batticaloa",
                "Gampaha", "Hambantota", "Jaffna", "Kalutara", "Kilinochchi", "Kurunegla", "Mannar", "Matale",
                "Matara", "Monaragala", "Mullative", "Nuwara Eliya", "Polonnaruwa", "Puttalam", "Ratnapura",
                "Trincomalee", "Vavuniya"};

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.dropdown_category, category);
        autoCompleteTextCat.setText(arrayAdapter.getItem(0).toString(), false);
        autoCompleteTextCat.setAdapter(arrayAdapter);

        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, R.layout.dropdown_category, Location);
        autoCompleteTextloc.setText(arrayAdapter2.getItem(0).toString(), false);
        autoCompleteTextloc.setAdapter(arrayAdapter2);


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();

            }
        });


//        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                captureImg();
//            }
//        });



        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRefe = FirebaseDatabase.getInstance().getReference().child("Product");
                sdbRefe = FirebaseStorage.getInstance().getReference().child("Product Images");

                try {

                    if (TextUtils.isEmpty(addProductName.getText().toString().trim())){

                        Toast.makeText(AddNewItem.this, "Title Required", Toast.LENGTH_SHORT).show();
                    }else if (TextUtils.isEmpty(rentFee.getText().toString().trim())){
                        Toast.makeText(AddNewItem.this, "Price Required", Toast.LENGTH_SHORT).show();

                    }else if (TextUtils.isEmpty(prodDescription.getText().toString().trim())){

                        Toast.makeText(AddNewItem.this, "Description Required", Toast.LENGTH_SHORT).show();

                    }else if(img_list.isEmpty()){
                        Toast.makeText(AddNewItem.this, "Image Required", Toast.LENGTH_SHORT).show();
                    } else {

                        loadingProgress.startProgress();
                        imageUploader(0);

                    }
                }catch (Exception e){

                }





            }
        });


    }
    public void onRadioButton(View view) {

        boolean isChecked = ((RadioButton) view).isChecked();

        switch (view.getId()) {

            case R.id.perDay_radio:
                showMessage("Per Day..");
                break;
            case R.id.RentperHour_radio:
                showMessage("Per Hour..");

                break;
        }

    }



    public void dataUploader(){

        post = new Product();
        SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("dd");
        //Setting the time zone
        dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        post.setDate_in_day(Integer.parseInt(dateTimeInGMT.format(new Date())));
        dateTimeInGMT = new SimpleDateFormat("HH");
        post.setDate_in_hour(Integer.parseInt(dateTimeInGMT.format(new Date())));
        dateTimeInGMT = new SimpleDateFormat("mm");
        post.setDate_in_min(Integer.parseInt(dateTimeInGMT.format(new Date())));
        dateTimeInGMT = new SimpleDateFormat("ss");
        post.setDate_in_sec(Integer.parseInt(dateTimeInGMT.format(new Date())));
        dateTimeInGMT = new SimpleDateFormat("yyy");
        post.setDate_in_year(Integer.parseInt(dateTimeInGMT.format(new Date())));
        Boolean isPremium = swhAddpost.isChecked();
        post.setIsPremium(isPremium);
        post.setLocation(autoCompleteTextloc.getText().toString().trim());
        post.setTitle(addProductName.getText().toString().trim());
        post.setPrice(Double.parseDouble(rentFee.getText().toString().trim()));
        post.setContactNumber(addProdcontact.getText().toString().trim());
        post.setDepositPercentage(Double.parseDouble(deposit.getText().toString().trim()));
        post.setDescription(prodDescription.getText().toString().trim());
        post.setImages1(imgData[0]);
        post.setImages2(imgData[1]);
        post.setImages3(imgData[2]);
        post.setImages4(imgData[3]);
        post.setMaxRentalTime(Integer.parseInt(edtmaxDate.getText().toString().trim()));
        post.setMinRentalTime(Integer.parseInt(edtminRentTime.getText().toString().trim()));
        post.setSellerId(userId);
        post.setId(dbRefe.push().getKey());
        dbRefe.child(post.getId()).setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddNewItem.this, "Successfully Added...", Toast.LENGTH_SHORT).show();
                loadingProgress.dismissProgress();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddNewItem.this, "Unsuccessfully Failed", Toast.LENGTH_SHORT).show();
                loadingProgress.dismissProgress();

            }
        });
    }

    private void imageUploader(int i) {

        if(i >= img_list.size()){
            dataUploader();
        } else{
            Bitmap bmp = null;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),img_list.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
            byte[] data = baos.toByteArray();
            //uploading the image
            StorageReference storageReference  = sdbRefe.child(System.currentTimeMillis() +"."+ GetFileExtension(img_list.get(i)));
            int imgfinal = i;
            storageReference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddNewItem.this, "image "+ (imgfinal +1)+" uploaded", Toast.LENGTH_SHORT).show();
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imgData[imgfinal] =  uri.toString();
                            imageUploader(imgfinal+1);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddNewItem.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            imageUploader(imgfinal+1);
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddNewItem.this, "image "+(imgfinal +1 )+" uploading Failed", Toast.LENGTH_SHORT).show();
                    imageUploader(imgfinal+1);
                }
            });

        }

    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }




    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void imageChooser() {
        FishBun.with(this).setImageAdapter(new GlideAdapter()).setMaxCount(4).setRequestCode(100).startAlbum();
    }


    private void captureImg(){

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {

            img_list = data.getParcelableArrayListExtra(FishBun.INTENT_PATH);

            initRecycler();
        }
    }

    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.img_prev_recycler);
        AddNewItemAdapter postAdapter = new AddNewItemAdapter(this,img_list);
        recyclerView.setAdapter(postAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
    }

}