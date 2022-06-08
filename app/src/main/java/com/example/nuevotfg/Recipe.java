package com.example.nuevotfg;

import java.util.ArrayList;

public class Recipe {
    ArrayList<Result> results;

    public Recipe(ArrayList<Result> results) {
        this.results = results;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }
}
