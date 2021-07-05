package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chatapp.databinding.ActivityPhoneNumberBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class phone_number extends AppCompatActivity {
ActivityPhoneNumberBinding binding;
FirebaseAuth auth;
public final static int PREMATION_REQUSET_CONTANT=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPhoneNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth =FirebaseAuth.getInstance();

        if (auth.getCurrentUser()!=null)
        {
            Intent intent=new Intent(phone_number.this,MainActivity.class);
            startActivity(intent);
            finish();
        }




        getSupportActionBar().hide();
        binding.edPhoneNumber.requestFocus();


        binding.btnPhoneContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edPhoneNumber.getText().toString().trim().isEmpty()){

                    Toast.makeText(phone_number.this,"Enter Your Phone Number",Toast.LENGTH_LONG).show();
                    return;
                }

                else {
                    binding.progpphone.setVisibility(View.VISIBLE);
                    binding.btnPhoneContinue.setVisibility(View.INVISIBLE);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+2" + binding.edPhoneNumber.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            phone_number.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    binding.progpphone.setVisibility(View.GONE);
                                    binding.btnPhoneContinue.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    binding.progpphone.setVisibility(View.GONE);
                                    binding.btnPhoneContinue.setVisibility(View.VISIBLE);
                                    Toast.makeText(phone_number.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verifcationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    binding.progpphone.setVisibility(View.GONE);
                                    binding.btnPhoneContinue.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(getApplicationContext(), Acept_Activity_Verify.class);
                                    intent.putExtra("phonenumber", binding.edPhoneNumber.getText().toString());
                                    intent.putExtra("verificationId", verifcationId);
                                    startActivity(intent);
                                }
                            });
                }
            }
        });
    }


   
}