package com.example.androidproject2024;

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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private ProgressBar progressBar;
    private Button loginButton, forgotPasswordButton, resendCodeButton, registerButton;

    private Button newButton;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        resendCodeButton = findViewById(R.id.resendCodeButton);
        registerButton = findViewById(R.id.registerButton);
        progressBar = findViewById(R.id.progressBar);
        newButton = findViewById(R.id.newButton);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        loginButton.setOnClickListener(v -> loginUser());
        registerButton.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, MainActivity.class)));
        forgotPasswordButton.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class)));
        resendCodeButton.setOnClickListener(v -> resendVerificationCode());

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Buttoni i ri!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

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

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required.");
            passwordEditText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        Log.d("Login", "Attempting to log in with email: " + email);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);

            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                Log.d("Login", "Login successful. Checking email verification...");

                if (user != null && user.isEmailVerified()) {
                    Log.d("Login", "Email verified. Navigating to HomeActivity.");
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Log.d("Login", "Email not verified.");
                    Toast.makeText(LoginActivity.this, "Please verify your email.", Toast.LENGTH_LONG).show();
                }

            } else {
                Log.e("Login", "Login failed: " + task.getException());
                Toast.makeText(LoginActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void resendVerificationCode(){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            if(!user.isEmailVerified()){
                Log.d("EmailVerification", "User is signed in, sending verification email...");
                user.sendEmailVerification().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("EmailVerification", "Verification email sent.");
                        Toast.makeText(LoginActivity.this, "Verification email sent.", Toast.LENGTH_LONG).show();
                    } else {
                        Log.e("EmailVerification", "Failed to send verification email.", task.getException());
                        Toast.makeText(LoginActivity.this, "Failed to send verification email.", Toast.LENGTH_LONG).show();
                    }
                });
            }else {
                Log.d("EmailVerification", "User is already verified.");
                Toast.makeText(LoginActivity.this, "Email is already verified.", Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d("EmailVerification", "No user signed in.");
            Toast.makeText(LoginActivity.this, "No user to send verification email to.", Toast.LENGTH_LONG).show();
        }
    }
}