package com.example.androidproject2024;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recipesRecyclerView;
    private RecipesAdapter adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recipesRecyclerView = findViewById(R.id.recipesRecyclerView);
        dbHelper = new DatabaseHelper(this);
        loadRecipes();
    }

    private void loadRecipes() {
        List<Recipe> recipes = dbHelper.getAllRecipes();
        adapter = new RecipesAdapter(recipes, this);
        recipesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Set grid layout with 2 columns
        recipesRecyclerView.setAdapter(adapter);
    }
}
