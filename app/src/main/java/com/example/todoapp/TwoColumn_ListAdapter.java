package com.example.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TwoColumn_ListAdapter extends ArrayAdapter<Task> {

    private LayoutInflater mInflater;
    private ArrayList<Task> tasks;
    private int mViewResourceId;

    public TwoColumn_ListAdapter(Context context, int textViewResourceId, ArrayList<Task> tasks) {
        super(context, textViewResourceId, tasks);
        this.tasks = tasks;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }


    public View getView(int position, View convertView, ViewGroup parents) {
        convertView = mInflater.inflate(mViewResourceId, null);

        Task task = tasks.get(position);

        if(task != null) {
            TextView taskName = (TextView) convertView.findViewById(R.id.task_title);
            TextView taskDue = (TextView) convertView.findViewById(R.id.task_duedate);

            if(taskName != null){
                taskName.setText(task.getTaskName());
            }
            if(taskDue != null){
                taskDue.setText(task.getTaskDueDate());
            }
        }
        return convertView;
    }
}
