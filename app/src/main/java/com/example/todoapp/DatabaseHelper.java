package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="tasks";
    private static final Integer DB_VER=1;
    public static final String DB_TABLE="table_task";
    public static final String DB_COLUMN="TaskName";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    // define Database access
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE table_task (ID INTEGER PRIMARY KEY AUTOINCREMENT, TaskName TEXT);");
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    // ADD NEW TASK
    public void AddNewTask (String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN, task);
        db.insertWithOnConflict(DB_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    // DELETE A TASK
    public void DeleteTask (String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, DB_COLUMN + "=?", new String[] {task});
        db.close();
    }

    // SHOW DATABASE LIST???
    public ArrayList<String> getTaskList() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor ResultCursor = db.query(DB_TABLE, new String[] {DB_COLUMN}, null, null, null, null, null);
        while(ResultCursor.moveToNext()) {
            int index = ResultCursor.getColumnIndex(DB_COLUMN);
            taskList.add(ResultCursor.getString(index));
        }
        ResultCursor.close();
        db.close();
        return taskList;
    }

}
