package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Boolean.TRUE;

public class AddTaskActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        final EditText enter_name = (EditText) findViewById(R.id.enter_name);
        final EditText enter_description = (EditText) findViewById(R.id.enter_description);
        final EditText enter_date = (EditText) findViewById(R.id.enter_date);
        final CheckBox enter_checkFav = (CheckBox) findViewById(R.id.enter_checkFav);
        Button button_add = (Button) findViewById(R.id.button_addtask);

        // TODO: restrict format for user to enter date: xx.xx.xxxx

        button_add.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view){
                String Status = "FALSE";
                String checkFav = String.valueOf(enter_checkFav.isChecked());
                databaseHelper.AddNewTask(enter_name.getText().toString(), enter_description.getText().toString(), Status, enter_date.getText().toString(), checkFav);

                Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                AddTaskActivity.this.startActivity(intent);
        }
    });

    }






}
