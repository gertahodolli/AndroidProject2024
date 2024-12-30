package com.example.androidproject2024;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class AboutUsActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_about_us);

            // Set up the toolbar
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            // Set up the navigation drawer
            drawer = findViewById(R.id.main); // ID of the main ConstraintLayout
            navigationView = findViewById(R.id.nav_view);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            // Handle navigation menu item selection
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
                        // Already in AboutUsActivity, do nothing
                        Toast.makeText(this, "You are already on the About Us page.", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.nav_login) {
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    }
                    drawer.closeDrawer(GravityCompat.START);
                } catch (Exception e) {
                    Log.e("AboutUsActivity", "Error handling navigation item selection", e);
                    Toast.makeText(this, "An error occurred while navigating", Toast.LENGTH_SHORT).show();
                }
                return true;
            });
        } catch (Exception e) {
            Log.e("AboutUsActivity", "Error during onCreate", e);
            Toast.makeText(this, "An error occurred while initializing the activity.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
