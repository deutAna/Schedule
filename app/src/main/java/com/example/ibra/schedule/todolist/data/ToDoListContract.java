package com.example.ibra.schedule.todolist.data;

import android.provider.BaseColumns;

/**
 * Created by ibra on 10/26/2017.
 */

public class ToDoListContract {


    public static final class ToDoListEntry implements BaseColumns {

        public static final String TABLE_NAME= "todolist";
        public static final String COLUMN_TASK = "todotask";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }
}
