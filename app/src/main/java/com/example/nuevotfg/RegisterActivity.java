package com.example.nuevotfg;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nuevotfg.DB.DBHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText emailText, passText, nameText, lastnameText, phoneText;
    Button registerButton;
    FirebaseAuth mAuth;
    DBHelper DB;
    CheckBox checkBox;
    AlertDialog.Builder alertDialogBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setup();
        registerButton.setEnabled(false);
        alertDialogBuilder.setMessage(R.string.terms_and_conditions)
                .setTitle("Terms and Conditions");
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    alertDialogBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            registerButton.setEnabled(true);
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
                    registerButton.setEnabled(false);
                }
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
                userToDatabase();
            }
        });
    }

    private void createUser(){
        String email = emailText.getText().toString();
        String pass = passText.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailText.setError("Email cannot be empty");
            emailText.requestFocus();
        }else if (TextUtils.isEmpty(pass)){
            passText.setError("Password cannot be empty");
            passText.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "User signed up", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Sign up error", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void userToDatabase(){
        String email = emailText.getText().toString();
        String pass = passText.getText().toString();
        String name = nameText.getText().toString();
        String lastname = lastnameText.getText().toString();
        String phone = phoneText.getText().toString();
        DB.insertUser(email, pass, name, lastname, phone);
    }

    private void setup() {
        mAuth = FirebaseAuth.getInstance();
        DB = new DBHelper(this);
        alertDialogBuilder = new AlertDialog.Builder(this);
        registerButton = findViewById(R.id.buttonRegisterView);
        emailText = findViewById(R.id.emailBox);
        passText = findViewById(R.id.passwordTextBox);
        nameText = findViewById(R.id.nameBox);
        lastnameText = findViewById(R.id.lastNameBox);
        phoneText = findViewById(R.id.phoneTextBox);
        checkBox = findViewById(R.id.checkboxTerms);
    }
}
