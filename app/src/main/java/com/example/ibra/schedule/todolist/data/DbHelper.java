package com.example.ibra.schedule.todolist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.ibra.schedule.todolist.data.ToDoListContract;

/**
 * edit version
 * Created by ibra on 10/26/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todolist.db";
    private static final int DATABASE_VERSION = 4;

    public DbHelper(Context context){
        super(context , DATABASE_NAME , null , DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_TODOLIST_TABLE = "CREATE TABLE " +
                ToDoListContract.ToDoListEntry.TABLE_NAME + " (" +
                ToDoListContract.ToDoListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ToDoListContract.ToDoListEntry.COLUMN_TASK + " TEXT NOT NULL, " +
                ToDoListContract.ToDoListEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        db.execSQL(SQL_CREATE_TODOLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ ToDoListContract.ToDoListEntry.TABLE_NAME);
        onCreate(db);

    }
}
