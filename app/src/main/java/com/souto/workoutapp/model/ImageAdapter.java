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
import com.souto.workoutapp.R;

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
        Glide.with(mContext).load(currentImage.getImageUri()).placeholder(R.drawable.circle).into(holder.imageView);
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
