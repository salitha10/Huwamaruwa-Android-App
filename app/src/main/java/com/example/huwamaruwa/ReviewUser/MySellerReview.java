package com.example.huwamaruwa.ReviewUser;

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
import com.example.huwamaruwa.Models.SellerReview;
import com.example.huwamaruwa.Models.SellerReview;
import com.example.huwamaruwa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import com.example.huwamaruwa.Models.SellerReview;
import com.example.huwamaruwa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
 * Use the {@link MySellerReview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MySellerReview extends Fragment {

    //Variables
    TextView reviewer, comments, edit;
    RatingBar rating;
    ImageView reviewerPic, delete;
    DatabaseReference dbf1, dbf2, dbf3;
    String pID, revID;


    SellerReview pr = new SellerReview();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MySellerReview() {
        // Required empty public constructor
    }

    public static MySellerReview newInstance(String param1, String param2) {
        MySellerReview fragment = new MySellerReview();
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
        pID = getArguments().getString("ProductID");
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String cUser = user.getUid();
        //String cUser = "Lud7rSb7CyeJLQt7saQOVYTZv953";
        String proID =pID;

        dbf1 = FirebaseDatabase.getInstance().getReference().child("SellerReview");
        dbf2 = FirebaseDatabase.getInstance().getReference().child("Users");

        dbf1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {

                    //reviewer.setText(snapshot.child("id").getValue().toString());
                    SellerReview pror;
                    for(DataSnapshot ds: snapshot.getChildren()){
                        pror = ds.getValue(SellerReview.class);

                        Log.d("REVID", pror.getReviewerID());

                        if(pror.getReviewerID().equals(cUser)){
                            pr = pror;
                            getView().setVisibility(View.VISIBLE);
                            break;
                        }
                    }

                    comments.setText(pr.getComment());
                    rating.setRating(Float.parseFloat(String.valueOf(pr.getAverageRating())));
                    String reviewerID = pr.getReviewerID();
                    revID = pr.getID();

                    dbf2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            reviewer.setText(snapshot.child(reviewerID).child("name").getValue().toString());
                            String url = snapshot.child(reviewerID).child("userImage").getValue().toString();
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

    public void goToEdit() {

        Intent intent = new Intent(getContext(), EditSellerReviews.class);
        intent.putExtra("sellerID", pr.getSellerID());
        startActivity(intent);
        Log.d("quality", String.valueOf(pr.getComRating()));
    }


    public void delete() {

        dbf1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Get data from Std1

                if (snapshot.hasChildren()) {
                    dbf1.child(revID).removeValue();
                    getView().setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                } else {
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