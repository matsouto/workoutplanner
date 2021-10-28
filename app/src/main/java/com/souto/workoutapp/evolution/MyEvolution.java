package com.souto.workoutapp.evolution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.souto.workoutapp.R;
import com.souto.workoutapp.exercises.Lats;
import com.souto.workoutapp.model.ImageAdapter;
import com.souto.workoutapp.model.ImageModel;

import java.util.ArrayList;
import java.util.Objects;

public class MyEvolution extends AppCompatActivity {

    public Button btn_ME_add;
    public RecyclerView mRecyclerView;

    private FirebaseAuth mAuth;
    private ArrayList<ImageModel> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); // Hide Navigation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // Hide Status

        Objects.requireNonNull(getSupportActionBar()).setTitle("M Y  E V O L U T I O N");

        setContentView(R.layout.activity_my_evolution);
        //-----------------------------------------------------

        btn_ME_add = findViewById(R.id.btn_ME_add);
        btn_ME_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMECreator();
            }
        });

        mRecyclerView = findViewById(R.id.my_evolution_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();

        // Reads the data from the storage to create the ListView
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = mDatabase.getReference().child("users").child(mAuth.getUid()).child("pics");


//        FirebaseStorage mStorage = FirebaseStorage.getInstance();
//        StorageReference mStorageRef = mStorage.getReference().child("users").child(mAuth.getUid()).child("pics");
        Toast.makeText(MyEvolution.this,"Loading Your Photos!",Toast.LENGTH_SHORT).show();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Clears the array to not have duplicates when a new entry is made
                imageList.clear();
                // Iterates for each item in the mRef database path
                for (DataSnapshot item_snapshot : snapshot.getChildren()) {
                    // Gets data from firebase and associates it to the imageModel object
                    ImageModel image = item_snapshot.getValue(ImageModel.class);
                    // Add the exercise to the exercises arraylist
                    imageList.add(image);
                }
                ImageAdapter mAdapter = new ImageAdapter(MyEvolution.this, imageList);
                mRecyclerView.setAdapter(mAdapter);

                if (imageList.isEmpty()){
                    Toast.makeText(MyEvolution.this,"You Don't Have Images!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyEvolution.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startMECreator() {
        Intent intent = new Intent(this,MyEvolution_Creator.class);
        startActivity(intent);
    }
}