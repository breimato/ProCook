package com.example.nuevotfg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nuevotfg.db.Ingredient;

public class NewIngredientActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.android.ingredientlistsql.REPLY";
    private static final int NEW_INGREDIENT_ACTIVITY_REQUEST_CODE = 1;

    private EditText mEditIngredientView;
    private IngredientViewModel mIngredientViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);
        mEditIngredientView = findViewById(R.id.edit_ingredient);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditIngredientView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String ingredient = mEditIngredientView.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, ingredient);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_INGREDIENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Ingredient ingredient = new Ingredient(data.getStringExtra(NewIngredientActivity.EXTRA_REPLY));
            mIngredientViewModel.insert(ingredient);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
