package com.example.chatapp.Adpters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.ChatUser;
import com.example.chatapp.MassegeModel;
import com.example.chatapp.R;
import com.example.chatapp.Users;
import com.example.chatapp.databinding.RowConversationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    @NonNull

    Context context;
    ArrayList<Users>users;

    public UserAdapter(@NonNull Context context, ArrayList<Users> users ) {
        this.context = context;
        this.users = users;

    }

    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_conversation,parent,false);

        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        Users users1=users.get(position);


        Picasso.get().load(users1.getImageprofile()).placeholder(R.drawable.avatar).into(holder.binding.imageconver);
        holder.binding.txUsernameConversation.setText(users1.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChatUser.class);
                intent.putExtra("userId",users1.getUid());
                intent.putExtra("username",users1.getName());
                intent.putExtra("userprofile",users1.getImageprofile());
                context.startActivity(intent);
            }
        });



            holder.binding.txLastmsgConver.setText(users1.getAbout());


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        RowConversationBinding binding;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            binding=RowConversationBinding.bind(itemView);
        }

    }



}
