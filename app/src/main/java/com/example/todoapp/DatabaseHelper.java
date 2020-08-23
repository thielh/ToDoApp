package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="tasks_2";
    private static final Integer DB_VER=1;
    public static final String DB_TABLE="table_task";
    public static final String DB_COL_NAME="TaskName";
    public static final String DB_COL_DESC = "Description";
    public static final String DB_COL_STATUS ="Status";
    public static final String DB_COL_DUE ="DueDate";
    public static final String DB_COL_FAV = "Favourite";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    // define Database access
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE table_task (ID INTEGER PRIMARY KEY AUTOINCREMENT, TaskName TEXT, Description TEXT, Status TEXT, DueDate TEXT, Favourite TEXT)";
        db.execSQL(query);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS table_task");
        onCreate(db);
    }

    // ADD NEW TASK
    public void AddNewTask (String taskName, String taskDes, String taskStatus, String taskDue, String taskFav) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COL_NAME, taskName);
        values.put(DB_COL_DESC, taskDes);
        values.put(DB_COL_STATUS, taskStatus);
        values.put(DB_COL_DUE, taskDue);
        values.put(DB_COL_FAV, taskFav);
        db.insert(DB_TABLE, null, values);
        db.close();
    }


    // DELETE A TASK
    public void DeleteTask (String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + DB_TABLE + " WHERE " + DB_COL_NAME + " = '"+task+"';");
        db.close();
    }

    // SHOW DATABASE LIST
    public ArrayList<String> getTaskList() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Cursor ResultCursor = db.query(DB_TABLE, new String[] {DB_COL_NAME}, null, null, null, null, null);
        Cursor ResultCursor = db.query(DB_TABLE, new String[] {DB_COL_NAME}, null, null, null, null, null);
        while(ResultCursor.moveToNext()) {
            int index = ResultCursor.getColumnIndex(DB_COL_NAME);
            taskList.add(ResultCursor.getString(index));
        }
        ResultCursor.close();
        db.close();
        return taskList;
    }

}
