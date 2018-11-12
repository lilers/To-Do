package com.lilers.todo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.lilers.todo.Views.MainFragment;

public class MainActivity extends AppCompatActivity {
    public static FragmentManager fM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fM = getSupportFragmentManager();
        fM.beginTransaction().add(R.id.container, new MainFragment()).commit();
    }
}
