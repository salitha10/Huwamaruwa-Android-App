package com.example.huwamaruwa.Delivery;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.Models.RequestRentModel;
import com.example.huwamaruwa.Models.RequestRentModel;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.customer_care.UserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder>{
    private Context mContext;
    private List<RequestRentModel> mRequestRentalModel;

    public RecAdapter(Context mContext, List<RequestRentModel> mRequestRentalModel) {
        this.mContext = mContext;
        this.mRequestRentalModel = mRequestRentalModel;
    }

    @NonNull
    public RecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.delivery_list_item, parent, false);
        return new RecAdapter.ViewHolder(view);
    }


    public void onBindViewHolder(@NonNull RecAdapter.ViewHolder holder, int position) {

        RequestRentModel recModel = mRequestRentalModel.get(position);
        String Product = recModel.getProductId();

        //Get product name from database
        DatabaseReference dbf = FirebaseDatabase.getInstance().getReference().child(Product);
        dbf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.recModelName.setText(snapshot.child("title").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AdminDeliveryScreen.class);
                intent.putExtra("recID", recModel.getId());
                intent.putExtra("sellerID", recModel.getSellerId());
                Log.d("UID", recModel.getId());
                mContext.startActivity(intent);
            }
        });
    }

    public int getItemCount() {
        return mRequestRentalModel.size();
    }


    //View Holder
    public class ViewHolder extends RecyclerView.ViewHolder{

        //Set values
        public TextView recModelName;

        public ViewHolder(View itemView){
            super(itemView);
            recModelName = itemView.findViewById(R.id.txtDelivery);
        }
    }
}
