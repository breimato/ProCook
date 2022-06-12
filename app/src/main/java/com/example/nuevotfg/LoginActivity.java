package com.example.nuevotfg;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nuevotfg.DB.DBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    public static final String KEY_FOR_INTENT = "1";
    Button btnLogin;
    TextView btnRegister;
    EditText inputMail, inputPass;
    DBHelper DB;
    FirebaseAuth mAuth;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setup();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.terms_and_conditions)
                .setTitle("Terms and Conditions");

        btnLogin.setEnabled(false);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    alertDialogBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            btnLogin.setEnabled(true);
                            dialog.dismiss();
                        }
                    });
                    alertDialogBuilder.setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            checkBox.setChecked(false);
                        }
                    });
                    alertDialogBuilder.show();
                }else{
                    btnLogin.setEnabled(false);
                }
            }
        });
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
        checkBox = findViewById(R.id.checkboxTerms);
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

