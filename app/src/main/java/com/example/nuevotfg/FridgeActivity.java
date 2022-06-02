package com.example.nuevotfg;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nuevotfg.db.Ingredient;
import com.example.nuevotfg.db.IngredientRoomDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FridgeActivity extends AppCompatActivity {

    public static final int NEW_INGREDIENT_ACTIVITY_REQUEST_CODE = 1;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fridge_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        final IngredientListAdapter adapter = new IngredientListAdapter(new IngredientListAdapter.IngredientDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        IngredientViewModel mIngredientViewModel = new ViewModelProvider(this).get(IngredientViewModel.class);
        List<Ingredient> listaIng = mIngredientViewModel.getAllIngredients();
        adapter.submitList(listaIng);


            IngredientRoomDatabase db = IngredientRoomDatabase.getDatabase(this.getApplicationContext());
            Ingredient ing1 = new Ingredient();
            ing1.setName("calabacin");
            Ingredient ing2 =new Ingredient();
            ing2.setName("ajo");
            Ingredient ing3 = new Ingredient();
            ing3.setName("cebolla");

            db.ingredientDao().insert(ing1,ing2,ing3);


            List<Ingredient> listaIngredientes = db.ingredientDao().getAlphabetizedIngredients();
            db.close();

            System.out.println("lista ingredientes"+listaIngredientes);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener( view -> {
            Intent intent = new Intent(FridgeActivity.this, NewIngredientActivity.class);
            startActivityForResult(intent, NEW_INGREDIENT_ACTIVITY_REQUEST_CODE);
        });
    }
}
