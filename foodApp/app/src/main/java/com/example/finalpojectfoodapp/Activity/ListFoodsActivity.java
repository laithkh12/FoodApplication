package com.example.finalpojectfoodapp.Activity;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.finalpojectfoodapp.Adapter.FoodListAdapter;
import com.example.finalpojectfoodapp.Domain.Foods;
import com.example.finalpojectfoodapp.databinding.ActivityListFoodsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListFoodsActivity extends BaseActivity {
    ActivityListFoodsBinding binding;
    private RecyclerView.Adapter adapterListFood;
    private int categoryId;
    private String categoryName;
    private String searchText;
    private boolean isSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListFoodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentExtra();
        initList();
    }



    private void initList() {
        DatabaseReference myRef = database.getReference("Foods");
        binding.progressBar.setVisibility(View.VISIBLE);
        ArrayList<Foods> list = new ArrayList<>();
        Query query;

        if (categoryId != 0) {
            query = myRef.orderByChild("CategoryId").equalTo(categoryId);
        } else {
            query = myRef;
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        Foods food = issue.getValue(Foods.class);
                        if (food != null && food.getDescription() != null && categoryName != null && food.getDescription().toLowerCase().contains(categoryName.toLowerCase())) {
                            list.add(food);
                        }
                    }
                    if (!list.isEmpty()) {
                        binding.foodListView.setLayoutManager(new GridLayoutManager(ListFoodsActivity.this, 2));
                        adapterListFood = new FoodListAdapter(list);
                        binding.foodListView.setAdapter(adapterListFood);
                    }
                    binding.progressBar.setVisibility(View.GONE);
                } else {
                    binding.progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }



    private void getIntentExtra() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("searchText")) {
                searchText = intent.getStringExtra("searchText");
                Log.d("ListFoodsActivity", "Search Text: " + searchText);
                binding.titleTxt.setText(searchText);
                binding.backBtn.setOnClickListener(v -> finish());
                return;
            }
            categoryId = intent.getIntExtra("CategoryId", 0);
            categoryName = intent.getStringExtra("CategoryName");
            isSearch = intent.getBooleanExtra("isSearch", false);
        }
        if (categoryName == null) {
            categoryName = "All Foods";
        }
        binding.titleTxt.setText(categoryName);
        binding.backBtn.setOnClickListener(v -> finish());
    }

}