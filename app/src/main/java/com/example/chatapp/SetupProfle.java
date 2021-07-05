package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.chatapp.databinding.ActivitySetupProfleBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SetupProfle extends AppCompatActivity {
    ActivitySetupProfleBinding binding;
    public static final int REQUST_CODE_IMAGE_PROFILE=1;
    ProgressDialog dialog;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySetupProfleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog=new ProgressDialog(this);
        dialog.setMessage("Updateing Profile..");
        dialog.setCancelable(false);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        binding.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,REQUST_CODE_IMAGE_PROFILE);

            }
        });
        binding.btnContineuProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =binding.tvNameProfile.getText().toString();
                String about=binding.tvAboutProfile.getText().toString();

                if (name.isEmpty()){
                    binding.tvNameProfile.setError("Please Enter the name");
                }
               if (about.isEmpty())
                {
                    binding.tvAboutProfile.setError("Please Enter the abut");

                }
               dialog.show();
               if (selectedimage !=null)
               {
                   StorageReference reference=storage.getReference().child("profiles").child(auth.getUid());
                   reference.putFile(selectedimage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                          if (task.isSuccessful())
                          {
                              reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                  @Override
                                  public void onSuccess(Uri uri) {
                                      String imgUri=uri.toString();
                                      String uid=auth.getUid();
                                      String name =binding.tvNameProfile.getText().toString();
                                      String about=binding.tvAboutProfile.getText().toString();
                                      String phone =auth.getCurrentUser().getPhoneNumber();
                                      Users users=new Users(uid,name,about,imgUri,phone);
                                      database.getReference().child("users")
                                              .child(auth.getUid())
                                              .setValue(users)
                                              .addOnSuccessListener(new OnSuccessListener<Void>() {
                                          @Override
                                          public void onSuccess(Void aVoid) {
                                              dialog.dismiss();
                                          Intent intent=new Intent(SetupProfle.this,MainActivity.class);
                                          startActivity(intent);
                                          finish();
                                          }
                                      });
                                  }
                              });
                          }
                       }
                   });
               }else
               {
                   String uid=auth.getUid();

                   String phone =auth.getCurrentUser().getPhoneNumber();
                   Users users=new Users(uid,name,about,"No Image",phone);
                   database.getReference().child("users").child(auth.getUid()).setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void aVoid) {
                           dialog.dismiss();
                           Intent intent=new Intent(SetupProfle.this,MainActivity.class);
                           startActivity(intent);
                           finish();
                       }
                   });
               }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data !=null){
            if (data.getData() !=null){
                binding.imgProfile.setImageURI(data.getData());
                selectedimage=data.getData();

            }
        }
    }
}