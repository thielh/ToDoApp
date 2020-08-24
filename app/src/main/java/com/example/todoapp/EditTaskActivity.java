package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class EditTaskActivity extends AppCompatActivity {


    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittask);

        final EditText edit_name = (EditText) findViewById(R.id.edit_name);
        final EditText edit_description = (EditText) findViewById(R.id.edit_description);
        final EditText edit_date = (EditText) findViewById(R.id.edit_date);
        final CheckBox edit_checkDone = (CheckBox) findViewById(R.id.edit_checkDone);
        final CheckBox edit_checkFav = (CheckBox) findViewById(R.id.edit_checkFav);

        // FROM MAIN ACTIVITY; GET THE INFO WHICH TASK I AM AT
        final String task = getIntent().getStringExtra("data");

        // USE CURRENT SELECTED TASK TO FETCH REMAINING DATA FROM ALL COLUMNS
        // Array contains: Description, Status, DueDate, Favourite
        String[] allData = databaseHelper.FetchData(task);

        // MOVE THE ARRAY DATA INTO VARIABLES
        String Description = allData[0];
        boolean Status = Boolean.parseBoolean(allData[1]);
        String DueDate = allData[2];
        boolean Favourite = Boolean.parseBoolean(allData[3]);

        // PRE-FILL THE EditText FIELDS WITH THE DB DATA
        edit_name.setText(task);
        edit_description.setText(Description);
        edit_date.setText(DueDate);
        edit_checkDone.setChecked(Status);
        edit_checkFav.setChecked(Favourite);

        /* -------------------- */
        // SAVE BUTTON STARTS HERE

        // SAME AS IN AddTask

        Button button_save = (Button) findViewById(R.id.button_edittask);

        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // from CheckBox to Boolean to String
                String checkDone = String.valueOf(edit_checkDone.isChecked());
                String checkFav = String.valueOf(edit_checkFav.isChecked());
                databaseHelper.ChangeTask(task, edit_name.getText().toString(), edit_description.getText().toString(), checkDone, edit_date.getText().toString(), checkFav);

                Intent intent = new Intent(EditTaskActivity.this, MainActivity.class);
                EditTaskActivity.this.startActivity(intent);
            }
        });

    }
}
