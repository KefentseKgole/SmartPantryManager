package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PantryAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private PantryAdapter adapter;
    private List<PantryItem> pantryList;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Setup Database and List
        db = new DatabaseHelper(this);
        pantryList = new ArrayList<>();

        // 2. Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerViewPantry);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PantryAdapter(pantryList, this);
        recyclerView.setAdapter(adapter);

        // 3. Setup the "+" (Floating Action Button)
        FloatingActionButton fab = findViewById(R.id.fabAddItem);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            startActivity(intent);
        });

        // 4. Setup the "What Can I Cook?" button
        Button btnSuggest = findViewById(R.id.btnSuggestRecipes);
        btnSuggest.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SuggestedRecipesActivity.class);
            startActivity(intent);
        });
    }

    // This runs every time you return to the main screen
    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems(); // Refresh the list from the database
    }

    private void loadPantryItems() {
        pantryList.clear();
        pantryList.addAll(db.getAllPantryItems());
        adapter.notifyDataSetChanged();
    }

    // When you tap on an item to EDIT it
    @Override
    public void onItemClick(PantryItem item) {
        Intent intent = new Intent(this, AddItemActivity.class);
        intent.putExtra("ITEM_ID", item.getId());
        intent.putExtra("ITEM_NAME", item.getName());
        intent.putExtra("ITEM_QUANTITY", item.getQuantity());
        intent.putExtra("ITEM_UNIT", item.getUnit());
        intent.putExtra("ITEM_EXPIRY", item.getExpiryDate());
        startActivity(intent);
    }

    // When you tap the TRASH CAN to DELETE an item
    @Override
    public void onDeleteClick(PantryItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to remove " + item.getName() + " from your pantry?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    db.deletePantryItem(item.getId());
                    loadPantryItems(); // Refresh the list
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // --- NEW CODE FOR THE SETTINGS MENU ---

    // This creates the 3-dot menu in the top right corner
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // This handles what happens when you click the Settings menu item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}