package com.lilers.todo.Models;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDAO {
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("DELETE FROM Tasks")
    void deleteAllTasks();

    // Need to change from id to priority once enabled
    @Query("SELECT * FROM TASKS ORDER BY id ASC")
    LiveData<List<Task>> getAllTasks();
}
