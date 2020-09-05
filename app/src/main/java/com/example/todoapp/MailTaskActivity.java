package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MailTaskActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    private EditText mail_receipient;
    private EditText mail_subject;
    private EditText mail_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailtask);

        mail_receipient = (EditText) findViewById(R.id.mail_receipient);
        mail_subject = (EditText) findViewById(R.id.mail_subject);
        mail_content = (EditText) findViewById(R.id.mail_content);

        // FROM MAIN ACTIVITY; GET THE INFO WHICH TASK I AM AT
        final String task = getIntent().getStringExtra("data");

        // USE CURRENT SELECTED TASK TO FETCH REMAINING DATA FROM ALL COLUMNS
        // Array contains: Description, Status, DueDate, Favourite
        String[] allData = databaseHelper.FetchDataForEdit(task);

        // MOVE THE ARRAY DATA INTO VARIABLES
        String Description = allData[0];
        String DueDate = allData[2];

        // PRE-FILL THE EditText FIELDS WITH THE DB DATA

        mail_subject.setText("here's a ToDo for you: "+Description);
        mail_content.setText("Due on: "+DueDate);

        Button buttonMail = findViewById(R.id.button_mailtask);
        buttonMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

    }

    public void sendMail() {
        String receipient = mail_receipient.getText().toString();
        String subject = mail_subject.getText().toString();
        String message = mail_content.getText().toString();

        // PASS OUR VARIABLES TO THE EMAIL INTENT
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, receipient);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        // TELL OUR APP THAT WE ONLY WANT TO OPEN E-MAIL CLIENTS
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an e-mail Client"));
    }
}
