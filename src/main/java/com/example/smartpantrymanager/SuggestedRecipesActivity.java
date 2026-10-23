package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private List<Recipe> suggestedRecipes;
    private TextView tvEmptyState;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        db = new DatabaseHelper(this);
        suggestedRecipes = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerViewRecipes);
        tvEmptyState = findViewById(R.id.tvEmptyState);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipeAdapter(suggestedRecipes, this);
        recyclerView.setAdapter(adapter);

        // Run the strict matching logic
        findMatchingRecipes();
    }

    private void findMatchingRecipes() {
        // 1. Get everything from the database
        List<PantryItem> pantry = db.getAllPantryItems();
        List<Recipe> allRecipes = db.getAllRecipes();

        // 2. Clear old suggestions
        suggestedRecipes.clear();

        // 3. THE STRICT-MATCHING ALGORITHM
        for (Recipe recipe : allRecipes) {
            // Split the recipe's required ingredients (e.g., "eggs, butter, milk" -> ["eggs", "butter", "milk"])
            String[] requiredIngredients = recipe.getRequiredIngredients().split(",");
            boolean hasAllIngredients = true;

            for (String required : requiredIngredients) {
                String reqClean = required.trim().toLowerCase(); // Clean up spaces and capitalization
                boolean foundInPantry = false;

                // Check if this specific ingredient is in the user's pantry
                for (PantryItem item : pantry) {
                    String pantryItemName = item.getName().trim().toLowerCase();

                    // Check if the pantry item name matches the required ingredient
                    // (e.g., pantry has "eggs", recipe needs "eggs")
                    if (pantryItemName.contains(reqClean) || reqClean.contains(pantryItemName)) {
                        foundInPantry = true;
                        break; // We found it, stop searching the pantry for this ingredient
                    }
                }

                // If even ONE ingredient is missing, the whole recipe fails.
                if (!foundInPantry) {
                    hasAllIngredients = false;
                    break; // Stop checking this recipe
                }
            }

            // If the loop finishes and hasAllIngredients is still true, add it to the list!
            if (hasAllIngredients) {
                suggestedRecipes.add(recipe);
            }
        }

        // 4. Show the "Empty State" if no recipes match
        if (suggestedRecipes.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        adapter.notifyDataSetChanged();
    }

    // When a user clicks on a suggested recipe, open the detail screen
    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra("RECIPE_NAME", recipe.getName());
        intent.putExtra("RECIPE_INGREDIENTS", recipe.getRequiredIngredients());
        intent.putExtra("RECIPE_INSTRUCTIONS", recipe.getInstructions());
        startActivity(intent);
    }
}