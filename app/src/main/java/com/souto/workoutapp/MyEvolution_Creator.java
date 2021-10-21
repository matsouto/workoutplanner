package com.souto.workoutapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.souto.workoutapp.model.ImageModel;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MyEvolution_Creator extends AppCompatActivity {

    private FirebaseAuth mAuth;

    public Button btn_camera, btn_gallery;
    public ImageView img_test;
    public ProgressBar progressBar;

    // Photo taken by the user is stored in this variable
    private File photoFile;
    private static final String FILE_NAME = "photo.jpg";

    private static final int CAPTURE_IMAGE_REQUEST = 100;
    private static final int PICK_IMAGE_REQUEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); // Hide Navigation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); // Hide Status

        Objects.requireNonNull(getSupportActionBar()).setTitle("M Y  E V O L U T I O N");

        setContentView(R.layout.activity_my_evolution_creator);
        //-----------------------------------------------------

        // If the user haven't allowed the camera permission yet, request it
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }

        img_test = findViewById(R.id.img_test);

        btn_camera = findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    takePicture();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_gallery = findViewById(R.id.btn_gallery);
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        progressBar = findViewById(R.id.progress_bar);
    }

    void takePicture() throws IOException {
        // Intent to capture the image
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFile(FILE_NAME);

        // Creates a Uri for the taken photo
        Uri fileProvider = FileProvider.getUriForFile(this, "com.souto.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // Check if there is a camera
        if(intent.resolveActivity(this.getPackageManager()) != null) {
            startActivityForResult(intent, CAPTURE_IMAGE_REQUEST);
        }else{
            Toast.makeText(this,"Unable to open camera!",Toast.LENGTH_SHORT).show();
        }
    }

    private File getPhotoFile(String fileName) throws IOException {
        // Gets taken photo and create a temporary file in the directory, returns the file
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(fileName,".jpg",storageDirectory);
    }

    void openFileChooser() {
        Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    // Sets image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // To take a photo
        if(requestCode==CAPTURE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            Glide.with(this).load(photoFile).into(img_test);
            Uri fileProvider = FileProvider.getUriForFile(this, "com.souto.fileprovider", photoFile);
            uploadImageFirebase(fileProvider);
        }

        // To select a photo from gallery
        if(requestCode==PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri mImageUri = (Uri) data.getData();
            Glide.with(this).load(mImageUri).into(img_test);
            uploadImageFirebase(mImageUri);
        }
    }

    // Gets image Uri and sends it to FBStorage
    private void uploadImageFirebase(Uri imageUri) {

        Uri mImageUri = imageUri;
        // Gets firebase authentication
        mAuth = FirebaseAuth.getInstance();

        // Gets the current time to name the photos in storage
        Date currentTime = Calendar.getInstance().getTime();

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = mDatabase.getReference().child("users").child(mAuth.getUid()).child("pics");

        // Gets firebase storage reference
        FirebaseStorage mStorage = FirebaseStorage.getInstance();
        StorageReference mRef = mStorage.getReference().child("users").child(mAuth.getUid()).child(""+currentTime);

        progressBar.setVisibility(View.VISIBLE);

        // Uploads file to storage
        mRef.putFile(mImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    // Delays the progress bar reset so the user can see it on 100%
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                progressBar.setProgress(0);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        },1000);

                        // O PROBLEMA TA AQUI NA HORA DE COLOCAR A URI ESSA URI TA ERRADA
                        ImageModel image = new ImageModel("" + currentTime, taskSnapshot.getUploadSessionUri().toString());
                        mDatabaseRef.child("" + currentTime).setValue(image);
                        Toast.makeText(MyEvolution_Creator.this,"Uploaded successfully to database!",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MyEvolution_Creator.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        // Gives the progress percentage of the bytes transferred and makes it visible
                        double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        progressBar.setProgress((int) progress);
                    }
                });
    }
}