package com.example.androidproject2024;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import java.util.List;
import androidx.core.view.GravityCompat;
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
        setContentView(R.layout.activity_home);

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
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // Handle the home action
            } else if (id == R.id.nav_profile) {
                // Handle the profile action
            } else if (id == R.id.nav_about) {
                // Handle the about action
            }

            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void loadRecipes() {
        new Thread(() -> {
            try {
                List<Recipe> recipes = dbHelper.getAllRecipes();
                runOnUiThread(() -> {
                    adapter = new RecipesAdapter(recipes, this);
                    recipesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                    recipesRecyclerView.setAdapter(adapter);
                });
            } catch (Exception e) {
                Log.e("HomeActivity", "Failed to load recipes", e);
                runOnUiThread(() -> Toast.makeText(HomeActivity.this, "Failed to load recipes", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
