package com.souto.workoutapp.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.souto.workoutapp.R;

import java.util.Objects;

public class Register extends AppCompatActivity {

    Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); // Hide Navigation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // Hide Status
        Objects.requireNonNull(getSupportActionBar()).hide(); // Hide Action Bar
        setContentView(R.layout.activity_register);

        register_btn = findViewById(R.id.register_btn);

        register_btn.setOnClickListener(view -> {
            openLogin();
        });
    }

    private void openLogin() {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }
}