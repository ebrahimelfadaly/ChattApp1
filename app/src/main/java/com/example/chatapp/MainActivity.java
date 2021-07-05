package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chatapp.Adpters.FragmentAdpter;
import com.example.chatapp.Adpters.UserAdapter;
import com.example.chatapp.databinding.ActivityMainBinding;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
FirebaseDatabase database;
UserAdapter adapter;
ArrayList<Users> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database=FirebaseDatabase.getInstance();
       binding.mainViewpager.setAdapter(new FragmentAdpter(getSupportFragmentManager()));
       binding.mainTaplayout.setupWithViewPager(binding.mainViewpager);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menubar,menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.searchbar:
                Toast.makeText(this,"search",Toast.LENGTH_LONG).show();
                break;
            case R.id.settingbar:
                Toast.makeText(this,"setting",Toast.LENGTH_LONG).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}