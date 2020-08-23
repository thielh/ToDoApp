package com.example.todoapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditTaskActivity extends AppCompatActivity {


    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittask);

        final EditText edit_name = (EditText) findViewById(R.id.edit_name);
        final EditText edit_description = (EditText) findViewById(R.id.edit_description);
        final EditText edit_date = (EditText) findViewById(R.id.edit_date);
        final CheckBox enter_checkFav = (CheckBox) findViewById(R.id.edit_checkFav);
        Button button_add = (Button) findViewById(R.id.button_edittask);

        // TODO: pre-fill EditText fields with data from DB, user can change them and update DB

    }

}
