package com.souto.workoutapp.exercises;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class Lats extends AppCompatActivity {

    private FirebaseAuth mAuth;

    public Button btn_add;
    public ArrayList<ExerciseModel> exercises = new ArrayList<>();
    public ListView listView;
    public TextView exercise_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); // Hide Navigation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Hide Status

        getSupportActionBar().setTitle("L A T S  &  B I C E P S");

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

        exercise_name = findViewById(R.id.exercise_name);

        listView = findViewById(R.id.lats_exercise_list);
        mAuth = FirebaseAuth.getInstance();

        // Reads the data from the database to create the ListView
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDatabase.getReference().child("users").child(mAuth.getUid()).child("latsList");
        Toast.makeText(Lats.this,"Loading Your Exercises!",Toast.LENGTH_SHORT).show();

        // Sets the listener to load the database exercise list
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exercises.clear();
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

                // Excludes the items when clicked
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // Gets the clicked view, gets the specific exercise_name view, casts into textview
                        TextView text = (TextView)view.findViewById(R.id.exercise_name);

                        // Strikethrough the selected item
                        // text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                        // Shows message of which exercise you have completed
                        String exercise_to_delete = text.getText().toString();
                        Toast.makeText(Lats.this, exercise_to_delete + " done!", Toast.LENGTH_SHORT).show();

                        // Removes the clicked item and than updates the adapter
                        exercises.remove(position);
                        mCustomAdapter.notifyDataSetChanged();

                    }
                });

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        // Gets the clicked view, gets the specific exercise_name view, casts into textview
                        TextView text = (TextView)view.findViewById(R.id.exercise_name);
                        String exercise_to_delete = text.getText().toString();

                        // Creates the Alert Dialog with the delete options
                        AlertDialog.Builder builder = new AlertDialog.Builder(Lats.this);
                        builder.setCancelable(true);
                        builder.setTitle("Delete");
                        builder.setMessage("Do you want to delete the exercise permanently?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mRef.child(""+exercise_to_delete).removeValue();
                                Toast.makeText(Lats.this, exercise_to_delete + " removed from database!", Toast.LENGTH_SHORT).show();
                                mCustomAdapter.notifyDataSetChanged();

                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                        // Return true to not trigger the short click listener
                        return true;
                    }
                });

                if (exercises.isEmpty()){
                    Toast.makeText(Lats.this,"You Don't Have Exercises!",Toast.LENGTH_SHORT).show();
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