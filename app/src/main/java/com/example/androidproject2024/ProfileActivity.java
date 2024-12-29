package com.example.androidproject2024;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class ProfileActivity extends AppCompatActivity {

    private EditText editTextBio, editTextLocation, editTextContactNumber;
    private TextView textViewUserName;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewUserName = findViewById(R.id.textViewUserName);
        editTextBio = findViewById(R.id.editTextBio);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextContactNumber = findViewById(R.id.editTextContactNumber);
        Button buttonSaveProfile = findViewById(R.id.buttonSaveProfile);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        if (firebaseUser != null) {
            loadUserProfile();
        }

        buttonSaveProfile.setOnClickListener(view -> saveUserProfile());

        drawer = findViewById(R.id.drawer_layout); // Check this ID in your XML
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
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
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void loadUserProfile() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            databaseReference.child(userId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult().exists()) {
                    User user = task.getResult().getValue(User.class);
                    if (user != null) {
                        String fullName = (user.name != null ? user.name : "") + " " + (user.surname != null ? user.surname : "");
                        Log.d("ProfileActivity", "User Name: " + user.name + " " + user.surname);
                        textViewUserName.setText(fullName);
                        editTextBio.setText(user.bio != null ? user.bio : "");
                        editTextLocation.setText(user.location != null ? user.location : "");
                        editTextContactNumber.setText(user.contactNumber != null ? user.contactNumber : "");
                    } else {
                        textViewUserName.setText("User details not available");
                        Log.d("ProfileActivity", "User details object is null");
                    }
                } else {
                    textViewUserName.setText("Failed to load data");
                    Log.e("ProfileActivity", "Error loading user data", task.getException());
                }
            });
        }
    }


    private void saveUserProfile() {
        String bio = editTextBio.getText().toString();
        String location = editTextLocation.getText().toString();
        String contactNumber = editTextContactNumber.getText().toString();
        User user = new User(firebaseUser.getDisplayName(), "", firebaseUser.getEmail());

        databaseReference.child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ProfileActivity.this, "Profile updated successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileActivity.this, "Failed to update profile.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}