package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chatapp.Adpters.ChatAdapter;
import com.example.chatapp.databinding.ActivityChatUserBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ChatUser extends AppCompatActivity {
ActivityChatUserBinding binding;
FirebaseDatabase database;
FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

       final   String senderId=auth.getUid();
        String recieveId=getIntent().getStringExtra("userId");
        String username=getIntent().getStringExtra("username");
         String profileimg=getIntent().getStringExtra("userprofile");
         binding.txNamechat.setText(username);
        Picasso.get().load(profileimg).placeholder(R.drawable.avatar).into(binding.imagechat);

        binding.imgarow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent=new Intent(ChatUser.this,MainActivity.class);
             startActivity(intent);
            }
        });
        final String sendrRoom = senderId + recieveId;
        final String reciverRoom = recieveId + senderId;

        final ArrayList<MassegeModel> massegeModels=new ArrayList<>();
        final ChatAdapter chatAdapter= new ChatAdapter(massegeModels,this,sendrRoom,reciverRoom);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        binding.recyclechatuser.setLayoutManager(linearLayoutManager);
        binding.recyclechatuser.setHasFixedSize(true);
        binding.recyclechatuser.setAdapter(chatAdapter);

            database.getReference().child("chats")
                    .child(sendrRoom)

                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            massegeModels.clear();
                            for (DataSnapshot dataSnapshot :snapshot.getChildren())
                            {
                                MassegeModel model =dataSnapshot.getValue(MassegeModel.class);
                                model.setSendrid(dataSnapshot.getKey());
                                massegeModels.add(model);

                            }
                             chatAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

         binding.imagesend.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String massege=binding.edTextpersonmassage.getText().toString();

                 final MassegeModel model=new MassegeModel(senderId,massege);

                 model.setTimestamp(new Date().getTime());

                 binding.edTextpersonmassage.setText("");
                 String randomkey=database.getReference().push().getKey();

                 database.getReference().child("chats")
                         .child(sendrRoom)

                         .child(randomkey)
                         .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                         database.getReference().child("chats")
                                 .child(reciverRoom)
                                 .child(randomkey)
                                 .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void aVoid) {


                             }
                         });


                     }
                 });
             }
         });


    }
}