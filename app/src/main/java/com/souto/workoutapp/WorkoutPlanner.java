package com.souto.workoutapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.souto.workoutapp.exercises.Lats;

public class WorkoutPlanner extends AppCompatActivity {

    protected Button btn_lats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); // Hide Navigation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // Hide Status

        getSupportActionBar().setTitle("P L A N N E R");

        setContentView(R.layout.activity_workout_planner);

        //-----------------------------------------------------

        btn_lats = findViewById(R.id.btn_A);
        btn_lats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLats();
            }
        });
    }

    public void startLats() {
        Intent intent_lats = new Intent(this, Lats.class);
        startActivity(intent_lats);
    }
}