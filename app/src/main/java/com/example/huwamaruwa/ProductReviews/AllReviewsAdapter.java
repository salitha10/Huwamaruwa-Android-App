package com.example.huwamaruwa.ProductReviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.huwamaruwa.R;
import com.example.huwamaruwa.buyerRentalRequestManage.SentRentalRequestBySellerAdapter;
import com.example.huwamaruwa.buyerRentalRequestManage.SentRentalRequestsByASellerModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.example.huwamaruwa.Models.ProductReviews;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllReviewsAdapter extends RecyclerView.Adapter<AllReviewsAdapter.ViewAdapter> {

    ArrayList<ProductReviews> arraylist;
    Context context;

    public AllReviewsAdapter(ArrayList<ProductReviews> arraylist, Context context) {
        this.arraylist = arraylist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.others_reviews_card_view,parent,false);
        ViewAdapter viewAdapter = new ViewAdapter(view);
        return viewAdapter;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter holder, int position) {

        ProductReviews model = arraylist.get(position);

        String buyerID = model.getReviewerID();
        DatabaseReference dbfBuyer = FirebaseDatabase.getInstance().getReference().child("Users").child(buyerID);
        dbfBuyer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Set vales
                holder.reviewer.setText(snapshot.child("name").getValue().toString());
                String url = snapshot.child("userImage").getValue().toString();
                Glide.with(holder.itemView.getContext()).load(url).circleCrop().placeholder(R.drawable.ic_launcher_background).into(holder.reviewerPic);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //Set data
        holder.comments.setText(model.getComment());
        holder.rating.setRating((float)model.getAverageRating());
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public static class ViewAdapter  extends RecyclerView.ViewHolder{

        TextView reviewer, comments;
        RatingBar rating;
        ImageView reviewerPic;

        public ViewAdapter(@NonNull View itemView) {
            super(itemView);

            //Hooks
            reviewer = (TextView) itemView.findViewById(R.id.OthersReviewsName);
            comments = (TextView) itemView.findViewById(R.id.othersreviewsDescription);
            rating = (RatingBar) itemView.findViewById(R.id.othersReviewsRatingBar);
            reviewerPic = (ImageView) itemView.findViewById(R.id.othersReviewsBuyerImage);
        }
    }

}