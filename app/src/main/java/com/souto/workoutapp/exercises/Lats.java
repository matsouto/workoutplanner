package com.souto.workoutapp.exercises;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.souto.workoutapp.R;
import com.souto.workoutapp.auth.Register;
import com.souto.workoutapp.model.CustomAdapter;
import com.souto.workoutapp.model.ExerciseModel;

import java.util.ArrayList;
import java.util.Objects;

public class Lats extends AppCompatActivity {

    private FirebaseAuth mAuth;

    public Button btn_add;
    public ArrayList<ExerciseModel> exercises = new ArrayList<>();
    public ListView listView;

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

        listView = findViewById(R.id.lats_exercise_list);
        mAuth = FirebaseAuth.getInstance();

        // Reads the data from the database to create the ListView
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDatabase.getReference().child("users").child(mAuth.getUid()).child("latsList");

        // Sets the listener to load the database exercise list
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(Lats.this,"Loading Your Exercises!",Toast.LENGTH_SHORT).show();
                // Iterates for each item in the mRef database path
                for (DataSnapshot item_snapshot : snapshot.getChildren()) {
                    // Gets data from firebase and associates it to the exercise object
                    ExerciseModel exercise = item_snapshot.getValue(ExerciseModel.class);
                    // Add the exercise to the exercises arraylist
                    exercises.add(exercise);
                }
                // Sets the adapter and creates the listview
                CustomAdapter mCustomAdapter = new CustomAdapter(Lats.this,exercises);
                listView.setAdapter(mCustomAdapter);

                if (exercises.isEmpty()){
                    Toast.makeText(Lats.this,"You Don't Have Exercises!",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Lats.this,"Exercises Loaded!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void openExerciseCreatorLats() {
        Intent intent = new Intent(this, ExerciseCreatorLats.class);
        startActivity(intent);
    }
}