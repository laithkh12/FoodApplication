package com.example.finalpojectfoodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.example.finalpojectfoodapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class BaseActivity extends AppCompatActivity {
    FirebaseAuth myAuth;
    public String TAG = "Laith";
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        myAuth = FirebaseAuth.getInstance();

        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
    }
}