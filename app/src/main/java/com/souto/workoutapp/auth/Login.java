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
import com.souto.workoutapp.MainActivity;
import com.souto.workoutapp.R;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private EditText edt_email;
    private EditText edt_password;
    public Button btn_login;
    public Button btn_register;
    private FirebaseAuth mAuth;
    public CheckBox ckb_show;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); // Hide Navigation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // Hide Status
        Objects.requireNonNull(getSupportActionBar()).hide(); // Hide Action Bar
        setContentView(R.layout.activity_login);

        edt_email = findViewById(R.id.login_email);
        edt_password = findViewById(R.id.login_password);
        btn_login = findViewById(R.id.login_btn);
        btn_register = findViewById(R.id.login_register);
        mAuth = FirebaseAuth.getInstance();
        ckb_show = findViewById(R.id.login_show);

        // Login button
        btn_login.setOnClickListener(view -> {
            String getEmail = edt_email.getText().toString();
            String getPassword = edt_password.getText().toString();

            // If email or password boxes are not empty
            if(!TextUtils.isEmpty(getEmail) || !TextUtils.isEmpty(getPassword)) {
                mAuth.signInWithEmailAndPassword(getEmail,getPassword)
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful()) {
                                openMainScreen();
                            }else{
                                String error = Objects.requireNonNull(task.getException()).getMessage();
                                Toast.makeText(Login.this, ""+error, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Show password button
        ckb_show.setOnCheckedChangeListener((compoundButton, b) -> {
            if(compoundButton.isChecked()) {
                edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else{
                edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        btn_register.setOnClickListener(view -> {
            openRegister();
        });
    }


    private void openMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void openRegister() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        finish();
    }
}