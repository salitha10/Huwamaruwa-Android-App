package com.example.huwamaruwa.buyerRentalRequestManage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huwamaruwa.R;

import java.util.ArrayList;

public class AllBuyerRequestsAdapter extends RecyclerView.Adapter <AllBuyerRequestsAdapter.MyViewHolder> {

    ArrayList<BuyerRentalRequestsModel> list;
    Context context;
    public static final String EXTRA_MESSAGE = "abc";

    public AllBuyerRequestsAdapter(ArrayList<BuyerRentalRequestsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_buyer_requests_cardview,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BuyerRentalRequestsModel buyerRentalRequestsModel= list.get(position);

        holder.productName.setText(buyerRentalRequestsModel.getProductTitle());
        holder.category.setText(buyerRentalRequestsModel.getCategory());
        holder.quantity.setText(buyerRentalRequestsModel.getQuantity());
        holder.requiredDate.setText(buyerRentalRequestsModel.getRequiredDate());
        holder.budget.setText(buyerRentalRequestsModel.getBudget());
        holder.sendOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SendingOffer.class);
                intent.putExtra(EXTRA_MESSAGE,list.get(position));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView productName, category, quantity, requiredDate, budget;
        Button sendOffer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.allBuyerRequests_productName);
            category = itemView.findViewById(R.id.allBuyerRequests_category);
            quantity = itemView.findViewById(R.id.allBuyerRequests_quantity);
            requiredDate = itemView.findViewById(R.id.allBuyerRequests_requiredDate);
            budget = itemView.findViewById(R.id.allBuyerRequests_budget);
            sendOffer = itemView.findViewById(R.id.allBuyerRequests_sendOffer);

        }
    }
}
