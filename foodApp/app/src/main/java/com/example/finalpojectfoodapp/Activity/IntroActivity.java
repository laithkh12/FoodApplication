package com.example.finalpojectfoodapp.Activity;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.finalpojectfoodapp.databinding.ActivityIntroBinding;


public class IntroActivity extends BaseActivity {
    ActivityIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
        getWindow().setStatusBarColor(Color.parseColor("#FF000000"));
    }

    private void setVariable() {
        binding.loginBtn.setOnClickListener(v -> {
            if (myAuth.getCurrentUser() != null){
                startActivity(new Intent(IntroActivity.this,MainActivity.class));
            }else{
                startActivity(new Intent(IntroActivity.this,LoginActivity.class));
            }
        });
        binding.signupBtn.setOnClickListener(v -> startActivity(new Intent(IntroActivity.this,SignupActivity.class)));
    }
}