package com.example.huwamaruwa.addProduct;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huwamaruwa.R;

import java.util.ArrayList;

public class AddNewItemAdapter extends RecyclerView.Adapter<AddNewItemAdapter.ViewHolder> {
    Context context;
    ArrayList<Uri>prev_img_list = new ArrayList<>();

    public AddNewItemAdapter(Context context, ArrayList<Uri> prev_img_list) {
        this.context = context;
        this.prev_img_list = prev_img_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.imgview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.prevImg.setImageURI(prev_img_list.get(position));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return prev_img_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView prevImg;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prevImg = itemView.findViewById(R.id.prev_img);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
