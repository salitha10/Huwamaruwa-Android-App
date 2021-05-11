package com.example.huwamaruwa.singleProduct.specification_and_feedback;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.huwamaruwa.Home.Home_recycler_1_adapter;
import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class SpecificationTab_fragment extends Fragment {
    TextView txtSpecLocation,txtSpecCategory,txtSpecContact,txtSpecDeposit,txtSpecMaxRent,txtSpecMinRent,txtSpecDes,txtSpecPrice;
    Product product;
    Button btnContact,btnChatWithSeller;
    DatabaseReference uDbRef;
    String contactNumber;
    LinearLayout contactLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_specification_tab_fragment, container, false);

        product = getActivity().getIntent().getParcelableExtra(Home_recycler_1_adapter.SINGLE_PRODUCT_TAG);

        txtSpecLocation = view.findViewById(R.id.txtproductDetails_location);
        txtSpecCategory = view.findViewById(R.id.txtproductDetails_category);
        txtSpecContact = view.findViewById(R.id.txtproductDetails_contact_number);
        txtSpecDeposit = view.findViewById(R.id.txtproductDetails_deposit);
        txtSpecMaxRent = view.findViewById(R.id.txtproductDetails_max_rental);
        txtSpecMinRent = view.findViewById(R.id.txtproductDetails_min_rental);
        txtSpecDes = view.findViewById(R.id.txtproductDetails_description);
        txtSpecPrice = view.findViewById(R.id.txtproductDetails_price);

        contactLayout = view.findViewById(R.id.specs_contact_layout);

        if (product.getIsPremium()) {
            contactLayout.setVisibility(View.GONE);
            btnChatWithSeller.setVisibility(View.GONE);
        }

        btnContact = view.findViewById(R.id.btnproductDetails_contact);
        btnChatWithSeller = view.findViewById(R.id.btnPremiumProduct_chat);
        uDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        Query query = uDbRef.orderByChild("userId").equalTo(product.getSellerId());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        contactNumber = dataSnapshot.child("phoneNo").getValue().toString();
                    }
                }
                txtSpecContact.setText(contactNumber);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //setup specification details
        txtSpecLocation.setText(product.getLocation());
        txtSpecCategory.setText("pending");

        txtSpecDeposit.setText( String.valueOf(product.getDepositPercentage()));
        txtSpecMaxRent.setText(String.valueOf(product.getMaxRentalTime()));
        txtSpecMinRent.setText(String.valueOf(product.getMinRentalTime()));
        txtSpecDes.setText(product.getDescription());
        txtSpecPrice.setText(String.valueOf(product.getPrice()));

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:".concat(contactNumber)));
                startActivity(intent);
            }
        });

        return view;
    }
}