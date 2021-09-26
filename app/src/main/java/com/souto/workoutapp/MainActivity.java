package com.souto.workoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.souto.workoutapp.auth.Login;
import com.souto.workoutapp.auth.Register;

import java.util.Objects;

// MENU

public class MainActivity extends AppCompatActivity {

    // Identifies if there is already a authentication session running

    private FirebaseAuth mAuth;

    public Button btn_planner;
    public Button btn_logout;
    public TextView txt_hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); // Hide Navigation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // Hide Status
        Objects.requireNonNull(getSupportActionBar()).hide(); // Hide Action Bar

        setContentView(R.layout.activity_main);

        //----------------------------------------------

        // Setting the callback for the planner button
        mAuth = FirebaseAuth.getInstance();
        txt_hello = findViewById(R.id.txt_hello);

        /* NAO TA FUNCIONANDO N SEI PQ
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mDatabase.getReference().child("users").child(mAuth.getUid());

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String helloName = "Hello, " + snapshot.child("name").getValue().toString();
                txt_hello.setText(helloName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        */

        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                openLogin();
            }
        });

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

    public void openLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    private void startPlanner() {
        Intent intent_planner = new Intent(this, WorkoutPlanner.class);
        startActivity(intent_planner);
        finish();
    }
}