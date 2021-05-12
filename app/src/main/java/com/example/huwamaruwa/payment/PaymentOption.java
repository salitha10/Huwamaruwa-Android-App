package com.example.huwamaruwa.payment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.huwamaruwa.MainActivity;
import com.example.huwamaruwa.Models.RequestRentModel;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.singleProduct.RequestRent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

public class PaymentOption extends AppCompatActivity {
CardView oneTimePayment,cashOnDelivery;
TextView amount;
Double productAmount =0.00;
Dialog dialog;
RequestRentModel requestRent;
DatabaseReference dbRef;
    Bundle bundle;
    Button payBtn;
    String id = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);

        oneTimePayment = findViewById(R.id.card_oneTime_Payment);
        cashOnDelivery = findViewById(R.id.card_cash_on_delivery);

        dialog = new Dialog(this);


       bundle = getIntent().getExtras();


       productAmount = bundle.getDouble("deposit");
//       productAmount = bundle.getString("address")
//       productAmount = bundle.getString("contact");
//       productAmount = bundle.getString("duration");
//       productAmount = bundle.getDouble("total");
//       productAmount = bundle.getBoolean("isPremium");
//       productAmount = bundle.getString("productId");
//       productAmount = bundle.getString("dateDif");
//       productAmount = bundle.getString("userId");
        oneTimePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PaymentOption.this,R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.fragment_add_payment__bottom__dialog_, (LinearLayout)findViewById(R.id.bottom_sheet_layout_add_payment));
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
               amount = bottomSheetView.findViewById(R.id.txt_add_payment_amount);
               amount.setText(Double.toString(productAmount));
                payBtn= bottomSheetView.findViewById(R.id.btnpayment_option_add_payment);

                payBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addRequestData();
                    }
                });
            }
        });

        cashOnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id != null){
                    fancyDialog();
                }else {
                    warningDialog();
                }

            }
        });


    }
    public void fancyDialog(){
        new FancyGifDialog.Builder(PaymentOption.this)
                .setTitle("You Almost Done !") // You can also send title like R.string.from_resources
                .setMessage("Give us some times to review your request and we will contact you soon.Thank you") // or pass like R.string.description_from_resources
                .setNegativeBtnText("Cancel") // or pass it like android.R.string.cancel
                .setPositiveBtnBackground(R.color.lightGrayBtn) // or pass it like R.color.positiveButton
                .setPositiveBtnText("Ok") // or pass it like android.R.string.ok
                .setNegativeBtnBackground(R.color.vetorGreen) // or pass it like R.color.negativeButton
                .setGifResource(R.drawable.delivery_done)   //Pass your Gif here
                .isCancellable(false)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                       Intent intent = new Intent(PaymentOption.this, MainActivity.class);
                       startActivity(intent);
                    }
                })
                .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        //Toast.makeText(MainActivity.this,"Cancel",Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }

    public void warningDialog(){
        //View alertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.cash_on_delivery_confirm_alert, (ConstraintLayout)findViewById(R.id.cash_on_delivery_warning_dialog_layout));
        dialog.setContentView(R.layout.cash_on_delivery_confirm_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imageViewClose = dialog.findViewById(R.id.img_close_button);
        Button confirm = dialog.findViewById(R.id.btn_confirrm_cash_ob_delivery);
        dialog.setCancelable(false);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRequestData();
                fancyDialog();

                dialog.dismiss();
            }
        });
        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }
    public void addRequestData(){
        dbRef = FirebaseDatabase.getInstance().getReference().child("RequestRent");
        requestRent = new RequestRentModel();
        requestRent.setAddress(bundle.getString("address"));
        requestRent.setContactNumber(bundle.getString("contact"));
        requestRent.setDuration(bundle.getString("duration"));
        requestRent.setInitialDeposit(bundle.getDouble("deposit"));
        requestRent.setTotal(bundle.getDouble("total"));
        requestRent.setIsPremium(bundle.getString("isPremium"));
        requestRent.setProductId(bundle.getString("productId"));
        requestRent.setDateDif(bundle.getString("dateDif"));
        requestRent.setStatus("Pending");
        requestRent.setUserId(bundle.getString("userId"));
        requestRent.setSellerId(bundle.getString("sellerId"));
        id = dbRef.push().getKey();
        requestRent.setId(id);
        dbRef.child(requestRent.getId()).setValue(requestRent).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(PaymentOption.this, "Data added successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PaymentOption.this, "Request failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}