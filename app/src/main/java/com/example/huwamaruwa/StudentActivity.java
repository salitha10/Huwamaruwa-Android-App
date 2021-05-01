package com.example.huwamaruwa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Only one child is created from this code, change according to your needs
 */

public class StudentActivity extends AppCompatActivity {

    EditText id, name, address, phone;
    Button save, show, update, delete;
    DatabaseReference dbf;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        id = (EditText) findViewById(R.id.txtId);
        name = (EditText) findViewById(R.id.txtName);
        address = (EditText) findViewById(R.id.txtAddress);
        phone = (EditText) findViewById(R.id.txtPhone);

        save = (Button) findViewById(R.id.btnSave);
        show = (Button) findViewById(R.id.btnShow);
        update = (Button) findViewById(R.id.btnUpdate);
        delete = (Button) findViewById(R.id.btnDelete);

        student = new Student();
    }

    private void clearContent(){
        id.setText("");
        name.setText("");
        address.setText("");
        phone.setText("");
    }

    public void Save(View v){

        try {
            dbf = FirebaseDatabase.getInstance().getReference().child("Student");

            if (TextUtils.isEmpty(id.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT);
            }
            if (TextUtils.isEmpty(id.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT);
            }
            if (TextUtils.isEmpty(id.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT);
            }
            if (TextUtils.isEmpty(id.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT);
            } else {

                student.setId(id.getText().toString().trim());
                student.setName(name.getText().toString().trim());
                student.setAddress(address.getText().toString().trim());
                student.setConNo(Integer.parseInt(phone.getText().toString().trim()));

                //dbf.push().setValue(student);
                dbf.child("Std1").setValue(student);

                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT);

                /**
                 *  Put log messages
                 */


                clearContent();
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT);
        }
    }

    public void Show(View v) {

        //Get data from Sdt1
            dbf = FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");

            dbf.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChildren()){
                        id.setText(snapshot.child("id").getValue().toString());
                        name.setText(snapshot.child("name").getValue().toString());
                        address.setText(snapshot.child("address").getValue().toString());
                        phone.setText(snapshot.child("conNo").getValue().toString());
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    public void Update(View v) {

        dbf = FirebaseDatabase.getInstance().getReference().child("Student");

        dbf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Get data from Std1
                if(snapshot.hasChild("Std1")){
                    student.setId(id.getText().toString().trim());
                    student.setName(name.getText().toString().trim());
                    student.setAddress(address.getText().toString().trim());
                    student.setConNo(Integer.parseInt(phone.getText().toString().trim()));

                    //set values
                    dbf = FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
                    dbf.setValue(student);
                    clearContent();

                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Update Failed", Toast.LENGTH_SHORT);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void Delete(View v) {

        dbf = FirebaseDatabase.getInstance().getReference().child("Student");

        dbf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Get data from Std1
                if(snapshot.hasChild("Std1")){
                    dbf = FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
                    dbf.removeValue();
                    clearContent();
                    Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Delete Failed", Toast.LENGTH_SHORT);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}