package com.example.smartpantrymanager;
// Activity to display the full recipe details and instructions

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        TextView tvName = findViewById(R.id.tvDetailRecipeName);
        TextView tvIngredients = findViewById(R.id.tvDetailIngredients);
        TextView tvInstructions = findViewById(R.id.tvDetailInstructions);

        // Get the data passed from the SuggestedRecipesActivity
        tvName.setText(getIntent().getStringExtra("RECIPE_NAME"));
        tvIngredients.setText(getIntent().getStringExtra("RECIPE_INGREDIENTS"));
        tvInstructions.setText(getIntent().getStringExtra("RECIPE_INSTRUCTIONS"));
    }
}
