package com.lilers.todo.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.lilers.todo.Models.Task;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Task> task = new MutableLiveData<>();

    public void setTask(Task task) {
        this.task.setValue(task);
    }

    public LiveData<Task> getTask() {
        return task;
    }
}
