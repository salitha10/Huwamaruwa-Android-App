package com.example.huwamaruwa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.huwamaruwa.Models.SignUpModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextInputLayout name, email, phoneNo, username, password, address, confirm_password;
    String userType;
    MaterialButton btnSignUp;
    DatabaseReference dbf;
    SignUpModel signUpModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phoneNo = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        username = findViewById(R.id.Username);
        password =  findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);

        btnSignUp = findViewById(R.id.btnSignUp);

        signUpModel =  new SignUpModel();
    }
    public void signIn(View view){
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.userSeller:
                if (checked)
                    userType = "Seller";
                    break;
            case R.id.userBuyer:
                if (checked)
                    userType = "Buyer";
                    break;
        }

    }
    //saving details to the Datbase
    public void Save(View view){
        boolean check = true;

        String passwordInput = password.getEditText().getText().toString();
        String confirmPasswordInput = confirm_password.getEditText().getText().toString();

        try {
            dbf = FirebaseDatabase.getInstance().getReference().child("Users");
            if (TextUtils.isEmpty(name.getEditText().getText().toString())) {
                check = false;
                Toast.makeText(getApplicationContext(), "Name Text Field is empty", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(email.getEditText().getText().toString())) {
                check = false;
                Toast.makeText(getApplicationContext(), "Email Text Field is empty", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(phoneNo.getEditText().getText().toString())) {
                check = false;
                Toast.makeText(getApplicationContext(), "Phone No. Text Field is empty", Toast.LENGTH_SHORT).show();
            }
            if (userType == null) {
                check = false;
                Toast.makeText(getApplicationContext(), "User type not selected", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(address.getEditText().getText().toString())) {
                check = false;
                Toast.makeText(getApplicationContext(), "Address Text Field is empty", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(username.getEditText().getText().toString())) {
                check = false;
                Toast.makeText(getApplicationContext(), "username Text Field is empty", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(password.getEditText().getText().toString())) {
                check = false;
                Toast.makeText(getApplicationContext(), "Password Text Field is empty", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(confirm_password.getEditText().getText().toString())) {
                check = false;
                Toast.makeText(getApplicationContext(), "Re-enter Password", Toast.LENGTH_SHORT).show();
            }
            if(!(passwordInput.equals(confirmPasswordInput))){
                check = false;
                Toast.makeText(getApplicationContext(), "Password mis-match", Toast.LENGTH_SHORT).show();
            }
            else{

                signUpModel.setName(name.getEditText().getText().toString().trim());
                signUpModel.setEmail(email.getEditText().getText().toString().trim());
                signUpModel.setPhoneNo(name.getEditText().getText().toString().trim());
                signUpModel.setUserType(userType.trim());
                signUpModel.setAddress(address.getEditText().getText().toString().trim());
                signUpModel.setUsername(username.getEditText().getText().toString().trim());
                signUpModel.setPassword(password.getEditText().getText().toString().trim());
//                dbf.push().setValue(student);

                if (check == true){
                    dbf.child(userType).push().setValue(signUpModel);
                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                }
            }


        }
        catch (Exception e){
            System.out.println(e);

        }
    }
}