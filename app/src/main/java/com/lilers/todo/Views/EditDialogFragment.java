package com.lilers.todo.Views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lilers.todo.Models.Task;
import com.lilers.todo.R;
import com.lilers.todo.ViewModels.SharedViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditDialogFragment extends DialogFragment {
    private EditText taskET, infoET, dueDateET;
    private Spinner prioritySpinner;
    private SharedViewModel sharedViewModel;
    private Task currentTask;
    private DatePickerDialog datePicker;
    private DatePickerDialog.OnDateSetListener dateListener;
    private Date date;
    private SimpleDateFormat dateFormat;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_edit_dialog, null);
        taskET = view.findViewById(R.id.taskET);
        infoET = view.findViewById(R.id.infoET);
        dueDateET = view.findViewById(R.id.dueDateET);
        prioritySpinner = view.findViewById(R.id.prioritySpinner);
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        TextView title = new TextView(getContext());
        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(
                getContext(), R.array.priorityArray, R.layout.spinner_item);

        priorityAdapter.setDropDownViewResource(R.layout.spinner_drop_down);
        prioritySpinner.setAdapter(priorityAdapter);

        AlertDialog.Builder editDialog = new AlertDialog.Builder(getActivity());
        title.setText("Edit Task");
        title.setTypeface(null, Typeface.BOLD);
        title.setTextSize(32);
        title.setPadding(32, 32, 0, 0);
        editDialog.setTitle("Edit task");
        editDialog.setCustomTitle(title);
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

        setUpPopUpCalendar();

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
                Date date = task.getDueDate();
                Calendar calendar = Calendar.getInstance();
                taskET.setText(task.getTitle());
                infoET.setText(task.getInfo());
                dueDateET.setText(dateFormat.format(date));
                prioritySpinner.setSelection(getIndexOf(task.getPriority()));
                calendar.setTime(date);
                // Need to fix cause date is saved even if canceled
                datePicker = new DatePickerDialog(getContext(), dateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
            }
        });
    }

    private void saveTask() {
        String title = taskET.getText().toString().trim(), info = infoET.getText().toString().trim(),
                dueDate = dueDateET.getText().toString(),
                priority = prioritySpinner.getSelectedItem().toString();

        if (title.trim().isEmpty() || info.trim().isEmpty() || dueDate.trim().isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            currentTask.setTitle(title);
            currentTask.setInfo(info);
            if (date != null) {
                currentTask.setDueDate(date);
            } else {
                try {
                    currentTask.setDueDate(dateFormat.parse(dueDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
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

    private void setUpPopUpCalendar() {
        final Calendar calendar = Calendar.getInstance();
        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                date = calendar.getTime();
                dueDateET.setText(dateFormat.format(date));
            }
        };

        dueDateET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if ((hasFocus) && (!datePicker.isShowing())) {
                    datePicker.show();
                }
            }
        });

        dueDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!datePicker.isShowing()) {
                    datePicker.show();
                }
            }
        });
    }
}
