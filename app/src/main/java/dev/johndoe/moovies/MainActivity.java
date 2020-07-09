package dev.johndoe.moovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

import dev.johndoe.moovies.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = ActivityMainBinding.inflate(getLayoutInflater());
        // Set content view based on binding
        setContentView(mActivityBinding.getRoot());
    }
}
