package com.example.huwamaruwa.addCategory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huwamaruwa.Models.Categories;
import com.example.huwamaruwa.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.huwamaruwa.R.id.catEdit;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<Categories> cat_list;
    private Context context;
    DatabaseReference dbref;
    EditCategoryData editCategoryData;
    public static final String EXTRA_MESSAGE = "com.example.huwamaruwa.CatId";


    public CategoryAdapter(ArrayList<Categories> cat_list, Context context) {
        this.cat_list = cat_list;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView catagoryName, CatgeoryId;
        ImageButton catDelete, catEdit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            catagoryName = itemView.findViewById(R.id.catagoryName);
            CatgeoryId = itemView.findViewById(R.id.catagoryid);
            catDelete = itemView.findViewById(R.id.catDelete);
            catEdit = itemView.findViewById(R.id.catEdit);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_recycler_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Categories categories = cat_list.get(position);
        holder.catagoryName.setText(cat_list.get(position).getCategoryTitle());
        holder.CatgeoryId.setText(cat_list.get(position).getCatId());
        holder.catEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditCategoryData.class);
                intent.putExtra("catId", categories.getCatId());
                //intent.putExtra("categoryTitle",categories.getCategoryTitle());

                context.startActivity(intent);
            }
        });
        holder.catDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbref = FirebaseDatabase.getInstance().getReference().child("Category").child(cat_list.get(position).getCatId());
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
        return cat_list.size();
    }


}
