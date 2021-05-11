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

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.Models.BuyerReview;
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

public class ReviewBuyer extends AppCompatActivity {

    EditText comment;
    TextView name;
    ImageView pic;
    RatingBar r1, r2, r3;
    Button cancel, done;
    BuyerReview br;
    String productID, userID, buyerID;
    DatabaseReference dbf;
    FirebaseUser user;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_buyer);

        comment = (EditText) findViewById(R.id.editTextBuyerReview);
        name = (TextView) findViewById(R.id.nameReportBuyer);
        pic = (ImageView) findViewById(R.id.imgReportBuyer);
        r1 = (RatingBar) findViewById(R.id.pRBBuyerReview);
        r2 = (RatingBar) findViewById(R.id.hRBBuyerReview);
        r3 = (RatingBar) findViewById(R.id.cRBBuyerReview);
        br = new BuyerReview();
        //Get current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        //userID = user.getUid();
        userID = "Lud7rSb7CyeJLQt7saQOVYTZv953";
        buyerID = "Lud7rSb7CyeJLQt7saQOVYTZv953";
    }

    public void onResume() {
        super.onResume();

        //Display product details
        productID = "-MZml4o8n65POxbXFlcO";
        dbf = FirebaseDatabase.getInstance().getReference().child("Users").child(buyerID);

        //Get data from product
        dbf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    name.setText(snapshot.child("name").getValue().toString());
                    String imageURL = snapshot.child("userImage").getValue().toString();
                    Glide.with(getApplicationContext()).load(imageURL).circleCrop().placeholder(R.drawable.ic_launcher_background).into(pic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void Save(View v) {

        try {

            DatabaseReference dbf = FirebaseDatabase.getInstance().getReference().child("BuyerReview");

            if (r1.getRating() == 0 || r2.getRating() == 0 || r3.getRating() == 0) {
                Toast.makeText(getApplicationContext(), "Rating can't be empty", Toast.LENGTH_SHORT).show();
            } else {

                float overall = (float) ((r1.getRating() + r2.getRating() + r3.getRating()) / 3.0);
                float overallRating = (float) (Math.round(overall * 2) / 2.0);

                br.setProductID(productID);
                br.setReviewerID(userID);
                br.setComment(comment.getText().toString().trim());
                br.setPriceRating(r1.getRating());
                br.setHandlingRating(r2.getRating());
                br.setComRating(r3.getRating());
                br.setDate(date);
                br.setAverageRating(overallRating);

                //dbf.push().setValue(br);
                String recID = dbf.push().getKey();
                br.setID(recID);
                dbf.child(recID).setValue(br);

                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

                /**
                 *  Put log messages
                 */

            }
        } catch (Exception e) {

        }
    }
}