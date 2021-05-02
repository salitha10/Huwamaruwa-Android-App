package com.example.huwamaruwa.Home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.R;

import java.util.ArrayList;

public class Home_recycler_1_adapter extends RecyclerView.Adapter<Home_recycler_1_adapter.ViewHolder> {
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
        holder.txtPrice.setText(product_list.get(position).getPrice());
        holder.txtTitle.setText(product_list.get(position).getTitle());
        holder.txtDescription.setText(product_list.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mainImg;
        TextView txtTitle,txtDescription,txtPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainImg = itemView.findViewById(R.id.home_recycler_1_img);
            txtPrice = itemView.findViewById(R.id.txt_home_recycler_1_price);
            txtTitle = itemView.findViewById(R.id.txt_home_recycler_1_title);
            txtDescription = itemView.findViewById(R.id.txt_home_recycler_1_description);
        }
    }
}
