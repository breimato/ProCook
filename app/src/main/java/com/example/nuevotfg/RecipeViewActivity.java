package com.example.nuevotfg;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecipeViewActivity extends AppCompatActivity {

    DBHelper DB;
    public static final String KEY_FOR_INTENT = "1";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_view);

        Intent intent = getIntent();
        int id = intent.getIntExtra(KEY_FOR_INTENT, 0);
        System.out.println(id);

//        OkHttpClient client = new OkHttpClient();
//        //GSON PARA PARSEAR EL JSON A OBJETO
//        Gson gson = new Gson();
//        //URL Y APIKEY DE LA API
//        String apiKey = "&apiKey=40c52ddeebce4a988c8472b08e9bc93b";
//        String url = "https://api.spoonacular.com/recipes/complexSearch?includeIngredients=onion,tomatoes";
//        Request request = new Request.Builder()
//                .url(url+apiKey)
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                if (response.isSuccessful()){
//                    String myResponse = response.body().string();
//                    CookActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (position == -1){
//                                Recipe r = gson.fromJson(myResponse, Recipe.class);
//                                recyclerViewAdapter.addFoodList(r);
//                            }else{
//                                Recipe r = gson.fromJson(myResponse, Recipe.class);
//                                Intent intent = new Intent(CookActivity.this, RecipeViewActivity.class);
//                                intent.putExtra(KEY_FOR_INTENT, r.results.get(position).getId());
//                                startActivity(intent);
//                            }
//                        }
//                    });
//                }
//            }
//        });
    }
}
