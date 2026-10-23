package com.example.smartpantrymanager;
// This class manages the SQLite database creation and CRUD operations

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_PANTRY = "pantry";
    private static final String TABLE_RECIPES = "recipes";

    // Pantry Columns
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_UNIT = "unit";
    private static final String KEY_EXPIRY = "expiry_date";

    // Recipe Columns
    private static final String KEY_RECIPE_ID = "id";
    private static final String KEY_RECIPE_NAME = "name";
    private static final String KEY_RECIPE_INGREDIENTS = "ingredients";
    private static final String KEY_RECIPE_INSTRUCTIONS = "instructions";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Pantry Table
        String CREATE_PANTRY_TABLE = "CREATE TABLE " + TABLE_PANTRY + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT,"
                + KEY_QUANTITY + " REAL,"
                + KEY_UNIT + " TEXT,"
                + KEY_EXPIRY + " TEXT" + ")";
        db.execSQL(CREATE_PANTRY_TABLE);

        // Create Recipes Table
        String CREATE_RECIPE_TABLE = "CREATE TABLE " + TABLE_RECIPES + "("
                + KEY_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_RECIPE_NAME + " TEXT,"
                + KEY_RECIPE_INGREDIENTS + " TEXT,"
                + KEY_RECIPE_INSTRUCTIONS + " TEXT" + ")";
        db.execSQL(CREATE_RECIPE_TABLE);

        // Load the 18 recipes into the database
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    // --- PANTRY CRUD OPERATIONS ---
    public void addPantryItem(PantryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName());
        values.put(KEY_QUANTITY, item.getQuantity());
        values.put(KEY_UNIT, item.getUnit());
        values.put(KEY_EXPIRY, item.getExpiryDate());
        db.insert(TABLE_PANTRY, null, values);
        db.close();
    }

    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> itemList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PANTRY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                PantryItem item = new PantryItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }

    public void updatePantryItem(PantryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName());
        values.put(KEY_QUANTITY, item.getQuantity());
        values.put(KEY_UNIT, item.getUnit());
        values.put(KEY_EXPIRY, item.getExpiryDate());
        db.update(TABLE_PANTRY, values, KEY_ID + " = ?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public void deletePantryItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PANTRY, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // --- RECIPE OPERATIONS ---
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RECIPES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recipeList;
    }

    private void seedRecipes(SQLiteDatabase db) {
        addRecipe(db, "Scrambled Eggs", "eggs, butter, milk", "Whisk eggs with milk, cook in butter.");
        addRecipe(db, "Simple French Toast", "bread, eggs, milk, butter", "Dip bread in egg/milk mix, fry in butter.");
        addRecipe(db, "Tomato Pasta", "pasta, tomato, olive oil, salt", "Boil pasta, mix with tomatoes and oil.");
        addRecipe(db, "Garlic Butter Rice", "rice, butter, garlic, salt", "Cook rice, stir in garlic and butter.");
        addRecipe(db, "Cheese Toastie", "bread, cheese, butter", "Butter bread, add cheese, grill.");
        addRecipe(db, "Oatmeal", "oats, milk, sugar", "Boil oats in milk, add sugar.");
        addRecipe(db, "Peanut Butter Sandwich", "bread, peanut butter", "Spread peanut butter on bread.");
        addRecipe(db, "Tuna Salad", "tuna, mayonnaise, bread", "Mix tuna and mayo, serve on bread.");
        addRecipe(db, "Vegetable Stir-fry", "rice, carrot, onion, soy sauce", "Fry veg, add soy sauce, serve with rice.");
        addRecipe(db, "Pancakes", "flour, milk, eggs, sugar", "Mix into batter, fry on pan.");
        addRecipe(db, "Mac and Cheese", "pasta, cheese, milk, butter", "Boil pasta, melt cheese with milk/butter.");
        addRecipe(db, "Bean Toast", "bread, baked beans, butter", "Toast bread, heat beans, top with butter.");
        addRecipe(db, "Chicken and Rice", "chicken, rice, olive oil, salt", "Cook chicken, serve with rice.");
        addRecipe(db, "Potato Soup", "potato, milk, butter, onion", "Boil potatoes/onions, blend with milk.");
        addRecipe(db, "Egg Fried Rice", "rice, eggs, soy sauce, onion", "Fry rice, egg, and onion with soy sauce.");
        addRecipe(db, "Grilled Cheese", "bread, cheese, butter", "Fry cheese between buttered bread.");
        addRecipe(db, "Simple Salad", "lettuce, tomato, cucumber, olive oil", "Chop veggies, drizzle with oil.");
        addRecipe(db, "Fruit Smoothie", "banana, milk, honey", "Blend ingredients until smooth.");
    }

    private void addRecipe(SQLiteDatabase db, String name, String ingredients, String instructions) {
        ContentValues values = new ContentValues();
        values.put(KEY_RECIPE_NAME, name);
        values.put(KEY_RECIPE_INGREDIENTS, ingredients);
        values.put(KEY_RECIPE_INSTRUCTIONS, instructions);
        db.insert(TABLE_RECIPES, null, values);
    }
}