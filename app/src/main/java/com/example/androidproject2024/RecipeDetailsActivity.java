package com.example.androidproject2024;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ImageView recipeImageView = findViewById(R.id.recipeImageView);
        TextView recipeNameTextView = findViewById(R.id.recipeNameTextView);
        TextView recipeInstructionsTextView = findViewById(R.id.recipeInstructionsTextView);

        // Get data from intent
        String name = getIntent().getStringExtra("name");
        int image = getIntent().getIntExtra("img.png", 0);
        String instructions = getIntent().getStringExtra("instructions");

        // Set data
        recipeNameTextView.setText(name);
        recipeImageView.setImageResource(image);
        recipeInstructionsTextView.setText(instructions);
    }
}