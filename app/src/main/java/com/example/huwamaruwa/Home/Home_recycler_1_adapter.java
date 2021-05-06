package com.example.huwamaruwa.Home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.singleProduct.PremiumProduct;
import com.example.huwamaruwa.singleProduct.RequestRent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Home_recycler_1_adapter extends RecyclerView.Adapter<Home_recycler_1_adapter.ViewHolder> {
    public static final String SINGLE_PRODUCT_TAG = "com.example.huwamaruwa.singleProduct";
    ArrayList<Product> product_list;
    Context context;
    public Home_recycler_1_adapter(ArrayList<Product> product_list, Context context) {
        this.product_list = product_list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_list_1,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(product_list.get(position).getImages1()).into(holder.mainImg);
        holder.txtPrice.setText(String.valueOf(product_list.get(position).getPrice()));
        holder.txtTitle.setText(product_list.get(position).getTitle());
        holder.txtDescription.setText(product_list.get(position).getDescription());

            Product product = product_list.get(position);
        SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("dd");
        //Setting the time zone
        dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        int day = Integer.parseInt(dateTimeInGMT.format(new Date()));
        dateTimeInGMT = new SimpleDateFormat("hh");
        int hour =Integer.parseInt(dateTimeInGMT.format(new Date()));
        dateTimeInGMT = new SimpleDateFormat("mm");
        int min =Integer.parseInt(dateTimeInGMT.format(new Date()));
        dateTimeInGMT = new SimpleDateFormat("ss");
        int sec =Integer.parseInt(dateTimeInGMT.format(new Date()));
        dateTimeInGMT = new SimpleDateFormat("yyy");
        int year =Integer.parseInt(dateTimeInGMT.format(new Date()));

            if ((year - product.getDate_in_year())>0){
                holder.txtTime.setText((year - product.getDate_in_year())+" years ago");
            }else if ((day - product.getDate_in_day()) > 0){
                holder.txtTime.setText((day - product.getDate_in_day())+" days ago");
            }else if ((hour - product.getDate_in_hour()) > 0){
                holder.txtTime.setText((hour - product.getDate_in_hour())+" hours ago");
            }else if ((min - product.getDate_in_min()) > 0){
                holder.txtTime.setText((min - product.getDate_in_min())+" minutes ago");
            }else if ((sec - product.getDate_in_sec()) > 0){
                holder.txtTime.setText((sec - product.getDate_in_sec())+" seconds ago");
            }


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PremiumProduct.class);
                intent.putExtra(SINGLE_PRODUCT_TAG,product_list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mainImg;
        TextView txtTitle,txtDescription,txtPrice,txtTime;
        ConstraintLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainImg = itemView.findViewById(R.id.home_recycler_1_img);
            txtPrice = itemView.findViewById(R.id.txt_home_recycler_1_price);
            txtTitle = itemView.findViewById(R.id.txt_home_recycler_1_title);
            txtDescription = itemView.findViewById(R.id.txt_home_recycler_1_description);
            txtTime = itemView.findViewById(R.id.txt_home_recycler_1_time);
            parentLayout = itemView.findViewById(R.id.home_recycer_1_parentLayout);
        }
    }
}
