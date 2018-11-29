package com.lilers.todo.Models;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {Task.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class TaskDB extends RoomDatabase {
    private  static TaskDB INSTANCE;

    public abstract TaskDAO taskDAO();

    // Synchronized allows only one thread at a time to access
    public static synchronized TaskDB getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TaskDB.class,
                    "TaskDB").fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
