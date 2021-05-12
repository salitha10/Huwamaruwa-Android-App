package com.example.huwamaruwa.customer_care;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huwamaruwa.Models.Chat;
import com.example.huwamaruwa.Models.User;
import com.example.huwamaruwa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    //Declaration
    private RecyclerView rec;
    private UserAdapter userAdapter;
    private List<User> mUser;

    FirebaseUser currentUser;
    DatabaseReference dbf;

    private List<String> userList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        rec = view.findViewById(R.id.chat_recycle);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(getContext()));

        //Get current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String cUser = currentUser.getUid();

        userList = new ArrayList<>();

        dbf = FirebaseDatabase.getInstance().getReference().child("Chats");

        dbf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Chat chat = ds.getValue(Chat.class);

                    if (chat.getSender().equals(cUser)) {
                        userList.add(chat.getReceiver());
                    }

                    if (chat.getReceiver().equals(cUser)) {
                        userList.add(chat.getSender());
                    }
                }

                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void readChats() {
        mUser = new ArrayList<>();

        dbf = FirebaseDatabase.getInstance().getReference().child("Users");
        dbf.addValueEventListener(new ValueEventListener() {

            //Get Data
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUser.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);

                    //Display 1 user from chat
                    for (String id : userList) {
                        if (user.getUserId().equals(id)) {
                            if (mUser.size() != 0) {
                                for (int i = 0; i < mUser.size(); i++) {
                                    String x = mUser.get(i).getUserId();
                                    Log.d("X", mUser.get(i).getUserId());
                                    Log.d("", mUser.get(i).getUserId());
                                    if (!user.getUserId().equals(x)) {
                                        mUser.add(user);
                                    }
                                }
                            } else {
                                mUser.add(user);
                            }
                        }
                    }
                }

                userAdapter = new UserAdapter(getContext(), mUser);
                rec.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("DBCancelled", error.getMessage());
            }
        });

    }

}