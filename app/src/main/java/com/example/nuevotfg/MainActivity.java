package com.example.nuevotfg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nuevotfg.DB.DBHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_FOR_INTENT = "1";
    FirebaseAuth mAuth;
    Button btnNevera, btnCook, btnManias, btnLoggout;
    TextView tvWelcome;
    DBHelper DB;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        String session_id = setupWithIntent();
        buttonsCall(session_id);
        Cursor res = DB.viewUser(session_id);
        while (res.moveToNext()) {
            String welcome = (res.getString(0));
            tvWelcome.setText("Welcome, "+welcome);
        }
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void buttonsCall(String session_id){
        btnLoggout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent loggOut = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loggOut);
            }
        });
        btnCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cookIntent = new Intent(MainActivity.this, CookActivity.class);
                cookIntent.putExtra(KEY_FOR_INTENT, session_id);
                startActivity(cookIntent);
            }
        });
        btnManias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent maniasIntent = new Intent(MainActivity.this, ManiasActivity.class);
                maniasIntent.putExtra(KEY_FOR_INTENT, session_id);
                startActivity(maniasIntent);
            }
        });
        btnNevera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fridgeIntent = new Intent(MainActivity.this, FridgeActivity.class);
                fridgeIntent.putExtra(KEY_FOR_INTENT, session_id);
                startActivity(fridgeIntent);
            }
        });
    }
    private String setupWithIntent(){
        Intent intent = getIntent();
        String session_id = intent.getStringExtra(KEY_FOR_INTENT);
        tvWelcome = findViewById(R.id.tvTitle);
        btnNevera = findViewById(R.id.nevera);
        btnCook = findViewById(R.id.cocinar);
        btnManias = findViewById(R.id.vicios);
        btnLoggout = findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();
        DB = new DBHelper(this);
        return session_id;
    }

}
