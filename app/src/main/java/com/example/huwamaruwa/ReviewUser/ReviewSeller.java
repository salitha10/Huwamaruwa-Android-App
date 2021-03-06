package com.example.huwamaruwa.ReviewUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huwamaruwa.Models.SellerReview;
import com.example.huwamaruwa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReviewSeller extends AppCompatActivity {

    EditText comment;
    TextView name;
    ImageView pic;
    RatingBar r1, r2, r3;
    Button cancel, done;
    SellerReview sr;
    String productID, userID, sellerID;
    DatabaseReference dbf;
    FirebaseUser user;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_seller);

        comment = (EditText) findViewById(R.id.editTextSellerReview);
        name = (TextView)findViewById(R.id.txtNameSR);
        pic = (ImageView) findViewById(R.id.imgSellerSellerReview);
        r1 = (RatingBar) findViewById(R.id.pRBSellerReview);
        r2 = (RatingBar) findViewById(R.id.hRBSellerReview);
        r3 = (RatingBar) findViewById(R.id.cRBSellerReview);
        sr = new SellerReview();
        //Get current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        //userID = user.getUid();
        userID = "Lud7rSb7CyeJLQt7saQOVYTZv953";
        sellerID = "HVGC2pBrNXaeGwcZAYfyxe0wgwJ3";
    }

    public void onResume() {
        super.onResume();

        //Display product details
        productID = "-MZml4o8n65POxbXFlcO";
        dbf = FirebaseDatabase.getInstance().getReference().child("Users").child(sellerID);

        //Get data from product
        dbf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    name.setText(snapshot.child("name").getValue().toString());
                    //String imageURL = snapshot.child("userImage").getValue().toString();
                    //Glide.with(getApplicationContext()).load(imageURL).circleCrop().placeholder(R.drawable.ic_launcher_background).into(pic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void Save(View v) {

        try {

            DatabaseReference dbf = FirebaseDatabase.getInstance().getReference().child("SellerReview");

            //Get values
            if (r1.getRating() == 0 || r2.getRating() == 0 || r3.getRating() == 0) {
                Toast.makeText(getApplicationContext(), "Rating can't be empty", Toast.LENGTH_SHORT).show();
            } else {

                sr.setProductID(productID);
                sr.setReviewerID(userID);
                sr.setComment(comment.getText().toString().trim());
                sr.setPriceRating(r1.getRating());
                sr.setHandlingRating(r2.getRating());
                sr.setComRating(r3.getRating());
                sr.setDate(date);

                //dbf.push().setValue(sr);
                String recID = dbf.push().getKey();
                sr.setID(recID);
                dbf.child(recID).setValue(sr);

                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

                /**
                 *  Put log messages
                 */

            }
        } catch (Exception e) {

        }
    }
}