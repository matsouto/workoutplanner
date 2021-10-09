package com.souto.workoutapp.exercises;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.souto.workoutapp.R;
import com.souto.workoutapp.model.ExerciseModel;

import java.util.ArrayList;

public class Lats extends AppCompatActivity {

    Button btn_add;
    ArrayList<ExerciseModel> exercises = new ArrayList<>(); //
    ListView listView; //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); // Hide Navigation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Hide Status

        getSupportActionBar().setTitle("A - D A Y");

        setContentView(R.layout.activity_lats);

        //-----------------------------------------------------

        // Add button will open the Exercise Creator
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExerciseCreatorLats();
            }
        });

        listView = (ListView)findViewById(R.id.lats_exercise_list);
    }

    public void openExerciseCreatorLats() {
        Intent intent = new Intent(this, ExerciseCreatorLats.class);
        startActivity(intent);

    }

}