package com.example.nuevotfg;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class FridgeActivity extends AppCompatActivity {

    EditText name;
    Button insertButton, deleteButton, selectButton;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        name = findViewById(R.id.ingredientName);
        insertButton = findViewById(R.id.insertButton);
        deleteButton = findViewById(R.id.deleteButton);
        selectButton = findViewById(R.id.viewButton);
        DB = new DBHelper(this);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString = name.getText().toString();

                boolean checkInsertData = DB.insertIngredient(nameString);
                if (checkInsertData) {
                    Toast.makeText(FridgeActivity.this, "Ingrediente añadido", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FridgeActivity.this, "El ingrediente no se ha podido añadir", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString = name.getText().toString();

                boolean checkDeleteData = DB.deleteIngredient(nameString);
                if (checkDeleteData) {
                    Toast.makeText(FridgeActivity.this, "Ingrediente eliminado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FridgeActivity.this, "El ingrediente no se ha podido eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = DB.viewIngredient();
                if (res.getCount() == 0) {
                    Toast.makeText(FridgeActivity.this, "No hay ingredientes", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuilder buffer = new StringBuilder();
                while (res.moveToNext()) {
                    buffer.append("ID: ").append(res.getString(0)).append("/n");
                    buffer.append("Name: ").append(res.getString(1)).append("/n/n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(FridgeActivity.this);
                builder.setCancelable(true);
                builder.setTitle("INGREDIENTES");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

    }
}
