package com.example.huwamaruwa.ProductReviews;

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

import com.example.huwamaruwa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyReviewFragment extends Fragment {


    //Variables
    TextView reviewer, comments, edit;
    RatingBar rating;
    ImageView reviewerPic;
    DatabaseReference dbf1, dbf2, dbf3;


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

        //Get data from review
        String reviewID = "-MZmdgRqUDg3QrQU1Gmm";

        dbf1 = FirebaseDatabase.getInstance().getReference().child("ProductReviews").child(reviewID);
        dbf2 = FirebaseDatabase.getInstance().getReference().child("Users");
        dbf1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    //reviewer.setText(snapshot.child("id").getValue().toString());
                    comments.setText(snapshot.child("comment").getValue().toString());
                    rating.setRating(Float.parseFloat(String.valueOf(snapshot.child("averageRating").getValue())));
                    String reviewerID = snapshot.child("reviewerID").getValue().toString();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}