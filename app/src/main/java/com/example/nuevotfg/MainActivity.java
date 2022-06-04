package com.example.nuevotfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button btnNevera, btnCook, btnManias, btnLoggout;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        setup();
        btnLoggout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loggOut = new Intent(MainActivity.this, Login.class);
                startActivity(loggOut);
            }
        });
        btnCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cookIntent = new Intent(MainActivity.this, CookActivity.class);
                startActivity(cookIntent);
            }
        });
        btnManias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent maniasIntent = new Intent(MainActivity.this, ManiasActivity.class);
                startActivity(maniasIntent);
            }
        });
        btnNevera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fridgeIntent = new Intent(MainActivity.this, FridgeActivity.class);
                startActivity(fridgeIntent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, Login.class));
        }
    }
    private void setup(){
        btnNevera = findViewById(R.id.nevera);
        btnCook = findViewById(R.id.cocinar);
        btnManias = findViewById(R.id.vicios);
        btnLoggout = findViewById(R.id.logout);
    }

}