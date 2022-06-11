package com.example.nuevotfg;

import android.content.Intent;
import android.database.Cursor;
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

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    public static final String KEY_FOR_INTENT = "1";
    Button btnLogin, btnRegister;
    EditText inputMail, inputPass;
    DBHelper DB;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setup();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(mainActivity);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {

        String email = inputMail.getText().toString();
        String pass = inputPass.getText().toString();

        if (TextUtils.isEmpty(email)) {
            inputMail.setError("El email no puede estar vacío");
            inputMail.requestFocus();
        } else if (TextUtils.isEmpty(pass)) {
            inputPass.setError("La contraseña no puede estar vacía");
            inputPass.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String session_id = "";
                        Cursor res = DB.checkUserAndPass(email, pass);
                        if (res.moveToFirst()){
                            session_id = res.getString(res.getColumnIndexOrThrow("idUser"));
                        }
                        Toast.makeText(LoginActivity.this, "User logged in", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.putExtra(KEY_FOR_INTENT, session_id);
                        startActivity(i);
                    } else {
                        Toast.makeText(LoginActivity.this, "Login error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setup() {
        DB = new DBHelper(this);
        mAuth = FirebaseAuth.getInstance();
        btnLogin = findViewById(R.id.loginButton);
        btnRegister = findViewById(R.id.registerButton);
        inputMail = findViewById(R.id.emailTextBox);
        inputPass = findViewById(R.id.passwordTextBox);
    }

//    private void register(){
//        String email = inputMail.getText().toString();
//        String pass = inputPass.getText().toString();
//        if (isEmail(inputMail) && isPass(inputPass)) {
//            Toast.makeText(LoginActivity.this, "Registro Correcto", Toast.LENGTH_SHORT).show();
//            emails.add(email);
//            contraseñas.add(pass);
//            cambiarActivity(email);
//        } else {
//            showAlert("Sintaxis incorrecta del email o contraseña");
//        }
//    }


//    private void loginButton(){
//        String email = inputMail.getText().toString();
//        String pass = inputPass.getText().toString();
//
//        if (isEmail(inputMail) && isPass(inputPass)) {
//            for (int i = 0; i < emails.size(); i++) {
//                if (email.equals(emails.get(i)) && pass.equals(contraseñas.get(i))) {
//                    Toast.makeText(LoginActivity.this, "LoginActivity Correcto", Toast.LENGTH_SHORT).show();
//                    changeActivity();
//                } else {
//                    if (i == emails.size() - 1) {
//                        Toast.makeText(LoginActivity.this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        } else {
//            showAlert("Sintaxis incorrecta del email o contraseña");
//        }
//    }
//
//    private static boolean isEmail(EditText text) {
//        CharSequence email = text.getText().toString();
//        return (!TextUtils.isEmpty(email) &&
//                Patterns.EMAIL_ADDRESS.matcher(email).matches());
//    }
//
//    private static boolean isPass(EditText text) {
//        String pass = text.getText().toString();
//        return (!TextUtils.isEmpty(pass));
//    }
//
//    private void showAlert(String mensaje) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//        builder.setMessage(mensaje);
//        builder.setTitle("Error");
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//    private void changeActivity() {
//        Intent mainActivity = new Intent(this, RegisterActivity.class);
//        startActivity(mainActivity);
//    }
}

