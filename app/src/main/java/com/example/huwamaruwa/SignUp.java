package com.example.huwamaruwa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.huwamaruwa.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextInputLayout name, email, phoneNo, password, address, confirm_password;
    String userType;
    MaterialButton btnSignUp;
    DatabaseReference dbf;
    User user;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phoneNo = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        password =  findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);

        btnSignUp = findViewById(R.id.btnSignUp);

        user =  new User();

        firebaseAuth = FirebaseAuth.getInstance();
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



                if (check == true){

                    firebaseAuth.createUserWithEmailAndPassword(email.getEditText().getText().toString(),
                            password.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                dbf.child(userType).push().setValue(user);
                                Intent intent = new Intent(SignUp.this,MainActivity.class);
                                startActivity(intent);

                                Toast.makeText(SignUp.this, "Registered User Successful", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

//                    user.setUserId(dbf.push().getKey());
                    user.setUserId(currentUser.getUid());
                    user.setUserImage("");
                    user.setName(name.getEditText().getText().toString().trim());
                    user.setEmail(email.getEditText().getText().toString().trim());
                    user.setPhoneNo(name.getEditText().getText().toString().trim());
                    user.setUserType(userType.trim());
                    user.setAddress(address.getEditText().getText().toString().trim());
                    user.setPassword(password.getEditText().getText().toString().trim());
//                dbf.push().setValue(student);



                }
            }


        }
        catch (Exception e){

        }
    }
}