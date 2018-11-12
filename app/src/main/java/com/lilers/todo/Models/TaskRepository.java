package com.lilers.todo.Models;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class TaskRepository {
    private TaskDAO taskDAO;
    private LiveData<List<Task>> tasks;

    public TaskRepository(Application application) {
        TaskDB taskDB = TaskDB.getINSTANCE(application);
        taskDAO = taskDB.taskDAO();
        tasks = taskDAO.getAllTasks();
    }

    public Observable insert(final Task task) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) {
                if (!e.isDisposed()) {
                    taskDAO.insert(task);
                    e.onComplete();
                }
            }
        });
    }

    public Observable update(final Task task) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) {
                if (!e.isDisposed()) {
                    taskDAO.update(task);
                    e.onComplete();
                }
            }
        });
    }

    public Observable delete(final Task task) {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) {
                if (!e.isDisposed()) {
                    taskDAO.delete(task);
                    e.onComplete();
                }
            }
        });
    }

    public Observable deleteAllTasks() {
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) {
                if (!e.isDisposed()) {
                    taskDAO.deleteAllTasks();
                    e.onComplete();
                }
            }
        });
    }

    public LiveData<List<Task>> getAllTasks() {
        return tasks;
    }
}
