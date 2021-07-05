package com.example.chatapp.Adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.MassegeModel;
import com.example.chatapp.R;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfig;
import com.github.pgreze.reactions.ReactionsConfigBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ChatAdapter extends RecyclerView.Adapter {

 ArrayList<MassegeModel>massegeModels;
 Context context;
 String SenderRoom;
 String ReciverRom;

    public ChatAdapter(ArrayList<MassegeModel> massegeModels, Context context,String SenderRoom,String ReciverRom) {
        this.massegeModels = massegeModels;
        this.context = context;
        this.SenderRoom=SenderRoom;
        this.ReciverRom=ReciverRom;
    }

    int SENDR_VIEW_TYPE=1;
    int RECIVER_VIEW_TYPE=2;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==SENDR_VIEW_TYPE)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender_layout,parent,false);
            return new senderViewholde(view);

        }
       else {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_reciver_layout,parent,false);
            return new ReciverViewholde(view);

        }
    }

    @Override
    public int getItemViewType(int position) {
        MassegeModel model =massegeModels.get(position);
        if (FirebaseAuth.getInstance().getUid().equals(model.getuId()))
        {
            return SENDR_VIEW_TYPE;
        }
        else {
            return RECIVER_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MassegeModel massegeModel= massegeModels.get(position);
        int[] reaction = new int[]{
                R.drawable.ic_fb_like,
                R.drawable.ic_fb_love,
                R.drawable.ic_fb_laugh,
                R.drawable.ic_fb_wow,
                R.drawable.ic_fb_sad,
                R.drawable.ic_fb_angry
        };

        ReactionsConfig config = new ReactionsConfigBuilder(context)
                .withReactions(reaction)
                .build();

        ReactionPopup popup = new ReactionPopup(context, config, (pos) -> {
            if (holder.getClass()==senderViewholde.class){
                senderViewholde viewholde = (senderViewholde)holder;
                viewholde.img_feels.setImageResource(reaction[pos]);
                viewholde.img_feels.setVisibility(View.VISIBLE);
            }
            else {
                ReciverViewholde viewholde =(ReciverViewholde)holder;
                viewholde.img_feelre.setImageResource(reaction[pos]);
                viewholde.img_feelre.setVisibility(View.VISIBLE);
            }
            massegeModel.setFeel(pos);
            FirebaseDatabase.getInstance().getReference()
                    .child("chats")
                    .child(SenderRoom)
                    .child(massegeModel.getSendrid()).setValue(massegeModel);


            FirebaseDatabase.getInstance().getReference()
                    .child("chats")
                    .child(ReciverRom)
                    .child(massegeModel.getSendrid()).setValue(massegeModel);
            return true; // true is closing popup, false is requesting a new selection

        });
        if (holder.getClass()==senderViewholde.class)
        {
            senderViewholde viewholde = (senderViewholde)holder;
            viewholde.sendermsg.setText(massegeModel.getMassege());
            if (massegeModel.getFeel()>=0) {
               // massegeModel.setFeel(reaction[(int) massegeModel.getFeel()]);
                viewholde.img_feels.setImageResource(reaction[ massegeModel.getFeel()]);
                viewholde.img_feels.setVisibility(View.VISIBLE);
            }
            else {
                viewholde.img_feels.setVisibility(View.GONE);
            }
            viewholde.sendermsg.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popup.onTouch(v,event);
                    return false;
                }
            });

        }
        else
        {
            ReciverViewholde viewholde =(ReciverViewholde)holder;
            viewholde.recivermsg.setText(massegeModel.getMassege());
            if (massegeModel.getFeel()>=0) {
                viewholde.img_feelre.setImageResource(reaction[ massegeModel.getFeel()]);
                viewholde.img_feelre.setVisibility(View.VISIBLE);
            }
            else {
                viewholde.img_feelre.setVisibility(View.GONE);
            }
            viewholde.recivermsg.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popup.onTouch(v,event);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return massegeModels.size();
    }

    public class  ReciverViewholde extends RecyclerView.ViewHolder {
        TextView recivermsg,recivertime;
        ImageView img_feelre;
        public ReciverViewholde(@NonNull View itemView) {
            super(itemView);
            recivermsg=itemView.findViewById(R.id.reciver_text);
            recivertime=itemView.findViewById(R.id.reciver_time);
            img_feelre=itemView.findViewById(R.id.feelingre);

        }
    }
    public class senderViewholde extends RecyclerView.ViewHolder {
        TextView sendermsg,sendertime;
        ImageView img_feels;
        public senderViewholde(@NonNull View itemView) {
            super(itemView);
            sendermsg=itemView.findViewById(R.id.sendr_text);
            sendertime=itemView.findViewById(R.id.sendr_time);
          img_feels=itemView.findViewById(R.id.feeling);
        }
    }

}

