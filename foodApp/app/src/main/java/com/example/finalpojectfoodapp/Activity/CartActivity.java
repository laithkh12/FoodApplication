package com.example.finalpojectfoodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.finalpojectfoodapp.Adapter.CartAdapter;
import com.example.finalpojectfoodapp.Helper.ManagmentCart;
import com.example.finalpojectfoodapp.R;
import com.example.finalpojectfoodapp.databinding.ActivityCartBinding;


public class CartActivity extends BaseActivity {
    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagmentCart managmentCart;
    private double tax;
    private ImageView van;
    private ImageView box;
    private ImageView check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);
        setVariable();
        calculateCart();
        initList();
        final ImageView box = findViewById(R.id.box);
        final ImageView van = findViewById(R.id.van);
        Button button2 = findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load animation
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_box);
                animation.setFillAfter(true); // Keep the final position of the animation
                box.startAnimation(animation);

                // Set animation listener to hide the box and make it appear inside the van
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Hide the box
                        box.setVisibility(View.GONE);
                        // Calculate the offset to center the box inside the van
                        int xOffset = (van.getWidth() - box.getWidth()) ;
                        int yOffset = (van.getHeight() - box.getHeight()) ;
                        // Set the position of the box inside the van
                        box.setTranslationX(van.getX() + xOffset);
                        box.setTranslationY(van.getY() + yOffset);
                        // Make the box visible inside the van
                        box.setVisibility(View.VISIBLE);
                        showNotification();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });

            }

        });

    }
    private void showNotification() {
        // Inflate the custom notification layout
        View view = LayoutInflater.from(this).inflate(R.layout.notification_layout2, null);

        // Create an AlertDialog to display the notification
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        // Create the AlertDialog
        AlertDialog alertDialog = builder.create();

        // Show the AlertDialog
        alertDialog.show();
    }

    private void initList() {
        if (managmentCart.getListCart().isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollviewCart.setVisibility(View.GONE);
        }else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollviewCart.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.cartView.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(managmentCart.getListCart(), this, () -> calculateCart());
        binding.cartView.setAdapter(adapter);
    }

    private void calculateCart() {
        double percentTax = 0.05;
        double delivery = 10;

        tax = Math.round(managmentCart.getTotalFee() * percentTax * 100.0) / 100;

        double total = Math.round((managmentCart.getTotalFee()+tax+delivery)*100)/100;
        double itemTotal = Math.round(managmentCart.getTotalFee()*100)/100;

        binding.totalFeeTxt.setText("$"+itemTotal);
        binding.taxTxt.setText("$"+tax);
        binding.deliveryTxt.setText("$"+delivery);
        binding.totalTxt.setText("$"+total);
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());
    }
}