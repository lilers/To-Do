package com.lilers.todo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int EDIT_REQUEST_CODE = 2;
    public static final String TASK_TEXT = "taskText";
    public static final String TASK_POS = "taskPos";

    ListView toDoLV;
    ArrayList<String> tasks;
    ArrayAdapter<String> tasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDoLV = findViewById(R.id.toDoLV);
        readTasks();
        tasksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        toDoLV.setAdapter(tasksAdapter);
        setUpLVListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((resultCode == RESULT_OK) && (requestCode == EDIT_REQUEST_CODE)) {
            String updatedTask = data.getExtras().getString(TASK_TEXT);
            int pos = data.getExtras().getInt(TASK_POS);
            tasks.set(pos, updatedTask);
            tasksAdapter.notifyDataSetChanged();
            writeTasks();
            Toast.makeText(this, "Task successfully updated", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Add a task to the list when add button is clicked
     */
    public void addTask(View v) {
        EditText addET = findViewById(R.id.addET);
        String task = addET.getText().toString();
        tasksAdapter.add(task);
        addET.setText("");
        writeTasks();
        Toast.makeText(getApplicationContext(), task + " added to list", Toast.LENGTH_SHORT).show();
    }

    /*
     * Remove/edit a task from the list depending on click type (long = remove, regular = edit)
     */
    private void setUpLVListener() {
        // Remove task
        toDoLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position,
                                           long id) {
                Toast.makeText(getApplicationContext(), tasks.get(position) + " successfully " +
                        "removed", Toast.LENGTH_SHORT).show();
                tasks.remove(position);
                tasksAdapter.notifyDataSetChanged();
                writeTasks();
                return true;
            }
        });

        // Edit task
        toDoLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
                intent.putExtra(TASK_TEXT, tasks.get(position));
                intent.putExtra(TASK_POS, position);
                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });
    }

    /*
     * Get the data file
     */
    private File getDataFile() {
        return new File(getFilesDir(), "toDo.text");
    }

    /*
     * Get tasks for list from data file
     */
    private void readTasks() {
        try {
            tasks = new ArrayList<>(FileUtils.readLines(getDataFile(),
                    Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading file", e);
            tasks = new ArrayList<>();
        }
    }

    /*
     * Update tasks for list to save to data file
     */
    private void writeTasks() {
        try {
            FileUtils.writeLines(getDataFile(), tasks);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing file", e);
        }
    }
}
