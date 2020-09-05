package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    public String[] FetchDataForEdit(String task) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor resultCursor = db.rawQuery("Select * from " +DB_TABLE+ " WHERE " +DB_COL_NAME+ " = '" +task+ "';",null);
        resultCursor.moveToFirst();
        String Description = resultCursor.getString(2);
        String Status = resultCursor.getString(3);
        String DueDate = resultCursor.getString(4);
        String Favourite = resultCursor.getString(5);

        String[] allData = {Description, Status, DueDate, Favourite};
        db.close();
        return allData;
    }

    // 29.08.2020
    // FETCH ALL DATA
    public Cursor getListContents(String status){
        Log.d("WHAT IS STATUS", status);
        SQLiteDatabase db = this.getReadableDatabase();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat datetoday = new SimpleDateFormat("yyyy.MM.dd");
        String date = datetoday.format(cal.getTime());
        Cursor cursor;
        switch(status) {
            case "DueToday":
                cursor = db.rawQuery("SELECT * FROM "+DB_TABLE+" WHERE "+DB_COL_DUE+" = '"+date+"'", null);
                // Log.d("TEST1", "you clicked due today");
                break;
            case "SortDueDate":
                cursor = db.rawQuery("SELECT * FROM "+DB_TABLE+" ORDER BY "+DB_COL_DUE+" ASC", null);
                // Log.d("TEST2", "you clicked sortby due date");
                break;
            case "Fav":
                cursor = db.rawQuery("SELECT * FROM "+DB_TABLE+" WHERE "+DB_COL_FAV+" = 'true'", null);
                // Log.d("TEST3", "you clicked show favs");
                break;
            case "Done":
                cursor = db.rawQuery("SELECT * FROM "+DB_TABLE+" WHERE "+DB_COL_STATUS+" = 'true'", null);
                // Log.d("TEST4", "you clicked show tasks done");
                break;
            default:
                cursor = db.rawQuery("SELECT * FROM "+DB_TABLE+" WHERE "+DB_COL_STATUS+" = 'false'", null);
        }
        Log.d("WHAT IS CURSOR", String.valueOf(cursor.getCount()));
        db.close();
        return cursor;
    }

    // 30.08.2020
    // FETCH ONLY DATA FOR

}
