package com.souto.workoutapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;

public class MyEvolution extends AppCompatActivity {

    private FirebaseAuth mAuth;

    public Button btn_camera, btn_gallery;
    public ImageView img_test;

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

        setContentView(R.layout.activity_my_evolution);
        //-----------------------------------------------------

        // Gets firebase authentication
        mAuth = FirebaseAuth.getInstance();

        // Gets firebase storage reference
        FirebaseStorage mStorage = FirebaseStorage.getInstance();
        StorageReference mRef = mStorage.getReference().child("users").child(mAuth.getUid());


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
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            img_test.setImageBitmap(bitmap);

//            Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            Glide.with(this).load(photoFile).into(img_test);
        }

        // To select a photo from gallery
        if(requestCode==PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri mImageUri = (Uri) data.getData();
            Glide.with(this).load(mImageUri).into(img_test);
        }
    }
}