package com.lilers.todo.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.lilers.todo.Models.Task;
import com.lilers.todo.Models.TaskRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository taskRepo;
    private LiveData<List<Task>> tasks;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

    public TaskViewModel(@NonNull Application application) {
        super(application);

        taskRepo = new TaskRepository(application);
        tasks = taskRepo.getAllTasks();
    }

    public void insert(Task task) {
        disposable.add(taskRepo.insert(task).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).ignoreElements()
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }

    public void update(Task task) {
        disposable.add(taskRepo.update(task).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).ignoreElements()
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }

    public void delete(Task task) {
        disposable.add(taskRepo.delete(task).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).ignoreElements()
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }

    public void deleteAllTasks() {
        disposable.add(taskRepo.deleteAllTasks().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).ignoreElements()
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }

    public LiveData<List<Task>> getAllTasks() {
        return tasks;
    }
}
