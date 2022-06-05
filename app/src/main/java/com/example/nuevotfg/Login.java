package com.example.nuevotfg;

import android.app.AlertDialog;
import android.app.backup.FileBackupHelper;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    public static final String KEY_FOR_INTENT = "1";

    Button btnLogin, btnRegister;
    EditText inputMail, inputPass;
    ArrayList<String> emails = new ArrayList<>();
    ArrayList<String> contraseñas = new ArrayList<>();
    DBHelper DB;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setup();
        DB = new DBHelper(this);
        mAuth = FirebaseAuth.getInstance();

//        //Login
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loginButton();
//            }
//        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(Login.this, Register.class);
                startActivity(mainActivity);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

//        btnRegister.setOnClickListener(view->{
//            startActivity(new Intent(Login.this, Register.class));
//        });
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

                        Toast.makeText(Login.this, "El usuario se loggeo correctamente", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Login.this, MainActivity.class);
                        i.putExtra(KEY_FOR_INTENT, session_id);
                        startActivity(i);
                    } else {
                        Toast.makeText(Login.this, "LogIn error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setup() {
        btnLogin = findViewById(R.id.loginButton);
        btnRegister = findViewById(R.id.registerButton);
        inputMail = findViewById(R.id.emailTextBox);
        inputPass = findViewById(R.id.passwordTextBox);
    }

//    private void register(){
//        String email = inputMail.getText().toString();
//        String pass = inputPass.getText().toString();
//        if (isEmail(inputMail) && isPass(inputPass)) {
//            Toast.makeText(Login.this, "Registro Correcto", Toast.LENGTH_SHORT).show();
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
//                    Toast.makeText(Login.this, "Login Correcto", Toast.LENGTH_SHORT).show();
//                    changeActivity();
//                } else {
//                    if (i == emails.size() - 1) {
//                        Toast.makeText(Login.this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
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
//        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
//        builder.setMessage(mensaje);
//        builder.setTitle("Error");
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//    private void changeActivity() {
//        Intent mainActivity = new Intent(this, Register.class);
//        startActivity(mainActivity);
//    }
}

