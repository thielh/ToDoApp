package com.example.todoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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


    // TEST COMMENT 2

    DatabaseHelper databaseHelper;
    ArrayAdapter<String> mAdapter;
    ListView FirstTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        FirstTask = (ListView)findViewById(R.id.FirstTask);



        loadTaskList();
    }

    private void loadTaskList() {
        ArrayList<String> taskList = databaseHelper.getTaskList();
        if(mAdapter==null) {
            mAdapter = new ArrayAdapter<String>(this, R.layout.row, R.id.task_title, taskList);
            FirstTask.setAdapter(mAdapter);
        }
        else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
    }

    // MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    // ADD A TASK THROUGH THE MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_task) {
            final EditText taskEditText = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Add New Task")
                    .setMessage("What do you want to do next?")
                    .setView(taskEditText)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            databaseHelper.AddNewTask(String.valueOf(taskEditText.getText()));
                            loadTaskList();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
                    dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // DELETE A TASK
    public void DeleteTask(View view) {
        View parent = (View)view.getParent();
        TextView taskTextView = (TextView)parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        databaseHelper.DeleteTask(task);
        loadTaskList();
    }
}