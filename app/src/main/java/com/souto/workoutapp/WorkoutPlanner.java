package com.souto.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.souto.workoutapp.exercises.Chest;
import com.souto.workoutapp.exercises.Lats;
import com.souto.workoutapp.exercises.Legs;

import java.util.Objects;

public class WorkoutPlanner extends AppCompatActivity {

    protected Button btn_lats, btn_legs, btn_chest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); // Hide Navigation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // Hide Status

        Objects.requireNonNull(getSupportActionBar()).setTitle("P L A N N E R");

        setContentView(R.layout.activity_workout_planner);

        //-----------------------------------------------------

        btn_lats = findViewById(R.id.btn_A);
        btn_lats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLats();
            }
        });

        btn_legs = findViewById(R.id.btn_B);
        btn_legs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLegs();
            }
        });

        btn_chest = findViewById(R.id.btn_C);
        btn_chest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChest();
            }
        });

    }

    // On back pressed it finishes the activity to save RAM.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void startLats() {
        Intent intent_lats = new Intent(this, Lats.class);
        startActivity(intent_lats);
    }

    public void startLegs() {
        Intent intent_lats = new Intent(this, Legs.class);
        startActivity(intent_lats);
    }

    public void startChest() {
        Intent intent_lats = new Intent(this, Chest.class);
        startActivity(intent_lats);
    }
}