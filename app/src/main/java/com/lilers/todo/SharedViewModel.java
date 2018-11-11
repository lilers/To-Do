package com.lilers.todo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Task> task = new MutableLiveData<>();

    public void setTask(Task task) {
        this.task.setValue(task);
    }

    public LiveData<Task> getTask() {
        return task;
    }
}
