package com.example.nuevotfg;

import android.os.Bundle;


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


public class CookActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(layoutManager);
        OkHttpClient client = new OkHttpClient();

        Gson gson = new Gson();

        String apiKey = "&apiKey=40c52ddeebce4a988c8472b08e9bc93b";
        String url = "https://api.spoonacular.com/recipes/complexSearch?includeIngredients=onion,tomatoes";
        Request request = new Request.Builder()
                .url(url+apiKey)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String myResponse = response.body().string();


                    CookActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Recipe r = gson.fromJson(myResponse, Recipe.class);

                            recyclerViewAdapter.addFoodList(r);
                        }
                    });
                }

            }
        });
    }

}

