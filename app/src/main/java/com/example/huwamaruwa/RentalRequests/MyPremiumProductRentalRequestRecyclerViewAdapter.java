package com.example.huwamaruwa.RentalRequests;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.Models.RequestRentModel;
import com.example.huwamaruwa.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyPremiumProductRentalRequestRecyclerViewAdapter extends RecyclerView.Adapter<MyPremiumProductRentalRequestRecyclerViewAdapter.ViewHolder> {

    private ArrayList<RequestRentModel>request_list;
    private Context context;
    private DatabaseReference pRef;
    private Product product;
    public MyPremiumProductRentalRequestRecyclerViewAdapter(ArrayList<RequestRentModel> request_list, Context context) {
        this.request_list = request_list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rental_requests_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        if (product_list.get(position) != null){
 //          Glide.with(context).load(product_list.get(position).getImages1()).into(holder.imgMain);
//        }
        pRef = FirebaseDatabase.getInstance().getReference();
        Query query2 = pRef.child("Product").orderByChild("id").equalTo(request_list.get(position).getProductId()).limitToFirst(1);

        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                if (snapshot1.exists()){
                    for (DataSnapshot dataSnapshot1 : snapshot1.getChildren()){
                        product = new Product();
                        product.setTitle(dataSnapshot1.child("title").getValue().toString());
                        product.setPrice(Double.parseDouble(dataSnapshot1.child("price").getValue().toString()));
                        product.setDescription(dataSnapshot1.child("description").getValue().toString());
                        product.setImages1(dataSnapshot1.child("images1").getValue().toString());
                        product.setImages2(dataSnapshot1.child("images2").getValue().toString());
                        product.setImages3(dataSnapshot1.child("images3").getValue().toString());
                        product.setImages4(dataSnapshot1.child("images4").getValue().toString());
                        product.setIsPremium(Boolean.parseBoolean(dataSnapshot1.child("isPremium").getValue().toString()));

                    }
                }
                holder.txtTitle.setText(product.getTitle());
                Glide.with(context).load(product.getImages1()).into(holder.imgMain);
                holder.txtTotal.setText(String.valueOf(request_list.get(position).getTotal()));
                holder.edtDeposit.setText(String.valueOf(request_list.get(position).getInitialDeposit()));
                holder.edtAddress.setText(request_list.get(position).getAddress());
                holder.edtContactNum.setText(request_list.get(position).getContactNumber());
                holder.edtSellerContactNum.setText(product.getContactNumber());
                holder.edtSellerId.setText("Not Yet Auth");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked accept Button", Toast.LENGTH_SHORT).show();
                acceptRequest( position);
            }
        });

        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRequest(position);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateRequest(holder,position);
            }
        });
        holder.btnViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked viewProduct Button", Toast.LENGTH_SHORT).show();
                viewProduct(position);
            }
        });

        holder.txtDuration.setText(request_list.get(position).getDuration());
     //  holder.txtTitle.setText(product_list.get(position).getTitle());

    }

    private void viewProduct(int position) {

    }

    private void updateRequest(ViewHolder holder,int position) {
        pRef = FirebaseDatabase.getInstance().getReference().child("RequestRent");
        pRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(request_list.get(position).getId())){
                    try {
                        RequestRentModel requestRentModel = request_list.get(position);
                        requestRentModel.setAddress(holder.edtAddress.getText().toString().trim());
                        requestRentModel.setContactNumber(holder.edtContactNum.getText().toString().trim());
                        requestRentModel.setInitialDeposit(Double.parseDouble(holder.edtDeposit.getText().toString().trim()));
                        pRef.child(requestRentModel.getId()).setValue(requestRentModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Request updated", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }catch (Exception e){
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void deleteRequest(int position) {
        pRef = FirebaseDatabase.getInstance().getReference().child("RequestRent").child(request_list.get(position).getId());
        pRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Request Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Request Failed to Delete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void acceptRequest(int position) {
        pRef = FirebaseDatabase.getInstance().getReference().child("RequestRent");
        pRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(request_list.get(position).getId())){
                    try {
                        RequestRentModel requestRentModel = request_list.get(position);
                        requestRentModel.setStatus("Approved");
                        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference().child("RequestRent");
                        dRef.child(request_list.get(position).getId()).setValue(requestRentModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Request Approved", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Failed to Approve", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }catch (Exception e){
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return request_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMain;
        EditText edtDeposit,edtContactNum,edtAddress,edtSellerId,edtSellerContactNum;
        TextView txtTitle,txtDuration,txtTotal;
        ImageButton btnAccept,btnReject,btnEdit;
        Button btnViewProduct;
        public ViewHolder(View view) {
            super(view);

            imgMain = view.findViewById(R.id.imgRequestRent_list_main);
            txtTitle = view.findViewById(R.id.txtRequestRent_list_title);
            txtDuration = view.findViewById(R.id.txtRequestRent_list_duration);
            txtTotal = view.findViewById(R.id.txtRequestRent_list_total);

            edtDeposit = view.findViewById(R.id.edtRequestRent_list_deposit);
            edtAddress = view.findViewById(R.id.edtRequestRent_list_address);
            edtContactNum = view.findViewById(R.id.edtRequestRent_list_contact_number);
            edtSellerId = view.findViewById(R.id.edtRequestRent_list_seller_id);
            edtSellerContactNum = view.findViewById(R.id.edtRequestRent_list_seller_contactNo);

            btnAccept = view.findViewById(R.id.btnRequestRent_list_accept);
            btnReject = view.findViewById(R.id.btnRequestRent_list_reject);
            btnEdit = view.findViewById(R.id.btnRequestRent_list_edit);
            btnViewProduct = view.findViewById(R.id.btnRequestRent_list_viewProduct);

        }

    }
}