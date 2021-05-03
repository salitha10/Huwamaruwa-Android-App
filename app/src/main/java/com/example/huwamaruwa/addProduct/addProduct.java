package com.example.huwamaruwa.addProduct;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.Progress.LoadingProgress;
import com.example.huwamaruwa.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class addProduct extends AppCompatActivity {

    Button btnSelect,btnUpload;
    EditText edtTitle,edtPrice,edtDes;
    ArrayList<Uri>prev_img_list;
    Product product;
    DatabaseReference dbRef;
    StorageReference sdbRef;
    LoadingProgress loadingProgress;
    String imgData[];
    Switch ifPremium;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        btnSelect = (Button)findViewById(R.id.btnSelect);
        btnUpload = (Button)findViewById(R.id.btnUpload);

        edtTitle = findViewById(R.id.edtTitle);
        edtPrice = findViewById(R.id.edtPrice);
        edtDes = findViewById(R.id.edtDescription);

        ifPremium = findViewById(R.id.switchPremiumAddProduct);

        prev_img_list = new ArrayList<>();
        imgData = new String[4];
        loadingProgress = new LoadingProgress(addProduct.this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Product");
                sdbRef = FirebaseStorage.getInstance().getReference().child("Product Images");

              
              
                try {
                    if (TextUtils.isEmpty(edtTitle.getText().toString().trim())){
                        Toast.makeText(addProduct.this, "Title Required", Toast.LENGTH_SHORT).show();
                    }else if (TextUtils.isEmpty(edtPrice.getText().toString().trim())){
                        Toast.makeText(addProduct.this, "Price Required", Toast.LENGTH_SHORT).show();

                    }else if (TextUtils.isEmpty(edtDes.getText().toString().trim())){
                        Toast.makeText(addProduct.this, "Description Required", Toast.LENGTH_SHORT).show();

                    }else if(prev_img_list.isEmpty()){
                        Toast.makeText(addProduct.this, "Image Required", Toast.LENGTH_SHORT).show();
                    } else {

                        loadingProgress.startProgress();
                        imageUploader(0);

                    }
                }catch (Exception e){

                }

  
  
            }
        });
    }

    private void dataUploader() {
        product = new Product();
        product.setTitle(edtTitle.getText().toString().trim());
        product.setPrice(edtPrice.getText().toString().trim());
        product.setDescription(edtDes.getText().toString().trim());
        Boolean isPremium = ifPremium.isChecked();
        product.setIsPremium(isPremium.toString());
        product.setImages1(imgData[0]);
        product.setImages2(imgData[1]);
        product.setImages3(imgData[2]);
        product.setImages4(imgData[3]);
        product.setDate(DateFormat.getDateInstance().getCalendar().getTime());

        product.setId(dbRef.push().getKey());
        dbRef.child(product.getId()).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(addProduct.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                loadingProgress.dismissProgress();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(addProduct.this, "Product Failed To Upload", Toast.LENGTH_SHORT).show();
                loadingProgress.dismissProgress();
            }
        });
    }


    private void imageUploader(int i) {

        if(i >= prev_img_list.size()){
            dataUploader();
        } else{
            StorageReference storageReference  = sdbRef.child(System.currentTimeMillis() +"."+ GetFileExtension(prev_img_list.get(i)));
            int finalI = i;
            storageReference.putFile(prev_img_list.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(addProduct.this, "image "+ (finalI +1)+" uploaded", Toast.LENGTH_SHORT).show();
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imgData[finalI] =  uri.toString();
                            imageUploader(finalI+1);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(addProduct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            imageUploader(finalI+1);
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(addProduct.this, "image "+(finalI +1 )+" uploading Failed", Toast.LENGTH_SHORT).show();
                    imageUploader(finalI+1);
                }
            });

        }

    }


    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }
    private void imageChooser() {
        FishBun.with(this).setImageAdapter(new GlideAdapter()).setMaxCount(4).setRequestCode(100).startAlbum();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK){

            Toast.makeText(this, "Multiple Image selected", Toast.LENGTH_SHORT).show();
            prev_img_list = data.getParcelableArrayListExtra(FishBun.INTENT_PATH);

            initRecycler();


        }
    }

    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.img_prev_recycler);
        AddProductAdapter addProductAdapter = new AddProductAdapter(this,prev_img_list);
        recyclerView.setAdapter(addProductAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
    }
}