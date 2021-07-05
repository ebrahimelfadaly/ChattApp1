package com.example.chatapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatapp.Adpters.UserAdapter;
import com.example.chatapp.R;
import com.example.chatapp.Users;
import com.example.chatapp.databinding.FragmentChatsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {

    public ChatsFragment() {
        // Required empty public constructor
    }
    FragmentChatsBinding binding;
    ArrayList<Users> users;
    FirebaseDatabase database;
    UserAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        database=FirebaseDatabase.getInstance();
        users=new ArrayList<>();
       binding=FragmentChatsBinding.inflate(inflater, container, false);
       adapter=new UserAdapter(getContext(),users);
       binding.chatrecylervie.setAdapter(adapter);
       binding.chatrecylervie.setHasFixedSize(true);
       binding.chatrecylervie.setLayoutManager(new LinearLayoutManager(getContext()));
       database.getReference().child("users").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               users.clear();
               for (DataSnapshot dataSnapshot : snapshot.getChildren())
               {

                   Users user=  dataSnapshot.getValue(Users.class);
                  user.setUid(dataSnapshot.getKey());
                   users.add(user);

               }
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

       return binding.getRoot();
    }
}