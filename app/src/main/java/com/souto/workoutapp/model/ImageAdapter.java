package com.souto.workoutapp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.souto.workoutapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    Context mContext;
    ArrayList<ImageModel> imageList = new ArrayList<>();

    public ImageAdapter(Context context, ArrayList<ImageModel> images) {
        mContext = context;
        imageList = images;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.evolution_list_item,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageModel currentImage = imageList.get(position);
        holder.textDateView.setText(currentImage.getImageDate());
        // AQUI TA DANDO PROBLEMA N TA LENDO A URI
//        Glide.with(mContext).load(currentImage.getImageUri()).placeholder(R.drawable.bodybuilder_icon).into(holder.imageView);

        //--------------------------------------------------------------
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Gets firebase storage reference
        FirebaseStorage mStorage = FirebaseStorage.getInstance();
        StorageReference mRef = mStorage.getReference().child("users").child(mAuth.getUid()).child(currentImage.getImageDate());

        try {
            final File localFile = File.createTempFile(currentImage.getImageDate(), "jpg");
            mRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Glide.with(mContext).load(localFile).placeholder(R.drawable.ic_person).into(holder.imageView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        //----------------------------------------------------------------------------
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textDateView;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textDateView = itemView.findViewById(R.id.photo_date);
            imageView = itemView.findViewById(R.id.img_photo);
        }
    }
}
