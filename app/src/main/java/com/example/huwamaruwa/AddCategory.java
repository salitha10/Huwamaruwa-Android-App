package com.example.huwamaruwa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huwamaruwa.Models.Categories;
import com.example.huwamaruwa.Progress.LoadingProgress;
import com.example.huwamaruwa.buyerRentalRequestManage.BuyerRentalRequestsModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddCategory extends AppCompatActivity {

    TextInputLayout AddCategoryTitle, catType1, catType2, catType3, catType4, catType5, catType6;
    Button catSubbtn;
    DatabaseReference dbRefe;
    StorageReference sdbRefe;
    LoadingProgress loadingProgress;
    Categories cat;
    int i =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        catSubbtn = (Button) findViewById(R.id.btnCatsub);
        AddCategoryTitle = (TextInputLayout) findViewById(R.id.txtcatName);
        catType1 = (TextInputLayout) findViewById(R.id.txtcatType1);
        catType2 = (TextInputLayout) findViewById(R.id.txtcatType2);
        catType3 = (TextInputLayout) findViewById(R.id.txtcatType3);
        catType4 = (TextInputLayout) findViewById(R.id.txtcatType4);
        catType5 = (TextInputLayout) findViewById(R.id.txtcatType5);
        catType6 = (TextInputLayout) findViewById(R.id.txtcatType6);

        cat = new Categories();

        dbRefe = FirebaseDatabase.getInstance().getReference().child("Category");

        catSubbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (TextUtils.isEmpty(AddCategoryTitle.getEditText().getText().toString())) {
                        Toast.makeText(AddCategory.this, "Empty...", Toast.LENGTH_SHORT).show();

                    } else {
                        cat.setCategoryTitle(AddCategoryTitle.getEditText().getText().toString().trim());
                        cat.setCatType1(catType1.getEditText().getText().toString().trim());
                        cat.setCatType2(catType2.getEditText().getText().toString().trim());
                        cat.setCatType3(catType3.getEditText().getText().toString().trim());
                        cat.setCatType4(catType4.getEditText().getText().toString().trim());
                        cat.setCatType5(catType5.getEditText().getText().toString().trim());
                        cat.setCatType6(catType6.getEditText().getText().toString().trim());

                        cat.setCatId(dbRefe.push().getKey());
                        dbRefe.child(cat.getCatId()).setValue(cat).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AddCategory.this, "Successfully Added New Category", Toast.LENGTH_SHORT).show();
                                loadingProgress.dismissProgress();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddCategory.this, "Failed...", Toast.LENGTH_SHORT).show();
                                loadingProgress.dismissProgress();
                            }
                        });

                        Toast.makeText(AddCategory.this, "Saved New Category", Toast.LENGTH_SHORT).show();


                    }

                } catch (Exception e) {

                }

                clearContent();


            }
        });

    }


    private void clearContent() {
        AddCategoryTitle.getEditText().setText("");
        catType1.getEditText().setText("");
        catType2.getEditText().setText("");
        catType3.getEditText().setText("");
        catType4.getEditText().setText("");
        catType5.getEditText().setText("");
        catType6.getEditText().setText("");
    }


}
