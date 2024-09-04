package com.example.finalpojectfoodapp.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import android.widget.Toast;


import com.example.finalpojectfoodapp.databinding.ActivitySignupBinding;


public class SignupActivity extends BaseActivity {
    ActivitySignupBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
    }

    private void setVariable() {
        binding.signupBtn.setOnClickListener(v -> {
            String email = binding.userEdt.getText().toString();
            String password = binding.passEdt.getText().toString();
            getWindow().setStatusBarColor(Color.parseColor("#FF000000"));

            if (password.length() < 6){
                Toast.makeText(SignupActivity.this,"Your password must contain al least 6 characters",Toast.LENGTH_SHORT).show();
                return;
            }
            myAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Log.i(TAG,"onComplete: ");
                    startActivity(new Intent(SignupActivity.this,MainActivity.class));
                }else{
                    Log.i(TAG,"failure: "+task.getException());
                    Toast.makeText(SignupActivity.this,"Authentication was failed",Toast.LENGTH_SHORT).show();
                }
            });
        });
        binding.textView7.setOnClickListener(v -> startActivity(new Intent(SignupActivity.this,LoginActivity.class)));
    }
}