package com.example.huwamaruwa.singleProduct;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.huwamaruwa.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

public class RequestRent extends AppCompatActivity {

    Button btn;
    TextView textView;
    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_rent);

        btn = findViewById(R.id.date_range);
        textView = findViewById(R.id.date_view);
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