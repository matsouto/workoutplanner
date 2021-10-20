package com.souto.workoutapp.model;

public class ImageModel {

    private String ImageDate;
    private String ImageUri;

    public ImageModel() {
    }

    public ImageModel(String imageDate, String imageUri) {
        ImageDate = imageDate;
        ImageUri = imageUri;
    }

    public String getImageDate() {
        return ImageDate;
    }

    public void setImageDate(String imageDate) {
        ImageDate = imageDate;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }
}
