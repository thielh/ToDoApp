package com.example.todoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ArrayAdapter<String> mAdapter;
    ListView FirstTask;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        FirstTask = (ListView)findViewById(R.id.FirstTask);


        loadTaskList();
    }

    // TASK LIST IS LOADED, METHOD CALLED IN THE ONCREATE & ADDTASK & DELETETASK & TODO: CHANGETASK
    public void loadTaskList() {
        ArrayList<String> taskList = databaseHelper.getTaskList();
        if(mAdapter==null) {
            mAdapter = new ArrayAdapter<>(this, R.layout.row, R.id.task_title, taskList);
            FirstTask.setAdapter(mAdapter);  // FirstTask is the ListView in the row.xml
        }
        else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
    }

    // MENU AT THE TOP OF SCREEN
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // JUMP TO AddTaskActivity VIA MENU ITEM (BUTTON +)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add_task:
                Intent intent = new Intent(this, AddTaskActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    // JUMP TO EditTaskActivity VIA EDIT BUTTON
    public void onClickEditTask(View view) {
        Intent intent = new Intent(this, EditTaskActivity.class);
        this.startActivity(intent);
    }


    // DELETE BUTTON
    public void DeleteTask(View view) {
        View parent = (View)view.getParent();
        TextView taskTextView = (TextView)parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        databaseHelper.DeleteTask(task);
        loadTaskList();
    }
}