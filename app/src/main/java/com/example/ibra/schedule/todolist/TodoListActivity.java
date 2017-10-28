package com.example.ibra.schedule.todolist;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.ibra.schedule.R;
import com.example.ibra.schedule.todolist.data.DbHelper;
import com.example.ibra.schedule.todolist.data.TaskAdapter;
import com.example.ibra.schedule.todolist.data.ToDoListContract;

public class TodoListActivity extends Activity {

    RecyclerView mRecyclerView;
    private SQLiteDatabase mDb;
    private TaskAdapter mAdapter;
    private EditText mTaskEditText;
    FloatingActionButton fabButton;
    LinearLayout layout;
    CheckBox cb;
    ContentValues cv;
    FloatingActionButton fabAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);



        cb = (CheckBox) findViewById(R.id.checkbox);
        mRecyclerView = (RecyclerView) findViewById(R.id.todo_recycler_list);
        mTaskEditText = (EditText)findViewById(R.id.edit_text);
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        DbHelper dbHelper = new DbHelper(this);

        mDb = dbHelper.getWritableDatabase();

        Cursor cursor = getAllTasks();

        mAdapter = new TaskAdapter(this , cursor);

        mRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            // COMPLETED (4) Override onMove and simply return false inside
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //do nothing, we only care about swiping
                return false;
            }

            // COMPLETED (5) Override onSwiped
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // COMPLETED (8) Inside, get the viewHolder's itemView's tag and store in a long variable id
                //get the id of the item being swiped
                long id = (long) viewHolder.itemView.getTag();
                // COMPLETED (9) call removeGuest and pass through that id
                //remove from DB
                removeTask(id);
                // COMPLETED (10) call swapCursor on mAdapter passing in getAllGuests() as the argument
                //update the list
                mAdapter.swapCursor(getAllTasks());
            }

            //COMPLETED (11) attach the ItemTouchHelper to the waitlistRecyclerView
        }).attachToRecyclerView(mRecyclerView);



        fabButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to start an AddTaskActivity
                layout = (LinearLayout) findViewById(R.id.addingLayout);
                layout.setVisibility(View.VISIBLE);

                fabButton.setVisibility(View.INVISIBLE);
                fabAdd.setVisibility(View.VISIBLE);

            }
        });




    }

    public void addToTask(View view){

        if (mTaskEditText.getText().length() == 0 ) {
            return;
        }

        // Add guest info to mDb
        addNewGuest(mTaskEditText.getText().toString());

        // Update the cursor in the adapter to trigger UI to display the new list
        mAdapter.swapCursor(getAllTasks());

        //clear UI text fields

       mTaskEditText.getText().clear();
       layout.setVisibility(View.GONE);

       fabAdd.setVisibility(View.INVISIBLE);
        fabButton.setVisibility(View.VISIBLE);


    }

    private Cursor getAllTasks(){
        return mDb.query(ToDoListContract.ToDoListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ToDoListContract.ToDoListEntry.COLUMN_TIMESTAMP
        );
    }


    private long addNewGuest(String name) {
        cv = new ContentValues();
        cv.put(ToDoListContract.ToDoListEntry.COLUMN_TASK, name);
        return mDb.insert(ToDoListContract.ToDoListEntry.TABLE_NAME, null, cv);
    }


    private boolean removeTask(long id) {
        // COMPLETED (2) Inside, call mDb.delete to pass in the TABLE_NAME and the condition that WaitlistEntry._ID equals id
        return mDb.delete(ToDoListContract.ToDoListEntry.TABLE_NAME, ToDoListContract.ToDoListEntry._ID + "=" + id, null) > 0;
    }

}
