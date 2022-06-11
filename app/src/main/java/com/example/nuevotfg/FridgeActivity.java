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

public class FridgeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String KEY_FOR_INTENT = "1";
    EditText name;
    Button insertButton, deleteButton;
    private ListView mListView;
    private List<String> mLista = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        Intent intent = getIntent();
        String session_id = intent.getStringExtra(KEY_FOR_INTENT);
        DB = new DBHelper(this);
        name = findViewById(R.id.ingredientName);
        insertButton = findViewById(R.id.insertButton);
        deleteButton = findViewById(R.id.deleteButton);
        mListView = findViewById(R.id.listaIngredientes);
        mListView.setOnItemClickListener(this);

        addDatabaseToListAdapter(session_id);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredient = name.getText().toString().trim();
                if (ingredient.equals("")){
                    Toast.makeText(FridgeActivity.this, "Input some value", Toast.LENGTH_SHORT).show();
                }else{
                    boolean checkInsertData = DB.insertIngredient(ingredient, session_id, 0);
                    if (checkInsertData) {
                        Toast.makeText(FridgeActivity.this, "Ingrediente añadido", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FridgeActivity.this, "El ingrediente no se ha podido añadir", Toast.LENGTH_SHORT).show();
                    }
                    name.setText("");
                    addDatabaseToListAdapter(session_id);
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredientToDelete = name.getText().toString().trim();

                boolean checkDeleteData = DB.deleteIngredient(ingredientToDelete, 0);
                if (checkDeleteData) {
                    Toast.makeText(FridgeActivity.this, "Ingrediente eliminado", Toast.LENGTH_SHORT).show();
                    mLista.remove(ingredientToDelete);
                } else {
                    Toast.makeText(FridgeActivity.this, "El ingrediente no se ha podido eliminar", Toast.LENGTH_SHORT).show();
                }
                addDatabaseToListAdapter(session_id);
            }
        });


    }

    private void addDatabaseToListAdapter(String session_id) {
        Cursor res = DB.viewIngredient(session_id, 0);
        while (res.moveToNext()) {
            String ingrediente = (res.getString(1));
            if (!mLista.contains(ingrediente)){
                mLista.add(ingrediente);
            }
        }
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mLista);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Ingrediente número "+position+1, Toast.LENGTH_SHORT).show();
    }
}
