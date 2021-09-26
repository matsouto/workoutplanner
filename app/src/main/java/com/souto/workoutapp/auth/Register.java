package com.souto.workoutapp.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.souto.workoutapp.R;

import java.util.Objects;

public class Register extends AppCompatActivity {

    public Button btn_register;
    public Button btn_login;
    private EditText edt_name;
    private EditText edt_email;
    private EditText edt_password;
    public CheckBox ckb_show;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); // Hide Navigation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // Hide Status
        Objects.requireNonNull(getSupportActionBar()).hide(); // Hide Action Bar
        setContentView(R.layout.activity_register);

        btn_register = findViewById(R.id.register_btn);
        btn_login = findViewById(R.id.register_login);
        edt_name = findViewById(R.id.register_name);
        edt_email = findViewById(R.id.register_email);
        edt_password = findViewById(R.id.register_password);
        ckb_show = findViewById(R.id.register_show);
        mAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(view -> openLogin());

        ckb_show.setOnCheckedChangeListener((compoundButton, b) -> {
            if(compoundButton.isChecked()) {
                edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else{
                edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        btn_register.setOnClickListener(view -> {
            String name = edt_name.getText().toString();
            String email = edt_email.getText().toString();
            String password = edt_password.getText().toString();

            // If email or password boxes are not empty
            if (!TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                        openLogin();
                    }else{
                        String error = Objects.requireNonNull(task.getException()).getMessage();
                        Toast.makeText(Register.this, ""+error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void openLogin() {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
        finish();
    }
}