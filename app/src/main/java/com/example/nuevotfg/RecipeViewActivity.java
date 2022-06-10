package com.example.nuevotfg;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import org.jsoup.Jsoup;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecipeViewActivity extends AppCompatActivity {

    public static final String KEY_FOR_INTENT = "1";
    TextView tvTitle, tvTime, tvInstructions;
    ImageView imgRecipe;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_view);

        tvTitle = findViewById(R.id.tvTitleRecipe);
        tvTime = findViewById(R.id.tvTimeRecipe);
        tvInstructions = findViewById(R.id.tvInstructionsRecipe);
        imgRecipe = findViewById(R.id.imageView);
        Intent intent = getIntent();
        int id = intent.getIntExtra(KEY_FOR_INTENT, 0);

        OkHttpClient client = new OkHttpClient();

        Gson gson = new Gson();

        String apiKey = "?apiKey=40c52ddeebce4a988c8472b08e9bc93b";
        String url = "https://api.spoonacular.com/recipes/"+id+"/information";

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
                    RecipeViewActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            FinalRecipe finalRecipe = gson.fromJson(myResponse, FinalRecipe.class);
                            Glide.with(RecipeViewActivity.this)
                                    .load(finalRecipe.getImage())
                                    .centerCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(imgRecipe);
                            int i = finalRecipe.getReadyInMinutes();
                            String htmlText = html2text(finalRecipe.getInstructions());
                            String time = Integer.toString(i);
                            time += " min";
                            tvTitle.setText(finalRecipe.getTitle());
                            tvInstructions.setText(htmlText);
                            tvTime.setText(time);
                        }

                        public String html2text(String html) {
                            return Jsoup.parse(html).text();
                        }
                    });
                }
            }
        });
    }
}
