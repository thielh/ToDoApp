package com.example.todoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ArrayList<Task> taskList;
    ListView FirstTask;
    ListView listView;
    Task task;

    public String StatusSortAndFilter ="empty";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadTaskList();
    }


    public void loadTaskList(){
        databaseHelper = new DatabaseHelper(this);

        // 29.08.2020
        // TEST
        taskList = new ArrayList<>();
        Cursor cursor = databaseHelper.getListContents(StatusSortAndFilter);

        // get amount of data rows pulled from DB
        int numRows = cursor.getCount();
        // make sure at least 1 row of data is selected;
        if (numRows == 0){
            Toast.makeText(MainActivity.this, "there is nothing in the Database", Toast.LENGTH_LONG).show();
        } else {
            while(cursor.moveToNext()) {
                task = new Task(cursor.getString(1), cursor.getString(4));
                taskList.add(task);
            }
            TwoColumn_ListAdapter adapter = new TwoColumn_ListAdapter(this, R.layout.row, taskList);
            listView = (ListView) findViewById(R.id.FirstTask);
            listView.setAdapter(adapter);
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
            case R.id.action_filter1:
                StatusSortAndFilter = "DueToday";
                break;
            case R.id.action_filter2:
                StatusSortAndFilter = "SortDueDate";
                break;
            case R.id.action_filter3:
                StatusSortAndFilter = "Fav";
                break;
            case R.id.action_filter4:
                StatusSortAndFilter = "Done";
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        loadTaskList();
        return true;
    }

        /* TODO: 30.08.2020
        IF onClickFilter1 == TRUE,                      // SWITCH???
        Cursor cursor = databaseHelper.getListContentsForFilter1();
        IF onClickFilter2 == TRUE,
        Cursor cursor = databaseHelper.getListContentsForFilter2();
         else */

    // JUMP TO EditTaskActivity VIA EDIT BUTTON
    public void onClickEditTask(View view) {
        View parent = (View)view.getParent();
        // which task am I selecting to edit?
        TextView taskTextView = (TextView)parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        Intent intent = new Intent(this, EditTaskActivity.class);
        intent.putExtra("data", task);
        this.startActivity(intent);
    }


    // DELETE BUTTON
    public void onClickDeleteTask(View view) {
        View parent = (View)view.getParent();
        TextView taskTextView = (TextView)parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        databaseHelper.DeleteTask(task);
        loadTaskList();
    }


    public void onClickMail(View view) {
        View parent = (View)view.getParent();
        // which task am I selecting to edit?
        TextView taskTextView = (TextView)parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        Intent intent = new Intent(this, MailTaskActivity.class);
        intent.putExtra("data", task);
        this.startActivity(intent);
    }
}