package com.example.huwamaruwa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class FireStore_crud extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    //Keys
    public static final String NAME = "name";
    public static final String EMAIL = "email";

    //Views
    EditText txt1, txt2;
    Button save,viewData;

    //Reference Document to firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_store_crud);
    }


    //Insert
    public void saveData(View view) {

        //Initialize
        txt1 = (EditText)findViewById(R.id.txt1);
        txt2 = (EditText)findViewById(R.id.txt2);
        save = (Button) findViewById(R.id.btnSave);
        viewData = (Button) findViewById(R.id.btnView);
        String name = txt1.getText().toString();
        String email = txt2.getText().toString();

        if(name.isEmpty() || email.isEmpty()){ return;}

        //Store data in HasMap to save in database
        Map<String, Object> dataToSave = new HashMap<>();
        dataToSave.put(NAME, name);
        dataToSave.put(EMAIL, email);

        //Save data in reference doc
        db.collection("TestCollection").add(dataToSave).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"Data saved", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Data Saved");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Data not saved", Toast.LENGTH_LONG).show();
                Log.d(TAG, "Data Not Saved");
            }
        });
    }


    //Read
    public void readData(View view){
        Toast.makeText(getApplicationContext(),"See Log", Toast.LENGTH_LONG).show();
        db.collection("TestCollection")
                .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }

}

