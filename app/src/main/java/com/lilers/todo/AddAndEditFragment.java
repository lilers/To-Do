package com.lilers.todo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddAndEditFragment extends Fragment {
    private EditText taskET, infoET, dueDateET;
    private Spinner prioritySpinner;
    private SharedViewModel sharedViewModel;
    private Task currentTask;

    public AddAndEditFragment() {
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_and_edit, container, false);
        taskET = view.findViewById(R.id.taskET);
        infoET = view.findViewById(R.id.infoET);
        dueDateET = view.findViewById(R.id.dueDateET);
        prioritySpinner = view.findViewById(R.id.prioritySpinner);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        sharedViewModel.getTask().observe(getViewLifecycleOwner(), new Observer<Task>() {
            @Override
            public void onChanged(@Nullable Task task) {
                if (task != null) {
                    getActivity().setTitle("Edit Task");
                    taskET.setText(task.getTitle());
                    infoET.setText(task.getInfo());
                    dueDateET.setText(task.getDueDate());
                    prioritySpinner.setSelection(getIndexOf(task.getPriority()));
                    currentTask = task;
                } else {
                    getActivity().setTitle("Add Task");
                    currentTask = new Task();
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_and_edit_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveBtn:
                saveTask();
                return true;
            case R.id.cancelBtn:
                MainActivity.fM.popBackStack();
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
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            currentTask.setTitle(title);
            currentTask.setInfo(info);
            currentTask.setDueDate(dueDate);
            currentTask.setPriority(priority);
            sharedViewModel.setTask(currentTask);

            MainActivity.fM.popBackStack();
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
