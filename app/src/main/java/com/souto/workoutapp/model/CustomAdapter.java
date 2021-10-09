package com.souto.workoutapp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.souto.workoutapp.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<ExerciseModel> exercises = new ArrayList<>();

    public CustomAdapter(Context context, ArrayList<ExerciseModel> exercises) {
        mContext = context;
        this.exercises = exercises;
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Object getItem(int position) {
        return exercises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.exercise_list_item,parent,false);
        }

        ExerciseModel tempExercise = (ExerciseModel) getItem(position);

        TextView series = (TextView) convertView.findViewById(R.id.exercise_series);
        TextView name = (TextView) convertView.findViewById(R.id.exercise_name);
        TextView load = (TextView) convertView.findViewById(R.id.exercise_load);

        series.setText(tempExercise.getSeries());
        name.setText(tempExercise.getExercise_name());
        load.setText(tempExercise.getWeight());

        return convertView;
    }
}
