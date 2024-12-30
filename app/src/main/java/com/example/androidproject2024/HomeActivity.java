package com.example.androidproject2024;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import androidx.core.view.GravityCompat;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.util.Log;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recipesRecyclerView;
    private RecipesAdapter adapter;
    private DatabaseHelper dbHelper;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_home);

            showWelcomeMessage();

            Toolbar toolbar = findViewById(R.id.toolbar); // Ensure there's a toolbar in your layout
            setSupportActionBar(toolbar);

            recipesRecyclerView = findViewById(R.id.recipesRecyclerView);
            dbHelper = new DatabaseHelper(this);
            loadRecipes();

            drawer = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            navigationView.setNavigationItemSelectedListener(item -> {
                try {
                    int id = item.getItemId();

                    if (id == R.id.nav_home) {
                        Intent intent = new Intent(this, HomeActivity.class);
                        startActivity(intent);
                    } else if (id == R.id.nav_profile) {
                        Intent intent = new Intent(this, ProfileActivity.class);
                        startActivity(intent);
                    } else if (id == R.id.nav_about) {
                        Intent intent = new Intent(this, AboutUsActivity.class);
                        startActivity(intent);
                    } else if (id == R.id.nav_login) {
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    }
                    drawer.closeDrawer(GravityCompat.START);
                } catch (Exception e) {
                    Log.e("HomeActivity", "Error handling navigation item selection", e);
                    Toast.makeText(this, "An error occurred while navigating", Toast.LENGTH_SHORT).show();
                }
                return true;
            });
        } catch (Exception e) {
            Log.e("HomeActivity", "Error during onCreate", e);
            Toast.makeText(this, "An error occurred while initializing the activity.", Toast.LENGTH_LONG).show();
        }
    }

    private void showWelcomeMessage() {
        try {
            Toast.makeText(this, "Welcome to Recipes Dairy!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("HomeActivity", "Error displaying welcome message", e);
        }
    }

    private void loadRecipes() {
        new Thread(() -> {
            try {
                List<Recipe> recipes = dbHelper.getAllRecipes();
                runOnUiThread(() -> {
                    try {
                        adapter = new RecipesAdapter(recipes, this);
                        recipesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                        recipesRecyclerView.setAdapter(adapter);
                    } catch (Exception e) {
                        Log.e("HomeActivity", "Error setting up RecyclerView", e);
                        Toast.makeText(HomeActivity.this, "Failed to display recipes", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                Log.e("HomeActivity", "Failed to load recipes", e);
                runOnUiThread(() -> Toast.makeText(HomeActivity.this, "Failed to load recipes", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}