package com.lilers.todo.Views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lilers.todo.MainActivity;
import com.lilers.todo.R;
import com.lilers.todo.Models.Task;
import com.lilers.todo.Models.TaskAdapter;
import com.lilers.todo.ViewModels.SharedViewModel;
import com.lilers.todo.ViewModels.TaskViewModel;

import java.util.List;

public class MainFragment extends Fragment {
    private RecyclerView toDoRV;
    private TaskViewModel taskViewModel;
    private SharedViewModel sharedViewModel;

    public MainFragment() {
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        toDoRV = view.findViewById(R.id.toDoRV);

        toDoRV.setLayoutManager(new LinearLayoutManager(getContext()));
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
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
                final Task task = adapter.getTaskAt(viewHolder.getAdapterPosition());

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage("Are you sure you want to delete " + task.getTitle() + "?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), task.getTitle() + " successfully " +
                                "removed", Toast.LENGTH_SHORT).show();
                        taskViewModel.delete(task);
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Swipe item back onto screen
                        adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                        dialog.dismiss();
                    }
                });
                TextView message = alert.show().findViewById(android.R.id.message);
                message.setGravity(Gravity.CENTER);
            }
        }).attachToRecyclerView(toDoRV);

        // Edit item
        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                sharedViewModel.setTask(task);
                EditDialogFragment editDialog = new EditDialogFragment();
                editDialog.show(MainActivity.fM, null);
            }
        });

        getActivity().setTitle(R.string.app_name);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        sharedViewModel.getTask().observe(getViewLifecycleOwner(), new Observer<Task>() {
            @Override
            public void onChanged(@Nullable Task task) {
                if ((task != null) && (task.getId() > 0)) {
                    taskViewModel.update(task);
                } else {
                    taskViewModel.insert(task);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteBtn:
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage("Are you sure you want to delete all tasks?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskViewModel.deleteAllTasks();
                        Toast.makeText(getContext(), "All tasks deleted",
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                TextView message = alert.show().findViewById(android.R.id.message);
                message.setGravity(Gravity.CENTER);
                return true;
            case R.id.addBtn:
                sharedViewModel.setTask(null);
                MainActivity.fM.beginTransaction().replace(R.id.container, new AddFragment())
                        .addToBackStack(null).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
