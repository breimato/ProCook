package com.example.nuevotfg.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient_table")
public class Ingredient {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ingredient")
    private String mIngredient;

    public Ingredient(@NonNull String ingredient) {
        this.mIngredient = ingredient;
    }
    public String getIngredient(){
        return this.mIngredient;
    }
}
