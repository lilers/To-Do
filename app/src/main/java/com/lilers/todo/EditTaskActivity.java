package com.lilers.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.lilers.todo.MainActivity.TASK_POS;
import static com.lilers.todo.MainActivity.TASK_TEXT;

public class EditTaskActivity extends AppCompatActivity {
    EditText editET;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        editET = findViewById(R.id.editET);
        editET.setText(getIntent().getStringExtra(TASK_TEXT));
        pos = getIntent().getIntExtra(TASK_POS, 0);
    }

    /*
     * Save edited task when save is clicked
     */
    public void saveTask(View v) {
        Intent intent = new Intent();
        intent.putExtra(TASK_TEXT, editET.getText().toString());
        intent.putExtra(TASK_POS, pos);
        setResult(RESULT_OK, intent);
        finish();
    }
}
