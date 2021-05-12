package com.example.huwamaruwa.singleProduct.specification_and_feedback;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.huwamaruwa.Home.Home_recycler_1_adapter;
import com.example.huwamaruwa.MainActivity;
import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.Models.ProductReviews;
import com.example.huwamaruwa.ProductReviews.AddProductReview;
import com.example.huwamaruwa.ProductReviews.AllProductReviews;
import com.example.huwamaruwa.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedbackTab_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedbackTab_fragment extends Fragment {

    Button addReview, showReviews;
    Product pd;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeedbackTab_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment feedbackTab_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedbackTab_fragment newInstance(String param1, String param2) {
        FeedbackTab_fragment fragment = new FeedbackTab_fragment();
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

        View view =inflater.inflate(R.layout.fragment_feedback_tab_fragment, container, false);
        //Get product ID
        pd = getActivity().getIntent().getParcelableExtra(Home_recycler_1_adapter.SINGLE_PRODUCT_TAG);

        addReview = (Button)view.findViewById(R.id.AddReview);
        showReviews = (Button)view.findViewById(R.id.ViewReviews);
        String productID = pd.getId();

        //Add button click
        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddProductReview.class);
                intent.putExtra("ProductID", productID);
                startActivity(intent);
            }
        });

        showReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AllProductReviews.class);
                intent.putExtra("ProductID", productID);
                startActivity(intent);
            }
        });


        return view;
    }


}