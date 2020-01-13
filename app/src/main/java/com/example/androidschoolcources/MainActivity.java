package com.example.androidschoolcources;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidschoolcources.fragments.LecturesFragment;

/**
 * Контейнер для лекций
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, LecturesFragment.newInstance())
                .commit();
    }


}