package com.example.huwamaruwa.RentalHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huwamaruwa.Models.RentalHistoryModel;
import com.example.huwamaruwa.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RentalHistoryAdapter extends RecyclerView.Adapter<RentalHistoryAdapter.ViewHolder> {
    private ArrayList<RentalHistoryModel>rental_list;
    private Context context;
    private ArrayList<String>users_list;
    private DatabaseReference dbRef;
    private String userName;

    public RentalHistoryAdapter(ArrayList<RentalHistoryModel> rental_list, Context context, ArrayList<String> users_list) {
        this.rental_list = rental_list;
        this.context = context;
        this.users_list = users_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rental_history_card_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       if (rental_list != null){
           String userId = users_list.get(position);

           dbRef = FirebaseDatabase.getInstance().getReference().child("Users");
           Query query = dbRef.orderByChild("userId").equalTo(userId);

           query.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   if (snapshot.hasChildren()){
                       for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                           userName = dataSnapshot.child("name").getValue().toString();
                       }
                   }

                   if (userName.length() > 10) {
                       holder.txtUserName.setText(userName.substring(0, 10).concat("..."));
                   } else {
                       holder.txtUserName.setText(userName);
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });


          holder.txtRentDate.setText(rental_list.get(position).getRentDate());
           holder.txtStatus.setText(rental_list.get(position).getStatus());
       }
    }

    @Override
    public int getItemCount() {
        return rental_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    TextView txtUserName,txtRentDate,txtStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txthistory_recycler_user_name);
            txtRentDate = itemView.findViewById(R.id.txthistory_recycler_rent_date);
            txtStatus = itemView.findViewById(R.id.txthistory_recycler_status);
        }
    }
}
