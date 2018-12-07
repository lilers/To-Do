package com.lilers.todo.Models;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lilers.todo.R;

import java.text.SimpleDateFormat;

public class TaskAdapter extends ListAdapter<Task, TaskAdapter.TaskHolder> {
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new TaskHolder(inflater.inflate(R.layout.task_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder taskHolder, int i) {
        Task task = getItem(i);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
        Resources resources = taskHolder.priority.getContext().getResources();
        taskHolder.title.setText(task.getTitle());
        taskHolder.info.setText(task.getInfo());
        taskHolder.dueDate.setText(dateFormat.format(task.getDueDate()));
        taskHolder.priority.setText(task.getPriority());
        taskHolder.priority.setTextColor(getPriorityColor(resources, task.getPriority()));
    }

    public TaskAdapter() {
        super(DIFF_CALLBACK);
    }

    /*
     * Compares tasks in the list to improve list updating (updates changed task instead of recreating
     * another list). ListAdapter takes care of things and runs on the background thread which helps
     * with really long lists
     */
    private static final DiffUtil.ItemCallback<Task> DIFF_CALLBACK = new DiffUtil.ItemCallback<Task>() {
        @Override
        public boolean areItemsTheSame(@NonNull Task task, @NonNull Task t1) {
            return task.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task task, @NonNull Task t1) {
            return ((task.getTitle() == t1.getTitle()) && (task.getInfo() == t1.getInfo()) &&
                    (task.getDueDate() == t1.getDueDate()) && (task.getPriority() == t1.getPriority()));
        }
    };

    public Task getTaskAt(int pos) {
        return getItem(pos);
    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        private TextView title, info, dueDate, priority;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleLabel);
            info = itemView.findViewById(R.id.infoLabel);
            dueDate = itemView.findViewById(R.id.dueDateLabel);
            priority = itemView.findViewById(R.id.priorityLabel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    // No position check is in case it's in the middle of delete animation
                    if ((listener != null) && (pos != RecyclerView.NO_POSITION)) {
                        listener.onItemClick(getItem(pos));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private int getPriorityColor(Resources resources, String priority) {
        switch (priority) {
            case "Low":
                return ResourcesCompat.getColor(resources, R.color.lowPriority, null);
            case "Medium":
                return ResourcesCompat.getColor(resources, R.color.mediumPriority, null);
            case "High":
                return ResourcesCompat.getColor(resources, R.color.highPriority, null);
            default:
                return ResourcesCompat.getColor(resources, R.color.unknownPriority, null);
        }
    }
}
