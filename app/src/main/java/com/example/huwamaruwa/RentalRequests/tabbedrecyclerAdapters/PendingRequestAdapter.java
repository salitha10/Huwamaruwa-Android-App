package com.example.huwamaruwa.RentalRequests.tabbedrecyclerAdapters;

import android.content.Context;
import android.util.Log;
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
import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.Models.RequestRentModel;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.RentalRequests.MyPremiumProductRentalRequestRecyclerViewAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PendingRequestAdapter extends RecyclerView.Adapter<PendingRequestAdapter.ViewHolder> {
    private static final String TAG = "PendingRequestAdapter";
    private DatabaseReference dbRef;
    private DatabaseReference uDbRef;
    private ArrayList<RequestRentModel> pending_req_list;
    private Context context;
    private Product product;
    private String userName,sellerEmail,selleContact;
    public PendingRequestAdapter(ArrayList<RequestRentModel> pending_req_list, Context context) {
        this.pending_req_list = pending_req_list;
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
            if (!pending_req_list.isEmpty()){

                uDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
                Query query1 = uDbRef.orderByChild("userId").equalTo(pending_req_list.get(position).getUserId());

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




                RequestRentModel requestRentModel = pending_req_list.get(position);

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
                                product.setSellerId(dataSnapshot.child("sellerId").getValue().toString());
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

            }
    }
    private void viewProduct(int position) {

    }
//update Request Details
    private void updateRequest(ViewHolder holder, int position) {
        dbRef = FirebaseDatabase.getInstance().getReference().child("RequestRent"); //get Database Reference
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(pending_req_list.get(position).getId())){ //check whether child is exist or not
                    try {
                        RequestRentModel requestRentModel = pending_req_list.get(position);
                        requestRentModel.setAddress(holder.edtAddress.getText().toString().trim());
                        requestRentModel.setContactNumber(holder.edtContactNum.getText().toString().trim());
                        requestRentModel.setInitialDeposit(Double.parseDouble(holder.edtDeposit.getText().toString().trim()));
                        dbRef.child(requestRentModel.getId()).setValue(requestRentModel).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                        Log.e(TAG,"Pending Req: "+e.getMessage());
                        //Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //delete request from database
    private void deleteRequest(int position) {
        dbRef = FirebaseDatabase.getInstance().getReference().child("RequestRent").child(pending_req_list.get(position).getId());
        dbRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Request Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Request Failed to Delete", Toast.LENGTH_SHORT).show();
                Log.e(TAG,"Pending Req: Delete fail");
            }
        });
    }
    private void acceptRequest(int position) {
        dbRef = FirebaseDatabase.getInstance().getReference().child("RequestRent");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(pending_req_list.get(position).getId())){
                    try {
                        RequestRentModel requestRentModel = pending_req_list.get(position);
                        requestRentModel.setStatus("Approved");
                        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference().child("RequestRent");
                        dRef.child(pending_req_list.get(position).getId()).setValue(requestRentModel).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        return pending_req_list== null? 0 : pending_req_list.size();
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
        }
    }
}
