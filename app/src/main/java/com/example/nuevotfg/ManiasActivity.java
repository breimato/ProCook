package com.example.nuevotfg;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.List;

public class ManiasActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String KEY_FOR_INTENT = "1";
    private EditText etIntolerance;
    private Button btnAdd, btnDelete;
    private ListView mListView;
    private List<String> mLista = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manias);


        DB = new DBHelper(this);
        etIntolerance = findViewById(R.id.etIngredientName);
        btnAdd = findViewById(R.id.insertIntolerance);
        btnDelete = findViewById(R.id.deleteIntolerance);
        mListView = findViewById(R.id.listaIntolerancias);
        btnAdd.setOnClickListener(this);
        mListView.setOnItemClickListener(this);
        Intent intent = getIntent();
        String session_id = intent.getStringExtra(KEY_FOR_INTENT);
        addDatabaseToListAdapter(session_id);
    }

    private void addDatabaseToListAdapter(String session_id) {

        Cursor res = DB.viewIngredient(session_id, 1);
        while (res.moveToNext()) {
            String ingrediente = (res.getString(1));
            if (!mLista.contains(ingrediente)){
                mLista.add(ingrediente);
            }
        }
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mLista);
        mListView.setAdapter(mAdapter);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.insertIntolerance:
                String ingredient = etIntolerance.getText().toString().trim();
                Intent intent = getIntent();
                String session_id = intent.getStringExtra(KEY_FOR_INTENT);
                if (ingredient.equals("")){
                    Toast.makeText(ManiasActivity.this, "Input some value", Toast.LENGTH_SHORT).show();
                }else{
                    boolean checkInsertData = DB.insertIngredient(ingredient, session_id, 1);
                    if (checkInsertData) {
                        Toast.makeText(ManiasActivity.this, "Alérgeno añadido", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ManiasActivity.this, "El alérgeno no se ha podido añadir", Toast.LENGTH_SHORT).show();
                    }
                    etIntolerance.setText("");
                    addDatabaseToListAdapter(session_id);
                }
                break;
            case R.id.deleteIntolerance:

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Alérgeno "+position+1, Toast.LENGTH_SHORT).show();
    }
}
