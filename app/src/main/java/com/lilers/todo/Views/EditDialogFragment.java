package com.lilers.todo.Views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.lilers.todo.R;
import com.lilers.todo.Models.Task;
import com.lilers.todo.ViewModels.SharedViewModel;

public class EditDialogFragment extends DialogFragment {
    private EditText taskET, infoET, dueDateET;
    private Spinner prioritySpinner;
    private SharedViewModel sharedViewModel;
    private Task currentTask;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_edit_dialog, null);
        taskET = view.findViewById(R.id.taskET);
        infoET = view.findViewById(R.id.infoET);
        dueDateET = view.findViewById(R.id.dueDateET);
        prioritySpinner = view.findViewById(R.id.prioritySpinner);

        AlertDialog.Builder editDialog = new AlertDialog.Builder(getActivity());
        editDialog.setTitle("Edit task");
        editDialog.setView(view);
        editDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveTask();
                dialog.dismiss();
            }
        });
        editDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return editDialog.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_dialog, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        sharedViewModel.getTask().observe(getViewLifecycleOwner(), new Observer<Task>() {
            @Override
            public void onChanged(@Nullable Task task) {
                currentTask = task;
                taskET.setText(task.getTitle());
                infoET.setText(task.getInfo());
                dueDateET.setText(task.getDueDate());
                prioritySpinner.setSelection(getIndexOf(task.getPriority()));
            }
        });
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
