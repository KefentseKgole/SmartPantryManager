package com.example.smartpantrymanager;
// Activity for adding and editing pantry items

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText etName, etQuantity, etUnit, etExpiry;
    private Button btnSave;
    private DatabaseHelper db;
    private int itemId = -1; // -1 means we are adding a new item, not editing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // Connect our Java code to the XML design
        etName = findViewById(R.id.etItemName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiry = findViewById(R.id.etExpiryDate);
        btnSave = findViewById(R.id.btnSaveItem);
        TextView tvTitle = findViewById(R.id.tvAddTitle);

        db = new DatabaseHelper(this);

        // Check if we opened this screen to EDIT an existing item
        if (getIntent().hasExtra("ITEM_ID")) {
            itemId = getIntent().getIntExtra("ITEM_ID", -1);
            etName.setText(getIntent().getStringExtra("ITEM_NAME"));
            etQuantity.setText(String.valueOf(getIntent().getDoubleExtra("ITEM_QUANTITY", 0)));
            etUnit.setText(getIntent().getStringExtra("ITEM_UNIT"));
            etExpiry.setText(getIntent().getStringExtra("ITEM_EXPIRY"));
            tvTitle.setText("Edit Pantry Item"); // Change the title
        }

        // What happens when you click Save
        btnSave.setOnClickListener(v -> saveItem());
    }

    private void saveItem() {
        String name = etName.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();

        // INPUT VALIDATION (Required by the assignment brief)
        if (TextUtils.isEmpty(name)) {
            etName.setError("Name is required");
            return;
        }
        if (TextUtils.isEmpty(quantityStr)) {
            etQuantity.setError("Quantity is required");
            return;
        }
        if (TextUtils.isEmpty(unit)) {
            etUnit.setError("Unit is required");
            return;
        }

        double quantity = Double.parseDouble(quantityStr);

        // Create a new PantryItem object with the data
        PantryItem item = new PantryItem(itemId, name, quantity, unit, expiry);

        if (itemId == -1) {
            // Add new item to database
            db.addPantryItem(item);
            Toast.makeText(this, "Item added to pantry", Toast.LENGTH_SHORT).show();
        } else {
            // Update existing item in database
            db.updatePantryItem(item);
            Toast.makeText(this, "Item updated", Toast.LENGTH_SHORT).show();
        }

        // Close this screen and go back to the Pantry List
        finish();
    }
}