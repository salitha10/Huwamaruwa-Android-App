package com.example.huwamaruwa.buyerRentalRequestManage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.Models.SendingOffersModel;
import com.example.huwamaruwa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;

import java.util.ArrayList;

public class EditSingleSellerRequest extends AppCompatActivity {

    EditText sendingProductTitle, sendingRental, sendingQuantity;
    ImageView productImage;
    TextView requestId;
    ArrayList<Uri> addedImage;

    SendingOffersModel sendingOffersModel;
    Button update, cancel, updatePhoto;
    DatabaseReference dbf;
    StorageReference sdbRefe;
    String FinalImageString;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_single_seller_request);


        sendingProductTitle = findViewById(R.id.sendingForm_productName);
        requestId = findViewById(R.id.sendingForm_requestId);
        sendingRental = findViewById(R.id.sendingForm_rental);
        sendingQuantity = findViewById(R.id.sendingForm_quantity);
        update = findViewById(R.id.btnSendingForm_sendOffer);
        cancel = findViewById(R.id.btnSendingForm_cancel);
        productImage = findViewById(R.id.EditView_Image);
        updatePhoto = findViewById(R.id.EditView_gallery);


        sendingOffersModel = getIntent().getParcelableExtra(SentRentalRequestBySellerAdapter.EXTRA_MESSAGE1);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(sendingOffersModel.getProductName());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        sendingProductTitle.setText(sendingOffersModel.getProductName());
        sendingQuantity.setText(sendingOffersModel.getQuantity());
        sendingRental.setText(sendingOffersModel.getRental());
        requestId.setText(sendingOffersModel.getProductRequestId());
        FinalImageString = sendingOffersModel.getProductImage();
        Glide.with(this).load(sendingOffersModel.getProductImage()).into(productImage);

        updatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditSingleSellerRequest.this, SentRentalRequestBySeller.class));
            }
        });

    }

    public void UpdateUser(View view){
        if (addedImage != null){
            addImagetoDB();
        }else{
            saveDetails();
            Toast.makeText(getApplicationContext(), "Profile Picture Not updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void imageChooser(){
        FishBun.with(this).setImageAdapter(new GlideAdapter()).setMaxCount(1).setRequestCode(100).startAlbum();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {

            addedImage = data.getParcelableArrayListExtra(FishBun.INTENT_PATH);
            productImage.setImageURI(addedImage.get(0));

            Toast.makeText(getApplicationContext(), "Image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void addImagetoDB() {
        sdbRefe = FirebaseStorage.getInstance().getReference().child("Offers for buyer requests");
        StorageReference storageReference  = sdbRefe.child(System.currentTimeMillis() +"."+ GetFileExtension(addedImage.get(0)));
        storageReference.putFile(addedImage.get(0)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        FinalImageString = uri.toString();
                        Toast.makeText(getApplicationContext(), "FinalImageString set", Toast.LENGTH_SHORT).show();
                        saveDetails();
                    }
                });

            }
        });
    }

    private String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void saveDetails(){
        dbf = FirebaseDatabase.getInstance().getReference().child("SentOffersForProductRequest");

        if (TextUtils.isEmpty(sendingProductTitle.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Text Field is empty", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(sendingQuantity.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Text Field is empty", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(sendingRental.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Text Field is empty", Toast.LENGTH_SHORT).show();
        }
        else{
            dbf.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(requestId.getText().toString())){
                        sendingOffersModel.setProductName(sendingProductTitle.getText().toString());
                        sendingOffersModel.setQuantity(sendingQuantity.getText().toString());
                        sendingOffersModel.setRental(sendingRental.getText().toString());
                        sendingOffersModel.setProductImage(FinalImageString.trim());

                        dbf = FirebaseDatabase.getInstance().getReference().child("SentOffersForProductRequest").child(requestId.getText().toString());
                        dbf.setValue(sendingOffersModel);
                        dbf.keepSynced(true);

                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(EditSingleSellerRequest.this,SentRentalRequestBySeller.class);
                        startActivity(intent);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}