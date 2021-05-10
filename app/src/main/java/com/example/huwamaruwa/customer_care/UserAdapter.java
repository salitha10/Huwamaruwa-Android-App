package com.example.huwamaruwa.customer_care;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.huwamaruwa.Models.User;
import com.example.huwamaruwa.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Context mContext;
    private List<User> mUser;

    public UserAdapter(Context mContext, List<User> mUser) {
        this.mContext = mContext;
        this.mUser = mUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = mUser.get(position);
        holder.userName.setText(user.getName());

        if(user.getUserImage().equals("default")){

        }
        else{
            Glide.with(mContext).load(user.getUserImage()).circleCrop().into(holder.proPic);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userId", user.getUserId());
                Log.d("UID", user.getUserId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }


    //View Holder
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView userName;
        public ImageView proPic;

        public ViewHolder(View itemView){
            super(itemView);
            userName = itemView.findViewById(R.id.userNameChat);
            proPic = itemView.findViewById(R.id.user_pic);
        }
    }
}
