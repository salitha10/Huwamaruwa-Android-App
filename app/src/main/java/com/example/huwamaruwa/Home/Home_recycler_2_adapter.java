package com.example.huwamaruwa.Home;

import android.content.Context;
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

public class Home_recycler_2_adapter extends RecyclerView.Adapter<Home_recycler_2_adapter.ViewHolder> {
    private ArrayList<Product>product_list;
    private Context context;

    public Home_recycler_2_adapter(ArrayList<Product> product_list, Context context) {
        this.product_list = product_list;
        this.context = context;
    }

    @NonNull
    @Override
    public Home_recycler_2_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_list_2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Home_recycler_2_adapter.ViewHolder holder, int position) {
        String rs = "RS. ";
        Glide.with(context).load(product_list.get(position).getImages1()).into(holder.mainImg);
        holder.txtTitle.setText(product_list.get(position).getTitle());
        holder.txtPrice.setText(rs.concat(product_list.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mainImg,deliveryIcon,careIcon,doneIcon;
        TextView txtTitle,txtPrice,txtTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainImg = itemView.findViewById(R.id.imghome_recycler_2_main);
            careIcon = itemView.findViewById(R.id.imgHome_recycler_2_care);
            deliveryIcon = itemView.findViewById(R.id.imgHome_recycler_2_delivery);
            doneIcon = itemView.findViewById(R.id.imgHome_recycler_2_done);

            txtPrice = itemView.findViewById(R.id.txtHome_recycler_2_price);
            txtTitle = itemView.findViewById(R.id.txtHome_recycler_2_title);
            txtTime = itemView.findViewById(R.id.txtHome_recycler_2_time);
        }
    }
}
