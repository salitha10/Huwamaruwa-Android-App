package com.example.huwamaruwa.MypostAD;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.huwamaruwa.Models.Product;
import com.example.huwamaruwa.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class addPostAdapter extends RecyclerView.Adapter<addPostAdapter.ViewHolder> {
    RecyclerView recyclerView;
    Context context;
    DatabaseReference dbref;
    ArrayList<Product> myPost_list;
    AlertDialog.Builder builder;



    public addPostAdapter(Context context, ArrayList<Product> myPost_list) {
        this.context = context;
        this.myPost_list = myPost_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_post_add_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = myPost_list.get(position);
        Glide.with(context).load(myPost_list.get(position).getImages1()).into(holder.images1);
        holder.post_product_name.setText(myPost_list.get(position).getTitle());
        holder.post_product_useId.setText(myPost_list.get(position).getSellerId());
        holder.post_product_price.setText(myPost_list.get(position).getId());

        //Toast.makeText(context, "id, Toast.LENGTH_SHORT).show();
        holder.btnEditpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, EditMyAd.class);
                intent.putExtra("id",product.getId());

                context.startActivity(intent);
            }
        });
        holder.btnDeletepost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dbref = FirebaseDatabase.getInstance().getReference().child("Product").child(myPost_list.get(position).getId());
                dbref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Successfully Deleted..", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Delete Unsuccessfully..", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });




    }

    @Override
    public int getItemCount() {
        return myPost_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView post_product_name, post_product_useId, post_product_price;
        Button btnEditpost, btnDeletepost;
        ImageView images1;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            images1 = itemView.findViewById(R.id.imgProduct1);
            post_product_name = itemView.findViewById(R.id.post_product_name);
            post_product_useId = itemView.findViewById(R.id.post_product_useId);
            post_product_price = itemView.findViewById(R.id.post_product_price);
            btnEditpost = itemView.findViewById(R.id.btnEditpost);
            btnDeletepost=itemView.findViewById(R.id. btnDeletepost);


        }
    }
}
