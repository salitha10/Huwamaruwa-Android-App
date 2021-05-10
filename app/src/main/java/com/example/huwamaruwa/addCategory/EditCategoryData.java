package com.example.huwamaruwa.addCategory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.huwamaruwa.AddCategory;
import com.example.huwamaruwa.Models.Categories;
import com.example.huwamaruwa.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditCategoryData extends AppCompatActivity {

    TextInputLayout editCategoryTitle, editCategoryType1, editCategoryType2, editCategoryType3, editCategoryType4, editCategoryType5, editCategoryType6, edtcatType3, edtcatType4, edtcatType5, edtcatType6;
    Button edtCatUpdate;
    DatabaseReference dbref;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category_data);

        editCategoryTitle = findViewById(R.id.edtcatName);
        editCategoryType1 = findViewById(R.id.edtcatType1);
        editCategoryType2 = findViewById(R.id.edtcatType2);
        editCategoryType3 = findViewById(R.id.edtcatType3);
        editCategoryType4 = findViewById(R.id.edtcatType4);
        editCategoryType5 = findViewById(R.id.edtcatType5);
        editCategoryType6 = findViewById(R.id.edtcatType6);
        edtCatUpdate = findViewById(R.id.edtCatUpdate);

        Intent intent = getIntent();
        id = intent.getStringExtra("catId");


        dbref = FirebaseDatabase.getInstance().getReference().child("Category").child(id);
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    editCategoryTitle.getEditText().setText(snapshot.child("categoryTitle").getValue().toString());
                    editCategoryType1.getEditText().setText(snapshot.child("catType1").getValue().toString());
                    editCategoryType2.getEditText().setText(snapshot.child("catType2").getValue().toString());
                    editCategoryType3.getEditText().setText(snapshot.child("catType3").getValue().toString());
                    editCategoryType4.getEditText().setText(snapshot.child("catType4").getValue().toString());
                    editCategoryType5.getEditText().setText(snapshot.child("catType5").getValue().toString());
                    editCategoryType6.getEditText().setText(snapshot.child("catType6").getValue().toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        edtCatUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = getIntent();
                id = intent1.getStringExtra("catId");


                dbref = FirebaseDatabase.getInstance().getReference().child("Category");
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.hasChildren()) {

                            Categories category = new Categories();
                            category.setCatId(id);

                            category.setCategoryTitle(editCategoryTitle.getEditText().getText().toString().trim());
                            category.setCatType1(editCategoryType1.getEditText().getText().toString().trim());
                            category.setCatType2(editCategoryType2.getEditText().getText().toString().trim());
                            category.setCatType3(editCategoryType3.getEditText().getText().toString().trim());
                            category.setCatType4(editCategoryType4.getEditText().getText().toString().trim());
                            category.setCatType5(editCategoryType5.getEditText().getText().toString().trim());
                            category.setCatType6(editCategoryType6.getEditText().getText().toString().trim());

                            dbref.child(id).setValue(category);
                            Toast.makeText(EditCategoryData.this, "Update Successfully", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(EditCategoryData.this, "Not Updated", Toast.LENGTH_SHORT).show();
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
