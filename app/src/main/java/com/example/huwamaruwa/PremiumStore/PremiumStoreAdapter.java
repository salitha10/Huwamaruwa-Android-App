package com.example.huwamaruwa.PremiumStore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.Home.Home_recycler_1_adapter;
import com.example.huwamaruwa.MainActivity;
import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.singleProduct.PremiumProduct;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class PremiumStoreAdapter extends RecyclerView.Adapter<PremiumStoreAdapter.ViewHolder> {
    private ArrayList<Product>product_list;
    private Context context;

    public PremiumStoreAdapter(ArrayList<Product> product_list, Context context) {
        this.product_list = product_list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_list_2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       if (product_list.get(position).getIsPremium()){
           Glide.with(context).load(product_list.get(position).getImages1()).into(holder.mainImg);
           holder.txtTitle.setText(product_list.get(position).getTitle().length() > 13?product_list.get(position).getTitle().replace(product_list.get(position).getTitle().substring(12),"..."):product_list.get(position).getTitle());
           holder.txtPrice.setText(product_list.get(position).isPerHour()?String.valueOf(product_list.get(position).getPrice()).concat(" /Per Hour"):String.valueOf(product_list.get(position).getPrice()).concat(" /Per Day"));
           if (!product_list.get(position).getIsPremium()){
               holder.doneIcon.setVisibility(View.INVISIBLE);
               holder.careIcon.setVisibility(View.INVISIBLE);
               holder.deliveryIcon.setVisibility(View.INVISIBLE);
           }
           Product product = product_list.get(position);
           SimpleDateFormat dateTimeInGMT = new SimpleDateFormat("dd");
           //Setting the time zone
           dateTimeInGMT.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
           int day = Integer.parseInt(dateTimeInGMT.format(new Date()));
           dateTimeInGMT = new SimpleDateFormat("HH");
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
                   MainActivity.userBehaviours.addBehaviour(product_list.get(position));
                   MainActivity.userBehaviours.uploadData();
                   Intent intent = new Intent(context, PremiumProduct.class);
                   intent.putExtra(Home_recycler_1_adapter.SINGLE_PRODUCT_TAG,product_list.get(position));
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent);
               }
           });
       }else {
           holder.parentLayout.setVisibility(View.GONE);
       }

    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mainImg,deliveryIcon,careIcon,doneIcon;
        TextView txtTitle,txtPrice,txtTime;
        LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainImg = itemView.findViewById(R.id.imghome_recycler_2_main);
            careIcon = itemView.findViewById(R.id.imgHome_recycler_2_care);
            deliveryIcon = itemView.findViewById(R.id.imgHome_recycler_2_delivery);
            doneIcon = itemView.findViewById(R.id.imgHome_recycler_2_done);

            txtPrice = itemView.findViewById(R.id.txtHome_recycler_2_price);
            txtTitle = itemView.findViewById(R.id.txtHome_recycler_2_title);
            txtTime = itemView.findViewById(R.id.txtHome_recycler_2_time);

            parentLayout = itemView.findViewById(R.id.home_recycler_view_2_parent_layout);
        }
    }
}
