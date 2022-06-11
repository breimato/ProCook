package com.example.nuevotfg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nuevotfg.DB.DBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText emailText;
    EditText passText;
    Button registerButton;
    FirebaseAuth mAuth;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        DB = new DBHelper(this);
        registerButton = findViewById(R.id.buttonRegisterView);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
                userToDatabase();
            }
        });
    }

    private void userToDatabase(){
        emailText = findViewById(R.id.emailBox);
        passText = findViewById(R.id.passwordTextBox);
        String email = emailText.getText().toString();
        String pass = passText.getText().toString();

        DB.insertUser(email, pass);
    }
    private void createUser(){
        emailText = findViewById(R.id.emailBox);
        passText = findViewById(R.id.passwordTextBox);
        String email = emailText.getText().toString();
        String pass = passText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailText.setError("El email no puede estar vacío");
            emailText.requestFocus();
        }else if (TextUtils.isEmpty(pass)){
            passText.setError("La contraseña no puede estar vacía");
            passText.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Hubo un fallo a la hora de registrar el usuario" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
