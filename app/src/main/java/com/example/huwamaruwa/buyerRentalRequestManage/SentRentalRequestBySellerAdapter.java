package com.example.huwamaruwa.buyerRentalRequestManage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huwamaruwa.R;

import java.util.ArrayList;

public class SentRentalRequestBySellerAdapter extends RecyclerView.Adapter<SentRentalRequestBySellerAdapter.ViewAdapter> {

    ArrayList<SentRentalRequestsByASellerModel> arraylist;

    public SentRentalRequestBySellerAdapter(ArrayList<SentRentalRequestsByASellerModel> arraylist) {
        this.arraylist = arraylist;
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

        SentRentalRequestsByASellerModel sentRentalRequestsByASellerModel = arraylist.get(position);

        holder.image.setImageResource(sentRentalRequestsByASellerModel.getImage());
        holder.productTitle.setText(sentRentalRequestsByASellerModel.getProductTitle());
        holder.category.setText(sentRentalRequestsByASellerModel.getCategory());
        holder.requiredDate.setText(sentRentalRequestsByASellerModel.getDate());
        holder.rentalPrice.setText(sentRentalRequestsByASellerModel.getRentalPrice());

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public static class ViewAdapter  extends RecyclerView.ViewHolder{

        ImageView image;
        TextView productTitle, category, requiredDate, rentalPrice;

        public ViewAdapter(@NonNull View itemView) {
            super(itemView);

            //hooks
            image = itemView.findViewById(R.id.imageViewProduct);
            productTitle = itemView.findViewById(R.id.productTitle);
            category = itemView.findViewById(R.id.category);
            requiredDate = itemView.findViewById(R.id.requiredDate1);
            rentalPrice = itemView.findViewById(R.id.rentalFee);
        }
    }
}
