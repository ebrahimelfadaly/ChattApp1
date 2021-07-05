package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.chatapp.databinding.ActivityAceptVerifyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Acept_Activity_Verify extends AppCompatActivity {
    ActivityAceptVerifyBinding binding;
      FirebaseAuth auth;
      String VerificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAceptVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        String PhoneNumber=getIntent().getStringExtra("phonenumber");

        binding.textmobileVerify.setText(String.format("+20-%s",PhoneNumber));

         setupinput();
         VerificationId=getIntent().getStringExtra("verificationId");
         binding.btnVerify.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (binding.edInc1.getText().toString().trim().isEmpty()
                         ||binding.edInc2.getText().toString().trim().isEmpty()
                         ||binding.edInc3.getText().toString().trim().isEmpty()
                         ||binding.edInc4.getText().toString().trim().isEmpty()
                         ||binding.edInc5.getText().toString().trim().isEmpty()
                         ||binding.edInc6.getText().toString().trim().isEmpty())
                 {
                     Toast.makeText(Acept_Activity_Verify.this,"Please Enter the code verfy",Toast.LENGTH_LONG).show();
                     return;
                 }
                 String code=binding.edInc1.getText().toString()+
                         binding.edInc2.getText().toString()+
                         binding.edInc3.getText().toString()+
                         binding.edInc4.getText().toString()+
                         binding.edInc5.getText().toString()+
                         binding.edInc6.getText().toString();
                 if (VerificationId!=null)
                 {
                     binding.progrspar.setVisibility(View.GONE);
                     binding.btnVerify.setVisibility(View.INVISIBLE);
                     PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(VerificationId,code);
                     FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).
                             addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {
                                     binding.progrspar.setVisibility(View.GONE);
                                     binding.btnVerify.setVisibility(View.VISIBLE);
                                     if (task.isSuccessful())
                                     {
                                         Intent intent=new Intent(getApplicationContext(),SetupProfle.class);
                                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                         startActivity(intent);
                                         finishAffinity();
                                     }
                                     else {
                                         Toast.makeText(Acept_Activity_Verify.this,"the code Enteried is vailad",Toast.LENGTH_LONG).show();

                                     }

                                 }
                             });
                 }



             }
         });
         binding.textresendOTP.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 PhoneAuthProvider.getInstance().verifyPhoneNumber(
                         "+20" + getIntent().getStringExtra("phonenumber"),
                         60,
                         TimeUnit.SECONDS,
                         Acept_Activity_Verify.this,
                         new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                             @Override
                             public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                             }

                             @Override
                             public void onVerificationFailed(@NonNull FirebaseException e) {

                                 Toast.makeText(Acept_Activity_Verify.this, e.getMessage(), Toast.LENGTH_LONG).show();
                             }

                             @Override
                             public void onCodeSent(@NonNull String newverifcationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                 VerificationId=newverifcationId;
                                 Toast.makeText(Acept_Activity_Verify.this,"the code sent",Toast.LENGTH_LONG).show();
                             }
                         });
             }
         });

    }
    private void setupinput(){
        binding.edInc1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              if (!s.toString().trim().isEmpty())
              {
                  binding.edInc2.requestFocus();
              }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.edInc2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                {
                    binding.edInc3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.edInc3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                {
                    binding.edInc4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.edInc4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                {
                    binding.edInc5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.edInc5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty())
                {
                    binding.edInc6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}