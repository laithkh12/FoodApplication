package com.example.finalpojectfoodapp.Activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.finalpojectfoodapp.Domain.Foods;
import com.example.finalpojectfoodapp.Helper.ManagmentCart;
import com.example.finalpojectfoodapp.R;
import com.example.finalpojectfoodapp.databinding.ActivityDetailBinding;

public class DetailActivity extends BaseActivity {
    ActivityDetailBinding binding;
    private Foods object;
    private int num = 1;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        managmentCart = new ManagmentCart(this);

        binding.backBtn.setOnClickListener(v -> finish());

        Glide.with(DetailActivity.this)
                .load(object.getImagePath())
                .into(binding.pic);

        binding.priceTxt.setText("$"+object.getPrice());
        binding.titleTxt.setText(object.getTitle());
        binding.descriptionTxt.setText(object.getDescription());
        binding.rateTxt.setText(object.getStar()+" Rating");
        binding.ratingBar.setRating((float) object.getStar());
        binding.totalTxt.setText((num*object.getPrice()+"$"));

        binding.plusBtn.setOnClickListener(v -> {
            num = num+1;
            binding.numTxt.setText(num+"");
            binding.totalTxt.setText("$"+(num * object.getPrice()));
        });

        binding.minusBtn.setOnClickListener(v -> {
            if(num > 1){
                num = num -1;
                binding.numTxt.setText(num + " ");
                binding.totalTxt.setText("$"+(num * object.getPrice()));
            }
        });

        binding.addBtn.setOnClickListener(v -> {
            object.setNumberInCart(num);
            managmentCart.insertFood(object);
        });
        binding.addBtn.setOnClickListener(v -> {
            object.setNumberInCart(num);
            managmentCart.insertFood(object);

            // Display a custom notification
            showNotification();
        });
    }

    private void getIntentExtra() {
        object = (Foods) getIntent().getSerializableExtra("object");
    }
    private void showNotification() {
        // Inflate the custom notification layout
        View view = LayoutInflater.from(this).inflate(R.layout.notification_layout, null);

        // Create an AlertDialog to display the notification
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        // Create the AlertDialog
        AlertDialog alertDialog = builder.create();

        // Show the AlertDialog
        alertDialog.show();
    }
}
