package com.souto.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.souto.workoutapp.auth.Login;
import com.souto.workoutapp.auth.Register;

import java.util.Objects;

// MENU

public class MainActivity extends AppCompatActivity {

    // Identifies if there is already a authentication session running

    private FirebaseAuth mAuth;

    protected Button btn_planner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); // Hide Navigation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // Hide Status
        Objects.requireNonNull(getSupportActionBar()).hide(); // Hide Action Bar

        setContentView(R.layout.activity_main);

        //----------------------------------------------

        // Setting the callback for the planner button
        btn_planner = findViewById(R.id.btn_planner);
        btn_planner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlanner();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Get the logged user, if it exists
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();

        // If not, go to login screen
        if(current_user == null) {
            Intent intent_login = new Intent(this, Login.class);
            startActivity(intent_login);
            // finishes the activity to not occupy memory
            finish();
        }
    }

    private void startPlanner() {
        Intent intent_planner = new Intent(this, WorkoutPlanner.class);
        startActivity(intent_planner);
        finish();
    }
}