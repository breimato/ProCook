package com.example.nuevotfg.Model;

public class FinalRecipe {

    private final String title;
    private final int readyInMinutes;
    private final String image;
    private final String instructions;

    public FinalRecipe(String title, int readyInMinutes, String image, String instructions) {
        this.title = title;
        this.readyInMinutes = readyInMinutes;
        this.image = image;
        this.instructions = instructions;
    }

    public String getTitle() {
        return title;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public String getImage() {
        return image;
    }

    public String getInstructions() {
        return instructions;
    }

}
