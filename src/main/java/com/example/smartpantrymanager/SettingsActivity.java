package com.example.smartpantrymanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchAlerts;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchAlerts = findViewById(R.id.switchExpiringAlerts);

        // SharedPreferences saves simple settings (like a toggle switch) on the phone
        prefs = getSharedPreferences("SmartPantryPrefs", MODE_PRIVATE);

        // Load the saved setting (default is true)
        boolean alertsEnabled = prefs.getBoolean("alerts_enabled", true);
        switchAlerts.setChecked(alertsEnabled);

        // Save the setting when the user flips the switch
        switchAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("alerts_enabled", isChecked).apply();
        });
    }
}