package com.example.nuevotfg;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nuevotfg.DB.DBHelper;
import com.example.nuevotfg.Model.Recipe;
import com.example.nuevotfg.ViewAdapter.RecyclerViewAdapter;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CookActivity extends AppCompatActivity implements RecyclerViewAdapter.onResultListener {
    public static final String KEY_FOR_INTENT = "1";
    public static final String url = "https://api.spoonacular.com/recipes/complexSearch?includeIngredients=";
    public static final String apiKey = "&apiKey=6466dbfbe08c462299e8547928e2df0f";
    private RecyclerViewAdapter recyclerViewAdapter;
    Gson gson;
    DBHelper DB;
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);
        getHttpRequestAndCall(-1, recyclerViewSetupAndIntent());
    }

    @Override
    public void onResultClick(int position) {
        Intent intent = getIntent();
        String session_id = intent.getStringExtra(KEY_FOR_INTENT);
        getHttpRequestAndCall(position, session_id);
    }

    private void getHttpRequestAndCall(int position, String session_id) {
        Cursor res = DB.viewIngredient(session_id, 0);
        String ingredients = getIngredientsOrIntolerances(res);

        res = DB.viewIngredient(session_id, 1);
        String ingredientsIntolerance = getIngredientsOrIntolerances(res);

        Request request = new Request.Builder()
                .url(url + ingredients + "&intolerances=" +ingredientsIntolerance + apiKey)
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    CookActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Recipe r = gson.fromJson(myResponse, Recipe.class);
                            if (position == -1) {
                                recyclerViewAdapter.addFoodList(r);
                            } else {
                                startActivityFunction(r, position);
                            }
                        }
                    });
                }
            }
        });
    }

    private void startActivityFunction(Recipe r, int position) {
        Intent intent = new Intent(CookActivity.this, RecipeViewActivity.class);
        intent.putExtra(KEY_FOR_INTENT, r.results.get(position).getId());
        startActivity(intent);
    }

    @NonNull
    private String getIngredientsOrIntolerances(Cursor res) {
        StringBuilder buffer = new StringBuilder();
        while (res.moveToNext()) {
            buffer.append(res.getString(1)).append(",");
        }
        return buffer.toString();
    }

    private String recyclerViewSetupAndIntent() {
        Intent intent = getIntent();
        String session_id = intent.getStringExtra(KEY_FOR_INTENT);
        DB = new DBHelper(this);
        gson = new Gson();
        client = new OkHttpClient();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(this, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        return session_id;
    }
}

