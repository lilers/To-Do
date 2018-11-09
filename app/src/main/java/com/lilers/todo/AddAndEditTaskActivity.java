package com.lilers.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddAndEditTaskActivity extends AppCompatActivity {
    public static final String TASK_ID = "taskID";
    public static final String TASK_TITLE = "taskTitle";
    public static final String TASK_INFO = "taskInfo";
    public static final String TASK_DUE_DATE = "taskDueDate";
    public static final String TASK_PRIORITY = "taskPriority";

    private EditText taskET, infoET, dueDateET;
    private Spinner prioritySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit_task);

        taskET = findViewById(R.id.taskET);
        infoET = findViewById(R.id.infoET);
        dueDateET =findViewById(R.id.dueDateET);
        prioritySpinner = findViewById(R.id.prioritySpinner);

        Intent intent = getIntent();
        if (intent.hasExtra(TASK_ID)) {
            setTitle("Edit Task");
            taskET.setText(intent.getStringExtra(TASK_TITLE));
            infoET.setText(intent.getStringExtra(TASK_INFO));
            dueDateET.setText(intent.getStringExtra(TASK_DUE_DATE));
            prioritySpinner.setSelection(getIndexOf(intent.getStringExtra(TASK_PRIORITY)));
        } else {
            setTitle("Add Task");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_and_edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveBtn:
                saveTask();
                return true;
            case R.id.cancelBtn:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveTask() {
        String title = taskET.getText().toString(), info = infoET.getText().toString(),
                dueDate = dueDateET.getText().toString(),
                priority = prioritySpinner.getSelectedItem().toString();

        if (title.trim().isEmpty() || info.trim().isEmpty() || dueDate.trim().isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra(TASK_TITLE, title);
            intent.putExtra(TASK_INFO, info);
            intent.putExtra(TASK_DUE_DATE, dueDate);
            intent.putExtra(TASK_PRIORITY, priority);

            // Update correct task if it exists
            int id = getIntent().getIntExtra(TASK_ID, -1);
            if (id != -1) {
                intent.putExtra(TASK_ID, id);
            }

            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /*
     * Gets the index of the priority string
     */
    private int getIndexOf(String string) {
        switch (string) {
            case "Low":
                return 0;
            case "Medium":
                return 1;
            case "High":
                return 2;
            default:
                return 0;
        }
    }
}
