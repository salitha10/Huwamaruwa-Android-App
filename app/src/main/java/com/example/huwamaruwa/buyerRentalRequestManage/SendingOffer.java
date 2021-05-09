package com.example.huwamaruwa.buyerRentalRequestManage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huwamaruwa.Home.Home_recycler_1_adapter;
import com.example.huwamaruwa.Models.SendingOffersModel;
import com.example.huwamaruwa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;

import java.util.ArrayList;

public class SendingOffer extends AppCompatActivity {

    TextView productName, category, quantity, requiredDate, budget, buyerRequestId;
    BuyerRentalRequestsModel buyerRentalRequestsModel;
    EditText sendingProductTitle, sendingRental, sendingQuantity;
    Button sendingOfferSubmit, getSendingOfferCancel, selectPhoto;
    CircularImageView productImage;
    ArrayList<Uri> addedImage;
    String FinalImageString;
    DatabaseReference dbf;
    StorageReference sdbRefe;
    SendingOffersModel sendingOffersModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_offer);

        productName = findViewById(R.id.sendingOffer_productName);
        category = findViewById(R.id.sendingOffer_category);
        quantity = findViewById(R.id.sendingOffer_quantity);
        requiredDate = findViewById(R.id.sendingOffer_requiredDate);
        budget = findViewById(R.id.sendingOffer_budget);
        buyerRequestId = findViewById(R.id.sendingOffer_buyerRequestId);
        selectPhoto = findViewById(R.id.btnSendingForm_selectPhoto);
        productImage = findViewById(R.id.sendingForm_photo);

        buyerRentalRequestsModel = getIntent().getParcelableExtra(AllBuyerRequestsAdapter.EXTRA_MESSAGE);

        productName.setText(buyerRentalRequestsModel.getProductTitle());
        category.setText(buyerRentalRequestsModel.getCategory());
        quantity.setText(buyerRentalRequestsModel.getQuantity());
        requiredDate.setText(buyerRentalRequestsModel.getRequiredDate());
        budget.setText(buyerRentalRequestsModel.getBudget());
        buyerRequestId.setText(buyerRentalRequestsModel.getBuyerRequestId());

        //sending Offer Details
        sendingProductTitle = findViewById(R.id.sendingForm_productName);
        sendingRental = findViewById(R.id.sendingForm_rental);
        sendingQuantity = findViewById(R.id.sendingForm_quantity);
        sendingOfferSubmit = findViewById(R.id.btnSendingForm_sendOffer);
        getSendingOfferCancel = findViewById(R.id.btnSendingForm_cancel);

        sendingOffersModel = new SendingOffersModel();

        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

    }

    public void sendOffer(View view){
        if (TextUtils.isEmpty(sendingProductTitle.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Product name Field is empty", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(sendingQuantity.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Quantity Field is empty", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(sendingRental.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Rental Field is empty", Toast.LENGTH_SHORT).show();
        }
        else if((Float.parseFloat(sendingRental.getText().toString()) > (Float.parseFloat(budget.getText().toString())))){
            Toast.makeText(getApplicationContext(), "Value should be less than " + budget.getText().toString(), Toast.LENGTH_SHORT).show();
        }
        else {

            if (addedImage != null) {
                addImagetoDB();
            } else {
                Toast.makeText(getApplicationContext(), "Profile Picture Not updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveDetails(){
        try {


            dbf = FirebaseDatabase.getInstance().getReference().child("SentOffersForProductRequest");



                sendingOffersModel.setProductName(sendingProductTitle.getText().toString().trim());
                sendingOffersModel.setQuantity(sendingQuantity.getText().toString().trim());
                sendingOffersModel.setRental(sendingRental.getText().toString().trim());
                sendingOffersModel.setStatus("Pending".trim());
                sendingOffersModel.setSendingRequestId(buyerRequestId.getText().toString().trim());
                sendingOffersModel.setProductRequestId(dbf.push().getKey());
                sendingOffersModel.setProductImage(FinalImageString.trim());

//                dbf.push().setValue(sendingOffersModel);
                dbf.child(sendingOffersModel.getProductRequestId()).setValue(sendingOffersModel);


                Toast.makeText(getApplicationContext(), "Request added successfully", Toast.LENGTH_LONG).show();


        }catch (Exception e){

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
//                        Toast.makeText(getApplicationContext(), "FinalImageString set", Toast.LENGTH_SHORT).show();
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
}