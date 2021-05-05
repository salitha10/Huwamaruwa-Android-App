package com.example.huwamaruwa.ProductReviews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huwamaruwa.Models.ProductReviews;
import com.example.huwamaruwa.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OtherReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtherReviewsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseRecyclerOptions<ProductReviews> options;
    private FirebaseListAdapter<AllReviewsAdapter> adapter;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OtherReviewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OtherReviewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OtherReviewsFragment newInstance(String param1, String param2) {
        OtherReviewsFragment fragment = new OtherReviewsFragment();
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
        return inflater.inflate(R.layout.fragment_other_reviews, container, false);
    }
}