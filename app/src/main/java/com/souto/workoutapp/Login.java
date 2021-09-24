package com.souto.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEmail = edt_email.getText().toString();
                String getPassword = edt_password.getText().toString();

                if(!TextUtils.isEmpty(getEmail) || !TextUtils.isEmpty(getPassword)) {
                    mAuth.signInWithEmailAndPassword(getEmail,getPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        openMainScreen();
                                    }else{
                                        String error = task.getException().getMessage();
                                        Toast.makeText(Login.this, ""+error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    private void openMainScreen() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}