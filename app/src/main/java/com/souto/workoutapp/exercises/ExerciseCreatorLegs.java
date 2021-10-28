package com.souto.workoutapp.exercises;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.souto.workoutapp.R;
import com.souto.workoutapp.model.ExerciseModel;

import java.util.ArrayList;
import java.util.Objects;

public class ExerciseCreatorLegs extends AppCompatActivity {

    Button btn_return, btn_create;
    EditText edt_exercise, edt_series, edt_load;
    ArrayList<ExerciseModel> exercises = new ArrayList<>();
    ListView listView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); // Hide Navigation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Hide Status
        Objects.requireNonNull(getSupportActionBar()).hide(); // Hide Action Bar

        getSupportActionBar().setTitle("L E G S  &  A B S");

        setContentView(R.layout.activity_exercise_creator_lats);

        // ------------------------------------------

        // Returns to the list
        btn_return = findViewById(R.id.btn_return);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLegs();
            }
        });

        // Creates the exercise
        btn_create = findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildExercise();
            }
        });

        edt_exercise = findViewById(R.id.create_name);
        edt_series = findViewById(R.id.create_series);
        edt_load = findViewById(R.id.create_load);
        listView = (ListView) findViewById(R.id.lats_exercise_list);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        openLegs();
        finish();
    }

    public void openLegs() {
        Intent intent = new Intent(this, Legs.class);
        startActivity(intent);
        finish();
    }

    public void buildExercise () {

        ExerciseModel exercise = new ExerciseModel(
                edt_exercise.getText().toString(),
                edt_series.getText().toString(),
                edt_load.getText().toString());

        // Send the new exercise to the database
        if (!TextUtils.isEmpty(exercise.getExercise_name()) ||
                !TextUtils.isEmpty(exercise.getSeries()) ||
                !TextUtils.isEmpty(exercise.getWeight())) {

            // Save the object in the database
            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = mDatabase.getReference();

            mRef.child("users").child(mAuth.getUid()).child("legsList").child(exercise.getExercise_name()).setValue(exercise);
            // Toast.makeText(ExerciseCreatorLats.this,"Saved Successfully!",Toast.LENGTH_SHORT).show();
        }
        openLegs();
        finish();
    }
}