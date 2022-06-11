package com.example.nuevotfg;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nuevotfg.DB.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ManiasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String KEY_FOR_INTENT = "1";
    private EditText etIntolerance;
    private Button btnAdd, btnDelete;
    private ListView mListView;
    private final List<String> mLista = new ArrayList<>();
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manias);
        String session_id = setupWithIntent();
        addDatabaseToListAdapter(session_id);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredient = etIntolerance.getText().toString().trim();

                if (ingredient.equals("")){
                    Toast.makeText(ManiasActivity.this, "Input some value", Toast.LENGTH_SHORT).show();
                }else{
                    insertAllergen(ingredient, session_id);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredientToDelete = etIntolerance.getText().toString().trim();

                if (ingredientToDelete.equals("")){
                    Toast.makeText(ManiasActivity.this, "Input some value", Toast.LENGTH_SHORT).show();
                }else{
                    deleteAllergen(ingredientToDelete, session_id);
                }

            }
        });


    }

    private void deleteAllergen(String ingredientToDelete, String session_id) {
        boolean checkDeleteData = DB.deleteIngredient(ingredientToDelete, 1);
        if (checkDeleteData) {
            Toast.makeText(ManiasActivity.this, "Allergen removed", Toast.LENGTH_SHORT).show();
            mLista.remove(ingredientToDelete);
        } else {
            Toast.makeText(ManiasActivity.this, "Can´t remove the allergen", Toast.LENGTH_SHORT).show();
        }
        addDatabaseToListAdapter(session_id);
        etIntolerance.setText("");
    }

    private void insertAllergen(String ingredient, String session_id) {
        boolean checkInsertData = DB.insertIngredient(ingredient, session_id, 1);
        if (checkInsertData) {
            Toast.makeText(ManiasActivity.this, "Allergen added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ManiasActivity.this, "Can´t add the allergen", Toast.LENGTH_SHORT).show();
        }
        etIntolerance.setText("");
        addDatabaseToListAdapter(session_id);
    }

    private String setupWithIntent() {
        Intent intent = getIntent();
        String session_id = intent.getStringExtra(KEY_FOR_INTENT);
        DB = new DBHelper(this);
        etIntolerance = findViewById(R.id.etIngredientName);
        btnAdd = findViewById(R.id.insertIntolerance);
        btnDelete = findViewById(R.id.deleteIntolerance);
        mListView = findViewById(R.id.listaIntolerancias);
        mListView.setOnItemClickListener(this);
        return session_id;
    }

    private void addDatabaseToListAdapter(String session_id) {
        Cursor res = DB.viewIngredient(session_id, 1);
        while (res.moveToNext()) {
            String ingrediente = (res.getString(1));
            if (!mLista.contains(ingrediente)){
                mLista.add(ingrediente);
            }
        }
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mLista);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Alérgeno número "+position+1, Toast.LENGTH_SHORT).show();
    }
}
