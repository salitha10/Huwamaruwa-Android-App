package com.example.huwamaruwa.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.huwamaruwa.R;
import com.example.huwamaruwa.customer_care.MessageActivity;


public class Customer_care_fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View view = inflater.inflate(R.layout.customer_care_fragment, container, false);

        Button btnComplaint= view.findViewById(R.id.btnproductDetails_contact);
        Button btnChatWithAdmin = view.findViewById(R.id.btnPremiumProduct_chat);

        btnComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnChatWithAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}