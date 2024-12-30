package com.example.androidproject2024;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPasswordButton;
    private ProgressBar progressBar;
    private Button goBackButton;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_forget_password);

            emailEditText = findViewById(R.id.emailEditText);
            resetPasswordButton = findViewById(R.id.resetPasswordButton);
            progressBar = findViewById(R.id.progressBar);
            goBackButton = findViewById(R.id.goBack);

            mAuth = FirebaseAuth.getInstance();

            resetPasswordButton.setOnClickListener(v -> {
                try {
                    resetPassword();
                } catch (Exception e) {
                    Toast.makeText(this, "An error occurred while resetting the password.", Toast.LENGTH_SHORT).show();
                }
            });

            goBackButton.setOnClickListener(v -> {
                try {
                    startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                } catch (Exception e) {
                    Toast.makeText(this, "An error occurred while navigating back.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "An error occurred during initialization: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void resetPassword() {
        try {
            String email = emailEditText.getText().toString().trim();

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

            progressBar.setVisibility(View.VISIBLE);

            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                progressBar.setVisibility(View.GONE);
                try {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgetPasswordActivity.this, "Password reset email sent.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ForgetPasswordActivity.this, "Failed to send reset email.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ForgetPasswordActivity.this, "An error occurred while handling the result: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(ForgetPasswordActivity.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}