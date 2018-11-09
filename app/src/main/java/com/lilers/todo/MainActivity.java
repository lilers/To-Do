package com.lilers.todo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import static com.lilers.todo.AddAndEditTaskActivity.TASK_ID;
import static com.lilers.todo.AddAndEditTaskActivity.TASK_TITLE;
import static com.lilers.todo.AddAndEditTaskActivity.TASK_INFO;
import static com.lilers.todo.AddAndEditTaskActivity.TASK_DUE_DATE;
import static com.lilers.todo.AddAndEditTaskActivity.TASK_PRIORITY;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_REQUEST_CODE = 1;
    public static final int EDIT_REQUEST_CODE = 2;

    private TaskViewModel taskViewModel;
    private RecyclerView toDoRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDoRV = findViewById(R.id.toDoRV);
        toDoRV.setLayoutManager(new LinearLayoutManager(this));
        // RecyclerView optimization since size will always match parent
        toDoRV.setHasFixedSize(true);
        final TaskAdapter adapter = new TaskAdapter();
        toDoRV.setAdapter(adapter);
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                adapter.submitList(tasks);
            }
        });

        // Swipe to remove
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder
                    viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Task task = adapter.getTaskAt(viewHolder.getAdapterPosition());
                Toast.makeText(getApplicationContext(), task.getTitle() + " successfully " +
                        "removed", Toast.LENGTH_SHORT).show();
                taskViewModel.delete(task);
            }
        }).attachToRecyclerView(toDoRV);

        // Edit item
        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Intent intent = new Intent(MainActivity.this, AddAndEditTaskActivity.class);
                intent.putExtra(TASK_ID, task.getId());
                intent.putExtra(TASK_TITLE, task.getTitle());
                intent.putExtra(TASK_INFO, task.getInfo());
                intent.putExtra(TASK_DUE_DATE, task.getDueDate());
                intent.putExtra(TASK_PRIORITY, task.getPriority());

                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteBtn:
                taskViewModel.deleteAllTasks();
                Toast.makeText(getApplicationContext(), "All tasks deleted", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.addBtn:
                Intent intent = new Intent(MainActivity.this, AddAndEditTaskActivity.class);
                startActivityForResult(intent, ADD_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((resultCode == RESULT_OK) && (requestCode == ADD_REQUEST_CODE)) {
            Task task = new Task();
            task.setTitle(data.getStringExtra(TASK_TITLE));
            task.setInfo(data.getStringExtra(TASK_INFO));
            task.setDueDate(data.getStringExtra(TASK_DUE_DATE));
            task.setPriority(data.getStringExtra(TASK_PRIORITY));
            taskViewModel.insert(task);

            Toast.makeText(getApplicationContext(), task.getTitle() + " added to list",
                    Toast.LENGTH_SHORT).show();
        } else if ((resultCode == RESULT_OK) && (requestCode == EDIT_REQUEST_CODE)) {
            int id = data.getIntExtra(TASK_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Error, can't update a task that doesn't exist",
                        Toast.LENGTH_SHORT).show();
            } else {
                Task task = new Task();
                task.setId(id);
                task.setTitle(data.getStringExtra(TASK_TITLE));
                task.setInfo(data.getStringExtra(TASK_INFO));
                task.setDueDate(data.getStringExtra(TASK_DUE_DATE));
                task.setPriority(data.getStringExtra(TASK_PRIORITY));
                taskViewModel.update(task);
                Toast.makeText(this, "Task successfully updated", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
