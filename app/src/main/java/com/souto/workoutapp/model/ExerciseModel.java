package com.souto.workoutapp.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ExerciseModel {

    String exercise_name;
    String series;
    String weight;

    // Empty constructor
    public ExerciseModel() {

    }

    // Specific constructor
    public ExerciseModel(String exercise_name, String series, String weight) {
        this.exercise_name = exercise_name;
        this.series = series;
        this.weight = weight;
    }


    public String getExercise_name() {
        return exercise_name;
    }

    public void setExercise_name(String exercise_name) {
        this.exercise_name = exercise_name;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

}


