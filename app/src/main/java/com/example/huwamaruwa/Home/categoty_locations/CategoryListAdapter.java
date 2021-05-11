package com.example.huwamaruwa.Home.categoty_locations;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huwamaruwa.Home.GridList.GridList;
import com.example.huwamaruwa.Models.Categories;
import com.example.huwamaruwa.R;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    public static final String TAG_CATEGORY_TITLE = "com.example.categoryTitle";
    private ArrayList<Categories>categories_list;
    private Context context;

    public CategoryListAdapter(ArrayList<Categories> categories_list, Context context) {
        this.categories_list = categories_list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.catName.setText(categories_list.get(position).getCategoryTitle());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GridList.class);
                intent.putExtra(TAG_CATEGORY_TITLE,categories_list.get(position).getCategoryTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    TextView catName;
    ImageView catImg;
    LinearLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //catImg = itemView.findViewById(R.id.txtcategory_list_cat_img);
            catName = itemView.findViewById(R.id.txtcategory_list_cat_name);
            parentLayout = itemView.findViewById(R.id.txtcategory_list_cat_parentLayout);
        }
    }
}
