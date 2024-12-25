package com.example.androidproject2024;

public class Recipe {
    private String name;
    private int imageResourceId;
    private String instructions;

    public Recipe(String name, int imageResourceId, String instructions) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getInstructions() {
        return instructions;
    }
}
