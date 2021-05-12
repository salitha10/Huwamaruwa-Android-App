package com.example.huwamaruwa.RentalRequests.tabbedrecyclerAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.Delivery.DeliveryUserScreen;
import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.Models.RequestRentModel;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.customer_care.MessageActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProcessingReqAdapter extends RecyclerView.Adapter<ProcessingReqAdapter.ViewHolder> {
   ArrayList<RequestRentModel>processing_req_list;
   Context context;
    DatabaseReference dbRef;
    private DatabaseReference uDbRef;
    private String userName,sellerEmail,selleContact;
    String rentID;
    Product product;
    public ProcessingReqAdapter(ArrayList<RequestRentModel> processing_req_list, Context context) {
        this.processing_req_list = processing_req_list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rental_requests_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       if (!processing_req_list.isEmpty()){
           holder.btnAccept.setVisibility(View.GONE);
           holder.btnEdit.setVisibility(View.GONE);
           holder.btnReject.setVisibility(View.GONE);
           holder.btnViewProduct.setText("View Delivery");

           uDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
           Query query1 = uDbRef.orderByChild("userId").equalTo(processing_req_list.get(position).getUserId());

           query1.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   if (snapshot.hasChildren()){
                       for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                           userName = dataSnapshot.child("name").getValue().toString();
                       }
                   }
                   holder.txtUserName.setText(userName);
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });


           RequestRentModel requestRentModel = processing_req_list.get(position);
           rentID = requestRentModel.getId();

               dbRef = FirebaseDatabase.getInstance().getReference().child("Product");

               Query query =dbRef.orderByChild("id").equalTo(requestRentModel.getProductId()).limitToFirst(1);

               query.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                           product = new Product();
                           product.setTitle(dataSnapshot.child("title").getValue().toString());
                           product.setPrice(Double.parseDouble(dataSnapshot.child("price").getValue().toString()));
                           product.setDescription(dataSnapshot.child("description").getValue().toString());
                           product.setImages1(dataSnapshot.child("images1").getValue().toString());
                           product.setImages2(dataSnapshot.child("images2").getValue().toString());
                           product.setImages3(dataSnapshot.child("images3").getValue().toString());
                           product.setImages4(dataSnapshot.child("images4").getValue().toString());
                           product.setIsPremium(Boolean.parseBoolean(dataSnapshot.child("isPremium").getValue().toString()));
                       }
                       uDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
                       Query query2 = uDbRef.orderByChild("userId").equalTo(product.getSellerId());

                       query2.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               if (snapshot.hasChildren()){
                                   for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                       selleContact = dataSnapshot.child("phoneNo").getValue().toString();
                                       sellerEmail = dataSnapshot.child("email").getValue().toString();
                                   }
                               }
                               holder.edtSellerContactNum.setText(selleContact);
                               holder.edtSellerId.setText(sellerEmail);
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       });

                       holder.txtTitle.setText(product.getTitle());
                       Glide.with(context).load(product.getImages1()).into(holder.imgMain);
                       holder.txtTotal.setText(String.valueOf(requestRentModel.getTotal()));
                       holder.edtDeposit.setText(String.valueOf(requestRentModel.getInitialDeposit()));
                       holder.edtAddress.setText(requestRentModel.getAddress());
                       holder.edtContactNum.setText(requestRentModel.getContactNumber());
                       holder.txtDuration.setText(requestRentModel.getDuration());
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });

           holder.btnViewProduct.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Toast.makeText(context, "Clicked viewProduct Button", Toast.LENGTH_SHORT).show();
                   viewProduct(position);
               }
           });
       }
    }
    private void viewProduct(int position) {

    }
    @Override
    public int getItemCount() {
        return processing_req_list== null? 0 : processing_req_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgMain;
        EditText edtDeposit,edtContactNum,edtAddress,edtSellerId,edtSellerContactNum;
        TextView txtTitle,txtDuration,txtTotal,txtUserName;
        ImageButton btnAccept,btnReject,btnEdit;
        Button btnViewProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMain = itemView.findViewById(R.id.imgRequestRent_list_main);
            txtTitle = itemView.findViewById(R.id.txtRequestRent_list_title);
            txtDuration = itemView.findViewById(R.id.txtRequestRent_list_duration);
            txtTotal = itemView.findViewById(R.id.txtRequestRent_list_total);
            txtUserName = itemView.findViewById(R.id.txtRequestRent_list_userName);

            edtDeposit = itemView.findViewById(R.id.edtRequestRent_list_deposit);
            edtAddress = itemView.findViewById(R.id.edtRequestRent_list_address);
            edtContactNum = itemView.findViewById(R.id.edtRequestRent_list_contact_number);
            edtSellerId = itemView.findViewById(R.id.edtRequestRent_list_seller_id);
            edtSellerContactNum = itemView.findViewById(R.id.edtRequestRent_list_seller_contactNo);

            btnAccept = itemView.findViewById(R.id.btnRequestRent_list_accept);
            btnReject = itemView.findViewById(R.id.btnRequestRent_list_reject);
            btnEdit = itemView.findViewById(R.id.btnRequestRent_list_edit);
            btnViewProduct = itemView.findViewById(R.id.btnRequestRent_list_viewProduct);

            btnViewProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MessageActivity.class);
                    intent.putExtra("sellerId", product.getSellerId());
                    intent.putExtra("rentId", rentID);
                    context.startActivity(intent);
                }
            });
        }
    }
}
