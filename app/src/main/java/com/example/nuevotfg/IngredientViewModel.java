package com.example.nuevotfg;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.nuevotfg.db.Ingredient;
import com.example.nuevotfg.db.IngredientRepository;

import java.util.List;

public class IngredientViewModel extends AndroidViewModel {
    private IngredientRepository mRepository;

    private final List<Ingredient> mAllIngredients;

    public IngredientViewModel (Application application) {
        super(application);
        mRepository = new IngredientRepository(application);
        mAllIngredients = mRepository.getAllIngredients();
    }

    List<Ingredient> getAllIngredients() { return mAllIngredients; }

    public void insert(Ingredient ingredient) { mRepository.insert(ingredient); }
}
