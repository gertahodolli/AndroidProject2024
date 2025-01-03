package com.example.androidproject2024;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        try {
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

            buttonSaveProfile.setOnClickListener(view -> {
                try {
                    saveUserProfile();
                } catch (Exception e) {
                    Log.e("ProfileActivity", "Error saving profile", e);
                    Toast.makeText(ProfileActivity.this, "An error occurred while saving the profile.", Toast.LENGTH_SHORT).show();
                }
            });

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
                    Log.e("ProfileActivity", "Error handling navigation item selection", e);
                    Toast.makeText(this, "An error occurred while navigating", Toast.LENGTH_SHORT).show();
                }
                return true;
            });
        } catch (Exception e) {
            Log.e("ProfileActivity", "Error during onCreate", e);
            Toast.makeText(this, "An error occurred while initializing the activity.", Toast.LENGTH_LONG).show();
        }
    }

    private void loadUserProfile() {
        try {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (firebaseUser != null) {
                String userId = firebaseUser.getUid();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            if (dataSnapshot.exists()) {
                                User user = dataSnapshot.getValue(User.class);
                                if (user != null) {
                                    Log.d("ProfileActivity", "User data loaded: " + user.name + " " + user.surname);
                                    updateUIWithUserInfo(user);
                                    saveUserInfoLocally(user);
                                } else {
                                    Log.d("ProfileActivity", "User data is null after fetch.");
                                }
                            } else {
                                Log.d("ProfileActivity", "No data exists for user: " + userId);
                                loadUserInfoFromLocal();
                            }
                        } catch (Exception e) {
                            Log.e("ProfileActivity", "Error processing user data", e);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("ProfileActivity", "Database error: " + databaseError.getMessage());
                        loadUserInfoFromLocal();
                    }
                });
            } else {
                Log.d("ProfileActivity", "FirebaseUser is null.");
                loadUserInfoFromLocal();
            }
        } catch (Exception e) {
            Log.e("ProfileActivity", "Error loading user profile", e);
        }
    }

    private void saveUserProfile() {
        try {
            String bio = editTextBio.getText().toString();
            String location = editTextLocation.getText().toString();
            String contactNumber = editTextContactNumber.getText().toString();

            if (firebaseUser != null) {
                User user = new User(firebaseUser.getDisplayName(), "", firebaseUser.getEmail());
                user.bio = bio;
                user.location = location;
                user.contactNumber = contactNumber;

                databaseReference.child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(task -> {
                    try {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Profile updated successfully.", Toast.LENGTH_SHORT).show();
                            saveUserInfoLocally(user);
                        } else {
                            Toast.makeText(ProfileActivity.this, "Failed to update profile.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e("ProfileActivity", "Error updating profile", e);
                    }
                });
            } else {
                Toast.makeText(ProfileActivity.this, "No user logged in.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("ProfileActivity", "Error in saveUserProfile", e);
            Toast.makeText(this, "An error occurred while saving the profile.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserInfoLocally(User user) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", user.name);
            editor.putString("surname", user.surname);
            editor.putString("bio", user.bio);
            editor.putString("location", user.location);
            editor.putString("contactNumber", user.contactNumber);
            editor.apply();
        } catch (Exception e) {
            Log.e("ProfileActivity", "Error saving user info locally", e);
        }
    }

    private void loadUserInfoFromLocal() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            User user = new User();
            user.name = sharedPreferences.getString("name", "");
            user.surname = sharedPreferences.getString("surname", "");
            user.bio = sharedPreferences.getString("bio", "");
            user.location = sharedPreferences.getString("location", "");
            user.contactNumber = sharedPreferences.getString("contactNumber", "");
            updateUIWithUserInfo(user);
        } catch (Exception e) {
            Log.e("ProfileActivity", "Error loading user info locally", e);
        }
    }

    private void updateUIWithUserInfo(User user) {
        try {
            String fullName = (user.name != null ? user.name : "") + " " + (user.surname != null ? user.surname : "");
            textViewUserName.setText(fullName);
            editTextBio.setText(user.bio != null ? user.bio : "");
            editTextLocation.setText(user.location != null ? user.location : "");
            editTextContactNumber.setText(user.contactNumber != null ? user.contactNumber : "");
        } catch (Exception e) {
            Log.e("ProfileActivity", "Error updating UI with user info", e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            loadUserProfile(); // Or directly call loadUserInfoFromLocal();
        } catch (Exception e) {
            Log.e("ProfileActivity", "Error during onResume", e);
        }
    }
}