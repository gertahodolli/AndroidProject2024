package com.example.androidproject2024;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText, surnameEditText, emailEditText, passwordEditText;
    private Button registerButton, logInButton;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        surnameEditText = findViewById(R.id.surnameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        logInButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);
        // Initialize Firebase Auth and Database references
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Set the register button click listener
        registerButton.setOnClickListener(v -> registerUser());

        logInButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, com.example.androidproject2024.LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser() {
        String name = nameEditText.getText().toString().trim();
        String surname = surnameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Name is required.");
            nameEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(surname)) {
            surnameEditText.setError("Surname is required.");
            surnameEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required.");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please enter a valid email.");
            emailEditText.requestFocus();
            return;
        }

        String passwordPattern = "^(?=.*[A-Z])(?=.*\\d).{8,}$";
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required.");
            passwordEditText.requestFocus();
            return;
        }

        if (!password.matches(passwordPattern)) {
            passwordEditText.setError("Password must be at least 8 characters, contain at least one uppercase letter, and one number.");
            passwordEditText.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);

                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        User user = new User(name, surname, email);

                        databaseReference.child(userId).setValue(user).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(verificationTask -> {
                                    if (verificationTask.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Registration successful. Please verify your email.", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Log.e("MainActivity", "Email verification failed: " + verificationTask.getException());
                                        Toast.makeText(MainActivity.this, "Failed to send verification email.", Toast.LENGTH_LONG).show();
                                    }
                                });
                            } else {
                                Log.e("MainActivity", "Database write failed: " + task1.getException());
                                Toast.makeText(MainActivity.this, "Failed to save user data. Try again.", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Log.e("MainActivity", "Authentication failed: " + task.getException());
                        Toast.makeText(MainActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


    }


}