package com.example.androidproject2024;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class ProfileActivity extends AppCompatActivity {

    private EditText editTextBio, editTextLocation, editTextContactNumber;
    private TextView textViewUserName;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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