package com.example.nuevotfg;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FridgeActivity extends AppCompatActivity {

    public static final int NEW_INGREDIENT_ACTIVITY_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fridge_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        final IngredientListAdapter adapter = new IngredientListAdapter(new IngredientListAdapter.IngredientDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        IngredientViewModel mIngredientViewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
        mIngredientViewModel.getAllIngredients().observe(this, ingredients -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(ingredients);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener( view -> {
            Intent intent = new Intent(FridgeActivity.this, NewIngredientActivity.class);
            startActivityForResult(intent, NEW_INGREDIENT_ACTIVITY_REQUEST_CODE);
        });
    }
}
