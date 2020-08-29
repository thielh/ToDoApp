package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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

    // CHANGE A TASK
    public void ChangeTask (String old_taskName, String taskName, String taskDes, String taskStatus, String taskDue, String taskFav) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COL_NAME, taskName);
        values.put(DB_COL_DESC, taskDes);
        values.put(DB_COL_STATUS, taskStatus);
        values.put(DB_COL_DUE, taskDue);
        values.put(DB_COL_FAV, taskFav);
        db.update(DB_TABLE, values, "TaskName= '"+old_taskName+"'", null);
        db.close();
    }


    // DELETE A TASK
    public void DeleteTask (String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + DB_TABLE + " WHERE " + DB_COL_NAME + " = '"+task+"';");
        db.close();
    }

    // 24.08.2020
    // FETCH DATA FOR 1 SELECTED TASK
    public String[] FetchData(String task) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultCursor = db.rawQuery("Select * from " +DB_TABLE+ " WHERE " +DB_COL_NAME+ " = '" +task+ "';",null);
        resultCursor.moveToFirst();
        String Description = resultCursor.getString(2);
        String Status = resultCursor.getString(3);
        String DueDate = resultCursor.getString(4);
        String Favourite = resultCursor.getString(5);

        String[] allData = {Description, Status, DueDate, Favourite};
        return allData;
    }

    // 29.08.2020
    // FETCH ALL DATA
    public Cursor getListContents(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+DB_TABLE, null);
        return cursor;
    }

}
