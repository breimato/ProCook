package com.example.nuevotfg;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    public static final String apiKey = "&apiKey=40c52ddeebce4a988c8472b08e9bc93b";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);
        Intent intent = getIntent();
        String session_id = intent.getStringExtra(KEY_FOR_INTENT);
        //INSTANCIA DEL DB HELPER
        DB = new DBHelper(this);
        //CONFIGURACIÓN DE LA RECYCLERVIEW
        recyclerViewSetup();
        //CONSULTA A LA API
        getHttpRequestAndCall(-1, session_id);
    }

    @Override
    public void onResultClick(int position) {
        Intent intent = getIntent();
        String session_id = intent.getStringExtra(KEY_FOR_INTENT);
        getHttpRequestAndCall(position, session_id);
    }

    private void recyclerViewSetup() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(this, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        //EN CUANTAS COLUMNAS SE VA A VER LA LISTA
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void getHttpRequestAndCall(int position, String session_id) {
        OkHttpClient client = new OkHttpClient();
        //GSON PARA PARSEAR EL JSON A OBJETO
        Gson gson = new Gson();
        //URL Y APIKEY DE LA API
        DB = new DBHelper(this);
        Cursor res = DB.viewIngredient(session_id);
        StringBuilder buffer = new StringBuilder();
        while (res.moveToNext()) {
            buffer.append(res.getString(1)).append(",");
        }
        String ingredients = buffer.toString();
        Request request = new Request.Builder()
                .url(url + ingredients + apiKey)
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
                            if (position == -1) {
                                Recipe r = gson.fromJson(myResponse, Recipe.class);
                                recyclerViewAdapter.addFoodList(r);
                            } else {
                                Recipe r = gson.fromJson(myResponse, Recipe.class);
                                Intent intent = new Intent(CookActivity.this, RecipeViewActivity.class);
                                intent.putExtra(KEY_FOR_INTENT, r.results.get(position).getId());
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }
}

