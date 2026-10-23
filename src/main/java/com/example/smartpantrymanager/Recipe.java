package com.example.smartpantrymanager;

public class Recipe {
    private int id;
    private String name;
    private String requiredIngredients;
    private String instructions;

    public Recipe(int id, String name, String requiredIngredients, String instructions) {
        this.id = id;
        this.name = name;
        this.requiredIngredients = requiredIngredients;
        this.instructions = instructions;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRequiredIngredients() { return requiredIngredients; }
    public void setRequiredIngredients(String requiredIngredients) { this.requiredIngredients = requiredIngredients; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
}