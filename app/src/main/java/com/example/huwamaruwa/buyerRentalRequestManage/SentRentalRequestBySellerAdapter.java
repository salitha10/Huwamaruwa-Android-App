package com.example.huwamaruwa.buyerRentalRequestManage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.Models.SendingOffersModel;
import com.example.huwamaruwa.ProfileView;
import com.example.huwamaruwa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SentRentalRequestBySellerAdapter extends RecyclerView.Adapter<SentRentalRequestBySellerAdapter.ViewAdapter> {

    ArrayList<SendingOffersModel> list;
    Context context;
    DatabaseReference dbf;
    public static final String EXTRA_MESSAGE1 = "aaa";
    public static final String EXTRA_MESSAGE2 = "aca";

    public SentRentalRequestBySellerAdapter(ArrayList<SendingOffersModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_rental_request_cardview,parent,false);
        ViewAdapter viewAdapter = new ViewAdapter(view);
        return viewAdapter;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter holder, int position) {

        SendingOffersModel sendingOffersModel = list.get(position);

        Glide.with(context).load(sendingOffersModel.getProductImage()).into(holder.image);
        holder.productTitle.setText(sendingOffersModel.getProductName());
        holder.requestId.setText(sendingOffersModel.getProductRequestId());
        holder.quantity.setText(sendingOffersModel.getQuantity());
        holder.rentalPrice.setText(sendingOffersModel.getRental());
        holder.deleteDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbf = FirebaseDatabase.getInstance().getReference().child("SentOffersForProductRequest");
                dbf.keepSynced(true);
                dbf.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(sendingOffersModel.getProductRequestId())){
                            dbf = FirebaseDatabase.getInstance().getReference().child("SentOffersForProductRequest").child(sendingOffersModel.getProductRequestId());
                            dbf.removeValue();

                            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(context,SentRentalRequestBySeller.class);
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        holder.updateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,EditSingleSellerRequest.class);
                intent.putExtra(EXTRA_MESSAGE1,list.get(position));
                context.startActivity(intent);
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ProductImageFullScreen.class);
                i.putExtra(EXTRA_MESSAGE2, sendingOffersModel.getProductImage() );
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewAdapter  extends RecyclerView.ViewHolder{

        ImageView image;
        TextView productTitle, quantity, rentalPrice, requestId;
        Button updateDetails, deleteDetails;

        public ViewAdapter(@NonNull View itemView) {
            super(itemView);

            //hooks
            image = itemView.findViewById(R.id.imageViewProduct);
            productTitle = itemView.findViewById(R.id.SellerRequests_productTitle);
            quantity = itemView.findViewById(R.id.SellerRequests_Quantity);
            rentalPrice = itemView.findViewById(R.id.SellerRequests_rentalFee);
            requestId = itemView.findViewById(R.id.SellerRequests_requestId);
            updateDetails = itemView.findViewById(R.id.btnSellerRequests_edit);
            deleteDetails = itemView.findViewById(R.id.btnSellerRequests_delete);

        }
    }
}
