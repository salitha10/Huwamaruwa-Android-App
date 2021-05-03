package com.example.huwamaruwa.singleProduct;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

public class RequestRent extends AppCompatActivity {

    Button btn;
    ImageView imgMain;
    TextView textView,txtTitle,txtDescription,txtPrice;
    Product product;
    public static final String RS="RS. ";
    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_rent);

        btn = findViewById(R.id.date_range);

        textView = findViewById(R.id.date_view);
        txtPrice = findViewById(R.id.txtRequest_rent_form_price);
        txtDescription = findViewById(R.id.txtRequest_rent_form_description);
        txtTitle = findViewById(R.id.txtRequest_rent_form_title);

        imgMain = findViewById(R.id.imgRequest_rent_form_main);

        product = getIntent().getParcelableExtra(PremiumProduct.REQUEST_RENT_TAG);

        Glide.with(this).load(product.getImages1()).into(imgMain);
        txtTitle.setText(product.getTitle());
        txtDescription.setText(product.getDescription());
        txtPrice.setText(RS.concat(product.getPrice())+"/Per Day" );


        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("select date");
       final MaterialDatePicker materialDatePicker = builder.build();

       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");
           }
       });


       materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
           @Override
           public void onPositiveButtonClick(Object selection) {
                    textView.setText(materialDatePicker.getHeaderText());
           }
       });

    }


}