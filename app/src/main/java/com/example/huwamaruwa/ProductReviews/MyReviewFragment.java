package com.example.huwamaruwa.ProductReviews;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.Models.ProductReviews;
import com.example.huwamaruwa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.huwamaruwa.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyReviewFragment extends Fragment {

    //Variables
    TextView reviewer, comments, edit;
    RatingBar rating;
    ImageView reviewerPic, delete;
    DatabaseReference dbf1, dbf2, dbf3;

    ProductReviews pr = new ProductReviews();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment myReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyReviewFragment newInstance(String param1, String param2) {
        MyReviewFragment fragment = new MyReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_review, container, false);


        return view;

    }

    public void onResume() {
        super.onResume();

        //Initialize elements
        reviewer = (TextView) getView().findViewById(R.id.reviewerNameMyReviews);
        edit = (TextView) getView().findViewById(R.id.myReviewEdit);
        comments = (TextView) getView().findViewById(R.id.MyReviewComment);
        rating = (RatingBar) getView().findViewById(R.id.myRatingBarMyRating);
        reviewerPic = (ImageView) getView().findViewById(R.id.ReviewerImageMyReview);
        delete = (ImageView) getView().findViewById(R.id.myReviewDelete);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEdit();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Message", "Delete");

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Delete Review");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete();
                    }
                });

                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

        //Get data from review
        String reviewID = "-M_A6vgcZciyRV2X0zok";

        dbf1 = FirebaseDatabase.getInstance().getReference().child("ProductReviews").child(reviewID);
        dbf2 = FirebaseDatabase.getInstance().getReference().child("Users");
        dbf3 = FirebaseDatabase.getInstance().getReference().child("Product");

        dbf1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    //reviewer.setText(snapshot.child("id").getValue().toString());

                    //Set object values
                    pr.setComment(snapshot.child("comment").getValue().toString());
                    //pr.setBuyerID(snapshot.child("buyerID").getValue().toString());
                    pr.setProductID(snapshot.child("productID").getValue().toString());
                    pr.setID(snapshot.child("id").getValue().toString());
                    pr.setPriceRating(Float.parseFloat(snapshot.child("priceRating").getValue().toString()));
                    pr.setQualityRating(Float.parseFloat(snapshot.child("qualityRating").getValue().toString()));
                    pr.setUsabilityRating(Float.parseFloat(snapshot.child("usabilityRating").getValue().toString()));
                    pr.setAverageRating(Float.parseFloat(snapshot.child("averageRating").getValue().toString()));
                    pr.setReviewerID(snapshot.child("reviewerID").getValue().toString());

                    Log.d("quality", String.valueOf(pr.getQualityRating()));

                    comments.setText(pr.getComment());
                    rating.setRating(Float.parseFloat(String.valueOf(pr.getAverageRating())));
                    String reviewerID = pr.getReviewerID();
                    String productID = pr.getProductID();

                    dbf2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            reviewer.setText(snapshot.child(reviewerID).child("name").getValue().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    dbf3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String url = snapshot.child(productID).child("images1").getValue().toString();
                            Glide.with(getContext()).load(url).circleCrop().placeholder(R.drawable.ic_launcher_background).into(reviewerPic);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void goToEdit(){
        Intent intent = new Intent(getContext(), EditReview.class);
        intent.putExtra("MyReview", pr);
        startActivity(intent);
        Log.d("quality", String.valueOf(pr.getQualityRating()));
    }

    public void delete(){
        dbf1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Get data from Std1
                if(snapshot.hasChildren()){
                    dbf1.removeValue();
                    getView().setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "Delete Failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error", "DB Cancelled");
            }
        });
    }
}