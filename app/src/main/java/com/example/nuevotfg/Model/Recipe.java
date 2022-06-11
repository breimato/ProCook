package com.example.nuevotfg.Model;

import java.util.ArrayList;

public class Recipe {
    public ArrayList<Result> results;

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
